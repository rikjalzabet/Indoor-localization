package hr.foi.air.indoorlocalization.navigation.items


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hr.foi.air.core.models.IFloorMap
import hr.foi.air.indoorlocalization.asset.HeatmapAssetLiveMovement
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import hr.foi.air.indoorlocalization.parser.*
import hr.foi.air.core.parser.floorMapList
import hr.foi.air.core.movements.*
import coil.request.ImageRequest
import coil3.compose.rememberAsyncImagePainter
import hr.foi.air.core.models.HeatmapDot
import hr.foi.air.core.models.HeatmapLiveDot
import hr.foi.air.core.parser.zonesList
import hr.foi.air.indoorlocalization.helpers.*
import hr.foi.air.indoorlocalization.zones.ZoneOverlay
import hr.foi.air.ws.TestData.testDataJSONMap
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

@Composable
fun Heatmap(
    floorMap: IFloorMap,
    ILiveAssetMovement: ILiveAssetMovement
) {
    val imageSize = remember { mutableStateOf(Size.Zero) }
    val imageOffset = remember { mutableStateOf(Offset.Zero) }
    val currentPosition = remember { mutableStateOf(Offset.Zero) }
    val heatmapDots = remember { mutableStateListOf<HeatmapDot>() }
    val heatmapLiveDots = remember { mutableStateListOf<HeatmapLiveDot>() }
    val maxHeatmapDotFrequency = 10;

    LaunchedEffect(Unit) {
        ILiveAssetMovement.simulateLiveMovement(currentPosition, floorMap.id)
    }

    // live heatmap or history heatmap toggle
    val radioOptions = listOf("Live", "History")
    val selectedOption = remember { mutableStateOf(radioOptions[0]) }

    // which history frequency to use for displaying live heatmap
    val liveDateRange = listOf(Instant.now().minusSeconds(1500), Instant.now())
    val historicDateRange = listOf(Instant.from(Instant.now().minus(356, ChronoUnit.DAYS)),
                        Instant.now())

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
       ,
        horizontalArrangement = Arrangement.SpaceEvenly){
        radioOptions.forEach { text ->
                ToggleButton(
                    text = text,
                    isSelected = selectedOption.value == text,
                    onClick = {
                        selectedOption.value = text
                    }
                )
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        val painter: Painter =
            if(!floorMap.image.startsWith("http")) {
                val context = LocalContext.current
                val resourceId = context
                    .resources
                    .getIdentifier(
                        floorMap.image,
                        "drawable",
                        context.packageName
                    )
                painterResource(id = resourceId)
            }
            else{
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(floorMap.image)
                        .build()
                )
            }

        Image(
            painter = painter,
            contentDescription = "Floor Map",
            modifier= Modifier
                .onGloballyPositioned { coordinates ->
                    val bounds = coordinates.size
                    val position = coordinates.positionInRoot()
                    imageSize.value = Size(
                        bounds.width.toFloat(),
                        bounds.height.toFloat()
                    )
                    imageOffset.value = position
                }
                .fillMaxWidth()
                .padding(5.dp)
                .border(2.dp, Color.Black),
            contentScale = ContentScale.Crop
        )
        if (imageSize.value.width > 0 && imageSize.value.height > 0) {
            zonesList.forEach { zone ->
                ZoneOverlay(
                    zone = zone,
                    imageSize = imageSize.value,
                    imageOffset = imageOffset.value
                )
            }
            //Log.d("Heatmap", "Dots size: ${heatmapDots.size}")
            if(selectedOption.value == "Live"){
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    clipRect(
                        left = imageOffset.value.x,
                        top = imageOffset.value.y,
                        right = imageOffset.value.x + imageSize.value.width,
                        bottom = imageOffset.value.y + imageSize.value.height
                    ) {
                        val newDotPosition = Offset(
                            x = imageOffset.value.x + currentPosition.value.x * imageSize.value.width,
                            y = imageOffset.value.y + currentPosition.value.y * imageSize.value.height
                        )

                        val existingDot = heatmapLiveDots.find { it.position == newDotPosition }
                        if (existingDot != null) {
                            existingDot.frequency += 1
                        } else {
                            val liveMovementSize = 30f
                            heatmapLiveDots.add(HeatmapLiveDot(newDotPosition, 1, liveMovementSize=liveMovementSize))
                        }

                        heatmapLiveDots.forEach { dot ->
                            val color = calculateColorForFrequencyLiveAsset(dot.frequency)
                            val size = calculateSizeForColorLiveAsset(color,dot) //calculateSizeForColor(dot.frequency)//

                            drawCircle(
                                color = color.copy(alpha = 0.5f),
                                radius = size,
                                center = dot.position
                            )
                        }
                    }
                }
            }
            else{
            HeatmapView(
                heatmapOffset = imageOffset.value,
                maxFrequency = maxHeatmapDotFrequency,
                modifier = Modifier.fillMaxWidth(),
                /*dots = if (selectedOption.value == "Live") {
                    calculateHeatmapDotsInDateRange(
                        floorMapId = floorMap.id,
                        fromDate = Date.from(liveDateRange[0]),
                        toDate = Date.from(liveDateRange[1]),
                        maxFrequency = maxHeatmapDotFrequency,
                        size = imageSize.value
                    )


                } else {*/
                  dots = calculateHeatmapDotsInDateRange(
                        floorMapId = floorMap.id,
                        fromDate = Date.from(historicDateRange[0]),
                        toDate = Date.from(historicDateRange[1]),
                        maxFrequency = maxHeatmapDotFrequency,
                        size = imageSize.value
                    )
                //}
            )
            }
        }

        Text(
            text = floorMap.name,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun HeatmapView(
    heatmapOffset : Offset,
    modifier: Modifier = Modifier,
    dots: List<HeatmapDot>,
    maxFrequency: Int,
){
    Canvas(
        modifier = modifier.fillMaxSize()

    ) {
        //Log.d("Heatmap", "Dots size: ${dots.size}")

        dots.forEach{ dot ->
            val leftOffset = dot.position.plus(heatmapOffset)
                .minus(Offset(0f, 64f))
            drawRect(
                color = calculateColorForFrequency(dot.frequency, maxFrequency).copy(alpha = 0.25f),
                topLeft = leftOffset,
                size = Size(dot.size, dot.size),
                blendMode = BlendMode.SrcOver
            )
        }
    }
}

@Composable
fun LiveHeatmap(){
    //TODO
}

@Composable
fun ToggleButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = text
        )
    }
}


@Preview
@Composable
fun PreviewHeatmap(){
    JsonDataParser().updateFloorMaps(testDataJSONMap)
    val testFloorMap = floorMapList[0]
    JsonDataParser().updateZones(hr.foi.air.ws.TestData.testDataJSONZones)
    JsonDataParser().updateLiveAssetPositions(hr.foi.air.ws.TestData.testAssetPositionJSON)
    Heatmap(floorMap = testFloorMap, HeatmapAssetLiveMovement())
}