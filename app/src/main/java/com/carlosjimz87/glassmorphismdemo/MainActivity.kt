package com.carlosjimz87.glassmorphismdemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.carlosjimz87.glassmorphismdemo.ui.theme.GlassmorphismDemoTheme

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlassmorphismDemoTheme {
                GlassmorphismScrollableBackground()
            }
        }
    }
}

@Composable
fun GlassmorphismScrollableBackground() {
    val imageResources = remember {
        listOf(
            R.drawable.cascade,
            R.drawable.jungle,
            R.drawable.desert,
            R.drawable.snow,
            R.drawable.spring
        )
    }

    // Get screen height in DP
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    // Remember the scroll state
    val listState = rememberLazyListState()

    // Detect scroll position
    val isAtBottom by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == imageResources.lastIndex
        }
    }

    // Animate GlassCard position from Top to Bottom
    val glassCardPosition by animateDpAsState(
        targetValue = if (isAtBottom) screenHeightDp * 0.95f else 0.dp, // Relative offset
        label = "glassCardAnimation"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(imageResources) { imageRes ->
                BackgroundImage(imageRes)
            }
        }

        GlassCard(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .align(Alignment.TopCenter)
            .offset(y = glassCardPosition))
    }
}

@Composable
fun BackgroundImage(drawableRes: Int) {
    Image(
        painter = rememberAsyncImagePainter(drawableRes),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun GlassCard(modifier: Modifier) {
    Box(
        modifier = modifier
            .graphicsLayer { alpha = 0.6f; shadowElevation = 10.dp.toPx() }
            .background(Color.White.copy(alpha = 0.5f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Glassmorphism Demo",
            color = Color.White,
            fontSize = 22.sp
        )
    }
}