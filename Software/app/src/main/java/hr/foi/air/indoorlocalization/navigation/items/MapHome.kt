package hr.foi.air.indoorlocalization.navigation.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import hr.foi.air.indoorlocalization.parser.*
import hr.foi.air.indoorlocalization.zones.ZoneOverlay
import androidx.compose.ui.graphics.drawscope.clipRect
import hr.foi.air.core.models.IFloorMap
import hr.foi.air.core.parser.floorMapList
import hr.foi.air.core.parser.zonesList
import hr.foi.air.indoorlocalization.asset.HomeMapAssetLiveMovement
import hr.foi.air.core.movements.*


@Composable
fun MapHome(
    floorMap: IFloorMap,
    ILiveAssetMovement: ILiveAssetMovement
){
    val imageSize = remember { mutableStateOf(Size.Zero) }
    val imageOffset = remember { mutableStateOf(Offset.Zero) }
    val currentPosition = remember { mutableStateOf(Offset.Zero) }

    var selectedMap by remember { mutableIntStateOf(floorMap.id) }
    var selectedMapText by remember { mutableStateOf(floorMap.name) }

    var selectedFloorMap by remember { mutableStateOf(floorMap) }

    val mapsList = remember { floorMapList }

    LaunchedEffect(Unit) {
        ILiveAssetMovement.simulateLiveMovement(currentPosition, floorMap.id)
    }

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
                        model = ImageRequest.Builder(LocalContext.current)
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

                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    clipRect(
                        left = imageOffset.value.x,
                        top = imageOffset.value.y,
                        right = imageOffset.value.x + imageSize.value.width,
                        bottom = imageOffset.value.y + imageSize.value.height
                    ) {
                        drawCircle(
                            color = Color.Red,
                            radius = 15f,
                            center = Offset(
                                x = imageOffset.value.x + currentPosition.value.x * imageSize.value.width,
                                y = imageOffset.value.y + currentPosition.value.y * imageSize.value.height
                            )
                        )
                    }
                }

            }

            Text(
                text=selectedFloorMap.name,
                modifier=Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            )

        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewMapHome(){
    JsonDataParser().updateFloorMaps(hr.foi.air.ws.TestData.testDataJSONMap)
    val testFloorMap = floorMapList[0]
    MapHome(floorMap=testFloorMap, HomeMapAssetLiveMovement())
}