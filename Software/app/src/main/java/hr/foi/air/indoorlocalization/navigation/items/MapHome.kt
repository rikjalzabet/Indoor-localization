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
import hr.foi.air.indoorlocalization.TestData.TestData
import hr.foi.air.indoorlocalization.TestData.testDataJSONMap
import hr.foi.air.indoorlocalization.models.IFloorMap
import hr.foi.air.indoorlocalization.parser.floorMapList
import hr.foi.air.indoorlocalization.parser.*
import hr.foi.air.indoorlocalization.parser.zonesList
import hr.foi.air.indoorlocalization.zones.ZoneOverlay
import androidx.compose.material3.DropdownMenuItem

@Composable
fun MapHome(
    floorMap: IFloorMap,
    //zones: List<IZone>
){
    val imageSize = remember { mutableStateOf(Size.Zero) }
    val imageOffset = remember { mutableStateOf(Offset.Zero) }

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
        }

        Text(
            text=floorMap.name,
            modifier=Modifier
                .padding(16.dp)
                .align(Alignment.TopCenter)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapHome(){
    JsonDataParser().updateFloorMaps(testDataJSONMap)
    val testFloorMap = floorMapList[0] //TestData.getFloorMaps()[0]
    MapHome(floorMap=testFloorMap)
}