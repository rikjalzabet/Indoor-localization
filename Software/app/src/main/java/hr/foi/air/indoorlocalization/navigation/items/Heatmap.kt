package hr.foi.air.indoorlocalization.navigation.items


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.zIndex
import hr.foi.air.indoorlocalization.parser.*
import hr.foi.air.core.parser.floorMapList
import hr.foi.air.core.movements.*
import coil.request.ImageRequest
import coil3.compose.rememberAsyncImagePainter
import hr.foi.air.core.models.HeatmapDot
import hr.foi.air.core.models.HeatmapLiveDot
import hr.foi.air.core.models.IAsset
import hr.foi.air.core.parser.assetZoneHistoryList
import hr.foi.air.core.parser.zonesList
import hr.foi.air.indoorlocalization.helpers.*
import hr.foi.air.indoorlocalization.zones.ZoneOverlay
import hr.foi.air.ws.TestData.testDataJSONMap
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
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
    val maxHeatmapDotFrequency = 1;
    val assetPositions = remember { mutableStateOf<List<IAsset>>(emptyList()) }

    LaunchedEffect(Unit) {
        ILiveAssetMovement.simulateLiveMovement(floorMap.id, assetPositions)
    }

    // live heatmap or history heatmap toggle
    val radioOptions = listOf("Live", "History")
    val selectedOption = remember { mutableStateOf(radioOptions[0]) }

    // date range picker
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    var startDate by remember { mutableStateOf(LocalDate.now().minusDays(365)) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val mapsList = remember { floorMapList }


    var selectedMap by remember { mutableIntStateOf(floorMap.id) }
    var selectedMapText by remember { mutableStateOf(floorMap.name) }

    var selectedFloorMap by remember { mutableStateOf(floorMap) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
            ){

            var expanded by remember { mutableStateOf(false) }
            Text("Select a map:", modifier = Modifier.padding(5.dp).align(Alignment.CenterVertically))

            Box {

                Button(onClick = {expanded = true}) {
                        Text(selectedMapText)
                }
                
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false}
                ) {



                    mapsList.forEach { map ->
                        DropdownMenuItem(
                            text = { Text(map.name)},
                            onClick = {
                                selectedMap = map.id
                                selectedMapText = map.name
                                expanded = false
                                selectedFloorMap = map
                            }
                        )
                    }
                }
            }
        }

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

        if (showStartDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            startDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                        }
                        showStartDatePicker = false
                    }) {
                        Text("OK")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (showEndDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            endDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                        }
                        showEndDatePicker = false
                    }) {
                        Text("OK")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (selectedOption.value == "History")  {
            Row(
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { showStartDatePicker = true }) {
                    Text("Start: ${startDate.format(formatter)}")
                }
                Button(onClick = { showEndDatePicker = true }) {
                    Text("End: ${endDate.format(formatter)}")
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
            contentAlignment = Alignment.Center
        ){
            val painter: Painter =
                if(!selectedFloorMap.image.startsWith("http")) {
                    val context = LocalContext.current
                    val resourceId = context
                        .resources
                        .getIdentifier(
                            selectedFloorMap.image,
                            "drawable",
                            context.packageName
                        )
                    painterResource(id = resourceId)
                }
                else{
                    rememberAsyncImagePainter(
                        model = coil3.request.ImageRequest.Builder(LocalContext.current)
                            .data(selectedFloorMap.image)
                            .build()
                    )
                }

            Image(
                painter = painter,
                contentDescription = "Floor Map",
                modifier= Modifier
                    .onGloballyPositioned { coordinates->
                        val bounds = coordinates.size
                        val position = coordinates.positionInRoot()
                        imageSize.value = Size(
                            bounds.width.toFloat(),
                            bounds.height.toFloat()
                        )
                        imageOffset.value=position
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

                            assetPositions.value.forEach { asset ->
                                val assetPosition = getAssetPosition(
                                    asset.x, asset.y, imageSize.value
                                )

                                drawCircle(
                                    color = Color.Red,
                                    radius = 15f, // Adjusted back to proper scaling
                                    center = Offset(
                                        x = imageOffset.value.x + assetPosition.x,
                                        y = imageOffset.value.y + assetPosition.y
                                    )
                                )
                            }

                            val newDotPosition = Offset(
                                x = imageOffset.value.x + (currentPosition.value.x * imageSize.value.width),
                                y = imageOffset.value.y + (currentPosition.value.y * imageSize.value.height)
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
                        imageSize = imageSize.value,
                        imageOffset = imageOffset.value,
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
                            floorMapId = selectedFloorMap.id,
                            fromDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            toDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            maxFrequency = maxHeatmapDotFrequency,
                            size = imageSize.value
                        )
                        //}
                    )
                }
            }

            Text(
                text = selectedFloorMap.name,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
    }







@Composable
fun HeatmapView(
    heatmapOffset : Offset,
    modifier: Modifier = Modifier,
    dots: List<HeatmapDot>,
    maxFrequency: Int,
    imageSize: Size,
    imageOffset: Offset
){
    Canvas(
        modifier = modifier.fillMaxSize()

    ) {
        //Log.d("Heatmap", "Dots size: ${dots.size}")
        clipRect(
            left = imageOffset.x,
            top = imageOffset.y,
            right = imageOffset.x + imageSize.width,
            bottom = imageOffset.y + imageSize.height
        ) {
            dots.forEach{ dot ->
                val position = getAssetPosition(dot.position.x, dot.position.y, imageSize)
                val leftOffset = position - imageOffset
                drawRect(
                    color = calculateColorForFrequency(dot.frequency, maxFrequency).copy(alpha = 0.25f),
                    topLeft = leftOffset,
                    size = Size(dot.size, dot.size),
                    blendMode = BlendMode.SrcOver
                )
            }
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



