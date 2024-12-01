package hr.foi.air.indoorlocalization.navigation.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.indoorlocalization.models.impl.FloorMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import hr.foi.air.indoorlocalization.TestData.TestData
import hr.foi.air.indoorlocalization.models.IFloorMap
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import hr.foi.air.indoorlocalization.models.IZone
import hr.foi.air.indoorlocalization.zones.ZoneOverlay

@Composable
fun MapHome(
    floorMap: IFloorMap,
    zones: List<IZone>
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
        /*if(!floorMap.image.startsWith("http")){
            Image(
                painter = painter,
                contentDescription = "Floor Map",
                modifier= Modifier
                    .onGloballyPositioned { coordinates->
                        val bounds = coordinates.size
                        imageSize.value = Size(
                            bounds.width.toFloat(),
                            bounds.height.toFloat()
                        )
                    }
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }*/
        /*else{
            Image(
                painter = rememberAsyncImagePainter(floorMap.image),
                contentDescription = "Floor Map",
                modifier= Modifier
                    .onGloballyPositioned { coordinates->
                    val bounds = coordinates.size
                    imageSize.value = Size(
                        bounds.width.toFloat(),
                        bounds.height.toFloat()
                    )
                }
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }*/
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

            if(imageSize.value.width > 0 && imageSize.value.height > 0){
                zones.forEachIndexed { index, zone ->
                    ZoneOverlay(
                        zone = zone,
                        imageSize = imageSize.value,
                        imageOffset = imageOffset.value
                    )
                }
            }

        /*if(imageSize.value.width>0 && imageSize.value.height>0){
           zones.forEachIndexed{index, zone->
               ZoneOverlay(
                   zone=zone,
                   imageSize=imageSize.value
               )
           }
        }*/


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
    val testFloorMap = TestData.getFloorMaps()[0]
    val zones=TestData.getZones()
    MapHome(floorMap=testFloorMap, zones=zones)
}