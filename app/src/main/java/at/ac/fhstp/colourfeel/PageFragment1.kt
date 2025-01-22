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
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextFieldDefaults
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
                // Pass selectedColor as the background of the Scaffold
                val selectedColor by remember { mutableStateOf(Color.White) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = selectedColor // Set the background color of the scaffold
                ) { innerPadding ->
                    ColorPickerScreen(
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

// Function to contrast a color
fun contrastColor(color: Color): Color {
    val red = (((color.red * 128) + 192)%255).toInt()
    val green = (((color.green * 128) + 192)%255).toInt()
    val blue = (((color.blue * 128) + 192)%255).toInt()
    return Color((red*0.6+green*0.2+blue*0.2).toInt() / 255f, (red*0.2+green*0.6+blue*0.2).toInt() / 255f, (red*0.2+green*0.2+blue*0.6).toInt() / 255f)
}

// Function to create off white
fun offWhiteColor(color: Color, value: Int): Color {
    val red = ((color.red * value) + 255 - value).toInt()
    val green = ((color.green * value) + 255 - value).toInt()
    val blue = ((color.blue * value) + 255 - value).toInt()
    return Color(red / 255f, green / 255f, blue / 255f)
}

// Function to create a midpoint colour
fun midColor(color: Color, color2 : Color): Color {
    val red = (((color.red * 128) + (color2.red * 128))%257).toInt()
    val green = (((color.green * 128) + (color2.green * 128))%257).toInt()
    val blue = (((color.blue * 128) + (color2.blue * 128))%257).toInt()
    return Color(red / 255f, green / 255f, blue / 255f)
}

data class ColorDay(var date: Int, var color: Color, var colorName: String, var dateText: String )

object GlobalState {
    var todayData by mutableStateOf(ColorDay(112233, Color.White, "..", "..."))
}

@OptIn(ExperimentalMaterial3Api::class)
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

    val contrastColour = contrastColor(GlobalState.todayData.color)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GlobalState.todayData.color) // Use selected color as the background for the Column
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
                        .border(6.dp, offWhiteColor(midColor(GlobalState.todayData.color, color), 32), RoundedCornerShape(18.dp))
                        .clickable { GlobalState.todayData = GlobalState.todayData.copy(color = color) }
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
                    .background(GlobalState.todayData.color)
                    .border(12.dp, offWhiteColor(GlobalState.todayData.color, 32), RoundedCornerShape(24.dp))
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
                    Text("Today you feel: ", modifier = Modifier.weight(1f), color = contrastColour) // Apply inverted text color

                    // The TextField for user input
                    androidx.compose.material3.TextField(
                        value = GlobalState.todayData.colorName,
                        onValueChange = { GlobalState.todayData = ColorDay(GlobalState.todayData.date, GlobalState.todayData.color, it, GlobalState.todayData.dateText) },
                        label = { Text("Feeling name?", color = contrastColour) },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .background(GlobalState.todayData.color), // Ensure transparent background for text field
                        textStyle = LocalTextStyle.current.copy(contrastColour),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = contrastColour, // Change the border color when focused
                            unfocusedIndicatorColor = contrastColour.copy(alpha = 0.6f), // Change the border color when not focused
                            containerColor = GlobalState.todayData.color // Optional: Set the background of the text field
                        )
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
                        value = GlobalState.todayData.dateText,
                        onValueChange = { GlobalState.todayData = ColorDay(GlobalState.todayData.date, GlobalState.todayData.color, GlobalState.todayData.colorName, it) },
                        label = { Text("Why so?", color = contrastColour) },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .background(GlobalState.todayData.color), // Ensure transparent background for text field
                        textStyle = LocalTextStyle.current.copy(contrastColour), // Apply inverted color to text
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = contrastColour, // Change the border color when focused
                            unfocusedIndicatorColor = contrastColour.copy(alpha = 0.6f), // Change the border color when not focused
                            containerColor = GlobalState.todayData.color // Optional: Set the background of the text field
                        )
                    )
                }
            }
        }
    }
}
