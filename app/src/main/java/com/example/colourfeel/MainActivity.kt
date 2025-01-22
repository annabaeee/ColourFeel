package com.example.colourfeel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.colourfeel.ui.theme.ColourFeelTheme
import com.example.colourfeel.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColourFeelTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "color_picker",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("color_picker") {
                            ColorPickerScreen(
                                onNavigateToCalendar = { navController.navigate("calendar") }
                            )
                        }
                        composable("calendar") {
                            CalendarScreen(onNavigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorPickerScreen(
    modifier: Modifier = Modifier,
    onNavigateToCalendar: () -> Unit
) {
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
                        .clip(RoundedCornerShape(12.dp)) // Add this to round the corners
                        .background(color)
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
                    .size(width = 80.dp, height = 100.dp)
                    .clip(RoundedCornerShape(12.dp)) // Add this to round the corners
                    .background(selectedColor)
            )

            Spacer(modifier = Modifier.width(16.dp)) // Add space between the square and the TextField

            Column() {
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
        // Button to navigate to the calendar screen
        androidx.compose.material3.Button(
            onClick = onNavigateToCalendar,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("View Calendar")
        }
    }
}

@Composable
fun CalendarScreen(onNavigateBack: () -> Unit) {
    val currentDate = LocalDate.now()
    val currentMonth = YearMonth.now()
    val daysInMonth = currentMonth.lengthOfMonth()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with Back Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.Button(onClick = onNavigateBack) {
                Text("Back")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Days of the week headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(text = day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Calendar grid
        val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7
        val days = (1..daysInMonth).map { it.toString() }
        val calendarDays = List(firstDayOfMonth) { "" } + days

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(calendarDays) { day ->
                Text(
                    text = day,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.LightGray.takeIf { day.isNotEmpty() } ?: Color.Transparent)
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ColorPickerScreenPreview() {
    ColourFeelTheme {
        ColorPickerScreen(onNavigateToCalendar = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    ColourFeelTheme {
        CalendarScreen(onNavigateBack = {})
    }
}
