package at.ac.fhstp.colourfeel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import at.ac.fhstp.colourfeel.ui.theme.ColourFeelTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight

class PageFragment3 : Fragment(R.layout.fragment_page_3) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using LayoutInflater
        val composeView = inflater.inflate(R.layout.fragment_page_3, container, false)

        // Set the Compose content inside the Fragment using ComposeView
        composeView.findViewById<ComposeView>(R.id.composeView).setContent {
            ColourFeelTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = GlobalState.todayData.color // Set the background color of the scaffold
                ) { innerPadding ->
                    AnalysisScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.Red) // Optionally keep a background color for other content
                            .fillMaxSize()
                    )
                }
            }
        }

        // Return the root view (composed content)
        return composeView
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(modifier: Modifier = Modifier) {

    val contrastColour = contrastColor(GlobalState.todayData.color)

    Column {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically // Align the components vertically
        ) {

            // The selected color box
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp)
                    .clip(RoundedCornerShape(24.dp)) // Add this to round the corners
                    .background(GlobalState.todayData.color)
                    .border(
                        12.dp,
                        offWhiteColor(GlobalState.todayData.color, 32),
                        RoundedCornerShape(24.dp)
                    )
            )

            Spacer(modifier = Modifier.width(16.dp)) // Add space between the square and the TextField

            Column{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically // Align the components vertically
                ) {
                    Text(
                        "Today you feel: ",
                        modifier = Modifier.weight(1f),
                        color = contrastColour,
                        fontWeight = FontWeight.Black
                    ) // Apply inverted text color

                    Text(
                        GlobalState.todayData.colorName,
                        modifier = Modifier.weight(1f),
                        color = contrastColour,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Due To: ",
                        modifier = Modifier.weight(1f),
                        color = contrastColour,
                        fontWeight = FontWeight.Black

                    ) // Apply inverted text color

                    Text(
                        GlobalState.todayData.dateText,
                        modifier = Modifier.weight(1f),
                        color = contrastColour,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add space between the square and the TextField

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(6.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            val dataPoints = arrayOf(
                arrayOf("Last Month Average:",Color.Yellow, Color.Yellow),
                arrayOf("Last Week Average:",Color.Red, Color.Red),
                arrayOf("Brightness Change:",Color.Yellow, Color.Red),
                arrayOf("Brightness Change:",Color.Gray, Color.Green),
                arrayOf("Hue Change:",Color.Blue, Color.DarkGray),
                arrayOf("Hue Change:",Color.LightGray, Color.Cyan),
                arrayOf("Saturation Change:",Color.Magenta, Color.Yellow),
                arrayOf("Saturation Change:",Color.White, Color.Transparent))

            items(dataPoints) { dataPoint ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(24.dp)) // Add this to round the corners
                        .background(Brush.verticalGradient(
                            colors = listOf(dataPoint[1], dataPoint[2]) as List<Color>
                        ))
                        .border(12.dp, offWhiteColor(GlobalState.todayData.color, 32), RoundedCornerShape(24.dp))
                )
                Text(""+dataPoint[0],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                    , fontWeight = FontWeight.Black
                )
            }
        }
    }
}