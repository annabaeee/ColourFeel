package at.ac.fhstp.colourfeel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import at.ac.fhstp.colourfeel.ui.theme.ColourFeelTheme
import android.content.Context
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PageFragment2 : Fragment(R.layout.fragment_page_2) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using LayoutInflater
        val composeView = inflater.inflate(R.layout.fragment_page_2, container, false)

        // Set the Compose content inside the Fragment using ComposeView
        composeView.findViewById<ComposeView>(R.id.composeView).setContent {
            ColourFeelTheme {
                // Pass selectedColor as the background of the Scaffold
                val selectedColor by remember { mutableStateOf(Color.White) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = selectedColor // Set the background color of the scaffold
                ) { innerPadding ->
                    CalendarScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.Red) // Optionally keep a background color for other content
                            .fillMaxSize(),
                        context = requireContext()
                    )
                }
            }
        }
        // Return the root view (composed content)
        return composeView
    }
}

// Define the DayData class
data class DayData(val date: Int, val color: Color, val colorName: String, val dateText: String, val dayTitle: String)

// Function to convert a DayData object to a string (can be CSV or a simple custom format)
fun dayDataToString(dayData: DayData): String {
    return "${dayData.date},${dayData.color.toArgb()},${dayData.colorName},${dayData.dateText},${dayData.dayTitle}"
}

// Function to convert a string back into a DayData object
fun stringToDayData(dataString: String): DayData {
    val parts = dataString.split(",")
    return DayData(
        date = parts[0].toInt(),
        color = Color(parts[1].toInt()),
        colorName = parts[2],
        dateText = parts[3],
        dayTitle = parts[4]
    )
}

// Function to save the selected day to SharedPreferences
fun saveDay(context: Context, selectedDay: DayData) {
    val sharedPrefs = context.getSharedPreferences("ColourFeelPrefs", Context.MODE_PRIVATE)

    // Retrieve the existing list of saved days (or empty set if none)
    val savedDays = sharedPrefs.getStringSet("selectedDaysList", mutableSetOf()) ?: mutableSetOf()

    // Create a mutable copy of the savedDays set
    val mutableSavedDays = savedDays.toMutableSet()

    // Convert the selectedDay to a string and add it to the mutable set
    mutableSavedDays.add(dayDataToString(selectedDay))

    // Save the updated list back to SharedPreferences
    with(sharedPrefs.edit()) {
        putStringSet("selectedDaysList", mutableSavedDays)
        apply()
    }
}

// Function to retrieve the saved selected days from SharedPreferences
fun getSavedDays(context: Context): List<DayData> {
    val sharedPrefs = context.getSharedPreferences("ColourFeelPrefs", Context.MODE_PRIVATE)

    // Retrieve the saved set of days (or an empty set if none)
    val savedDays = sharedPrefs.getStringSet("selectedDaysList", mutableSetOf()) ?: mutableSetOf()

    // Convert each saved string back into a DayData object
    return savedDays.map { stringToDayData(it) }
}

fun initEmptyMonth(context: Context){
    saveDay(context, DayData(250101, Color.White, "", "", ""))
    saveDay(context, DayData(250102, Color.White, "", "", ""))
    saveDay(context, DayData(250103, Color.White, "", "", ""))
    saveDay(context, DayData(250104, Color.White, "", "", ""))
    saveDay(context, DayData(250105, Color.White, "", "", ""))
    saveDay(context, DayData(250106, Color.White, "", "", ""))
    saveDay(context, DayData(250107, Color.White, "", "", ""))

    saveDay(context, DayData(250108, Color.White, "", "", ""))
    saveDay(context, DayData(250109, Color.White, "", "", ""))
    saveDay(context, DayData(250110, Color.White, "", "", ""))
    saveDay(context, DayData(250111, Color.White, "", "", ""))
    saveDay(context, DayData(250112, Color.White, "", "", ""))
    saveDay(context, DayData(250113, Color.White, "", "", ""))
    saveDay(context, DayData(250114, Color.White, "", "", ""))

    saveDay(context, DayData(250115, Color.White, "", "", ""))
    saveDay(context, DayData(250116, Color.White, "", "", ""))
    saveDay(context, DayData(250117, Color.White, "", "", ""))
    saveDay(context, DayData(250118, Color.White, "", "", ""))
    saveDay(context, DayData(250119, Color.White, "", "", ""))
    saveDay(context, DayData(250120, Color.White, "", "", ""))
    saveDay(context, DayData(250121, Color.White, "", "", ""))

    saveDay(context, DayData(250122, Color.White, "", "", ""))
    saveDay(context, DayData(250123, Color.White, "", "", ""))
    saveDay(context, DayData(250124, Color.White, "", "", ""))
    saveDay(context, DayData(250125, Color.White, "", "", ""))
    saveDay(context, DayData(250126, Color.White, "", "", ""))
    saveDay(context, DayData(250127, Color.White, "", "", ""))
    saveDay(context, DayData(250128, Color.White, "", "", ""))

    saveDay(context, DayData(250129, Color.White, "", "", ""))
    saveDay(context, DayData(250130, Color.White, "", "", ""))
    saveDay(context, DayData(250131, Color.White, "", "", ""))
}

fun getCurrentDateInt(): Int {
    // Get the current date
    val currentDate = LocalDate.now()

    // Format the current date as yyMMdd
    val formatter = DateTimeFormatter.ofPattern("yyMMdd")
    val formattedDate = currentDate.format(formatter)

    // Convert the formatted date string to an Int
    return formattedDate.toInt()
}

fun deleteSavedDay(context: Context, date: Int) {
    val sharedPrefs = context.getSharedPreferences("ColourFeelPrefs", Context.MODE_PRIVATE)

    // Retrieve the existing list of saved days (or empty set if none)
    val savedDays = sharedPrefs.getStringSet("selectedDaysList", mutableSetOf()) ?: mutableSetOf()

    // Create a mutable copy of the savedDays set
    val mutableSavedDays = savedDays.toMutableSet()

    // Find the day to remove based on the date and remove it
    val dayToRemove = mutableSavedDays.find { it.startsWith("$date") }
    if (dayToRemove != null) {
        mutableSavedDays.remove(dayToRemove)
    }

    // Save the updated list back to SharedPreferences
    with(sharedPrefs.edit()) {
        putStringSet("selectedDaysList", mutableSavedDays)
        apply()
    }
}

fun formatDate(yyMMdd: Int): String {
    // Convert yyMMdd (e.g., 250123) to a string (e.g., "25-01-23")
    val dateStr = yyMMdd.toString().padStart(6, '0')
    val year = "20" + dateStr.substring(0, 2) // Extract year part and convert to full year
    val month = dateStr.substring(2, 4)
    val day = dateStr.substring(4, 6)

    // Create the LocalDate object
    val date = LocalDate.parse("$year-$month-$day", DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    // Get the day of the week, month, and day of the month
    val dayOfWeek = date.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH)
    val dayOfMonth = date.dayOfMonth
    val monthName = date.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH)

    // Get the correct suffix for the day of the month
    val suffix = when (dayOfMonth % 10) {
        1 -> if (dayOfMonth == 11) "th" else "st"
        2 -> if (dayOfMonth == 12) "th" else "nd"
        3 -> if (dayOfMonth == 13) "th" else "rd"
        else -> "th"
    }

    // Format the final string
    return "$dayOfWeek the $dayOfMonth$suffix of $monthName, $year"
}

@Composable
fun CalendarScreen(modifier: Modifier = Modifier, context: Context) {

    initEmptyMonth(context)

    // The list of saved days
    val originalDayList = getSavedDays(context = context)

    // Use a state to store the day list and sort it by date
    val days = remember { mutableStateListOf(*originalDayList.toTypedArray()) }

    val blankDaysCount = 3

    // Add two blank days at the start
    val blankDays = List(blankDaysCount) {
        DayData(date = -(it + 1), color = Color.Transparent, colorName = "", dateText = "", dayTitle = "")
    }

    // Sort the list by date (note: blank days will still appear at the start)
    days.sortBy { it.date }

    val contrastColour = contrastColor(GlobalState.todayData.color)

    var selectedDay by remember { mutableStateOf(days[23]) } // Now start with the first real day (after the two blanks)

    val currentDateInt = getCurrentDateInt()

    val letters = listOf("S", "M", "T", "W", "T", "F", "S")

    deleteSavedDay(context, currentDateInt)
    deleteSavedDay(context, currentDateInt)
    deleteSavedDay(context, currentDateInt)
    deleteSavedDay(context, currentDateInt)

    // Save new day and update the state immediately
    val newDay = DayData(currentDateInt, GlobalState.todayData.color, GlobalState.todayData.colorName, GlobalState.todayData.dateText, "")
    saveDay(context, newDay)

    //days.forEach(){day ->
    //    deleteSavedDay(context, day.date)
    //}

    // Update the local state for the list and ensure it is sorted
    days.clear() // Clear the old list
    days.addAll(getSavedDays(context)) // Reload the updated list
    // Add blank days to the start of the days list
    days.addAll(0, blankDays)
    days.sortBy { it.date } // Ensure the list is sorted by date

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GlobalState.todayData.color) // Use selected color as the background for the Column
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(3.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(letters) { letter ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(18.dp)) // Add this to round the corners
                        .background(offWhiteColor(GlobalState.todayData.color, 32))
                        .border(
                            6.dp,
                            midColor(
                                GlobalState.todayData.color,
                                offWhiteColor(GlobalState.todayData.color, 32)
                            ),
                            RoundedCornerShape(18.dp)
                        )
                )
                Text(
                    letter,
                    modifier = Modifier
                        .size(60.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    color = contrastColor(offWhiteColor(GlobalState.todayData.color, 32)),
                    fontWeight = FontWeight.Black
                )
            }
        }

        // Grid of colors with blank days added
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(3.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(days) { day ->
                if (day.date < 0) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(18.dp)) // Add this to round the corners
                            .background(GlobalState.todayData.color)
                    )
                } else {
                    if (currentDateInt == day.date) {
                        deleteSavedDay(context, day.date)
                        saveDay(context, newDay)
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(18.dp)) // Add this to round the corners
                                .background(GlobalState.todayData.color)
                                .border(
                                    width = if (day == selectedDay) 8.dp else 6.dp,
                                    offWhiteColor(
                                        midColor(GlobalState.todayData.color, day.color),
                                        64
                                    ),
                                    RoundedCornerShape(18.dp)
                                )
                                .clickable {
                                    selectedDay = day
                                    deleteSavedDay(context, day.date)
                                    saveDay(context, newDay)
                                    days.clear()
                                    days.addAll(getSavedDays(context)) // Reload updated list
                                    days.sortBy { it.date } // Sort the list again after change
                                }
                        )
                        Text(
                            (day.date % 100).toString(), modifier = Modifier
                                .size(60.dp)
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                            color = contrastColor(GlobalState.todayData.color),
                            fontWeight = FontWeight.Black
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(18.dp)) // Add this to round the corners
                                .background(day.color)
                                .border(
                                    width = if (day == selectedDay) 8.dp else 4.dp,
                                    offWhiteColor(
                                        midColor(GlobalState.todayData.color, day.color),
                                        32
                                    ),
                                    RoundedCornerShape(18.dp)
                                )
                                .clickable {
                                    selectedDay = day
                                }
                        )
                        Text(
                            (day.date % 100).toString(), modifier = Modifier
                                .size(60.dp)
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                            color = contrastColor(day.color),
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically // Align the components vertically
        ) {

            Text(
                formatDate(selectedDay.date),
                modifier = Modifier.weight(1f),
                color = contrastColour,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                fontSize = (32.sp),
                lineHeight = 32.sp

            )
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
                    .background(selectedDay.color)
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
                        "That day you felt: ",
                        modifier = Modifier.weight(1f),
                        color = contrastColour,
                        fontWeight = FontWeight.Black
                    ) // Apply inverted text color

                    Text(
                        selectedDay.colorName,
                        modifier = Modifier.weight(1f),
                        color = contrastColour,
                        fontWeight = FontWeight.Medium
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
                        selectedDay.dateText,
                        modifier = Modifier.weight(1f),
                        color = contrastColour,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

