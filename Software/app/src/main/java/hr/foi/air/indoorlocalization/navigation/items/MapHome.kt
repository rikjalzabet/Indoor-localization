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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import hr.foi.air.indoorlocalization.TestData.TestData
import hr.foi.air.indoorlocalization.models.IFloorMap

@Composable
fun MapHome(floorMap: IFloorMap){
    Box (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        if(floorMap.image.startsWith("http")){
            rememberAsyncImagePainter(floorMap.image)
        }
        val model: String = floorMap.image

        val painter: Painter=if(!floorMap.image.startsWith("http")) {
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
        //if(!floorMap.image.startsWith("http")){
            Image(
                painter = painter,
                contentDescription = "Floor Map",
                modifier= Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
       /* }
        else{
            AsyncImage(
                model=model,
                contentDescription = "Floor Map Online",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }*/

        Text(
            text=floorMap.name,
            modifier=Modifier
                .padding(top=16.dp)
                .align(Alignment.TopCenter)
        )
    }
    /*
    Scaffold{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ){
            Text(
                text="This is main map page",
                style= typography.bodyLarge,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                textAlign= TextAlign.Center
            )
        }
    }*/
}

@Preview(showBackground = true)
@Composable
fun PreviewMapHome(){
    val testFloorMap = TestData.getFloorMaps()[1]
    MapHome(floorMap=testFloorMap)
}