package at.ac.fhstp.colourfeel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import at.ac.fhstp.colourfeel.ui.theme.ColourFeelTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource

class PageFragment1 : Fragment(R.layout.fragment_page_1) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using LayoutInflater
        val composeView = inflater.inflate(R.layout.fragment_page_1, container, false)

        // Set the Compose content inside the Fragment using ComposeView
        composeView.findViewById<ComposeView>(R.id.composeView).setContent {
            ColourFeelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorPickerScreen(modifier = Modifier
                        .padding(innerPadding)
                        .background(Color.Red)
                    )
                }
            }
        }

        // Return the root view (composed content)
        return composeView
    }
}

@Composable
fun ColorPickerScreen(modifier: Modifier = Modifier) {
    // The list of 36 colors for the grid
    val originalColors = listOf(
        colorResource(R.color.red), colorResource(R.color.orange), colorResource(R.color.yellow),
        colorResource(R.color.green), colorResource(R.color.blue), colorResource(R.color.magenta),
        colorResource(R.color.red1), colorResource(R.color.orange1), colorResource(R.color.yellow1),
        colorResource(R.color.green1), colorResource(R.color.blue1), colorResource(R.color.magenta1),
        colorResource(R.color.red2), colorResource(R.color.orange2), colorResource(R.color.yellow2),
        colorResource(R.color.green2), colorResource(R.color.blue2), colorResource(R.color.magenta2),
        colorResource(R.color.red3), colorResource(R.color.orange3), colorResource(R.color.yellow3),
        colorResource(R.color.green3), colorResource(R.color.blue3), colorResource(R.color.magenta3),
        colorResource(R.color.red4), colorResource(R.color.orange4), colorResource(R.color.yellow4),
        colorResource(R.color.green4), colorResource(R.color.blue4), colorResource(R.color.magenta4),
        colorResource(R.color.random1), colorResource(R.color.random2), colorResource(R.color.random3),
        colorResource(R.color.random4), colorResource(R.color.random5), colorResource(R.color.random6),
    )

    val colors = remember { originalColors.shuffled() }
    // The currently selected color
    var selectedColor by remember { mutableStateOf(Color.White) }
    // State for the TextField input
    var textFieldValue by remember { mutableStateOf("") }
    var textFieldValue1 by remember { mutableStateOf("")}

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Grid of colors
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(6.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(colors) { color ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(18.dp)) // Add this to round the corners
                        .background(color)
                        .border(6.dp, Color.White, RoundedCornerShape(18.dp))
                        .clickable { selectedColor = color }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                    .background(selectedColor)
                    .border(12.dp, Color.White, RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.width(16.dp)) // Add space between the square and the TextField

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically // Align the components vertically
                ) {
                    Text("Today you feel: ", modifier = Modifier.weight(1f))

                    // The TextField for user input
                    androidx.compose.material3.TextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        label = { Text("Enter text") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.TextField(
                        value = textFieldValue1,
                        onValueChange = { textFieldValue1 = it },
                        label = { Text("How so?") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorPickerScreenPreview() {
    ColourFeelTheme {
        ColorPickerScreen()
    }
}