package at.ac.fhstp.colourfeel

import android.content.Context
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
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import at.ac.fhstp.colourfeel.ui.theme.ColourFeelTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import kotlin.math.roundToInt
import kotlin.math.sqrt

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

fun getAverageColorMonth(days: List<DayData>): Color {
    // Get the last 31 days (if there are fewer than 31, take as many as available)
    val last31Days = days.takeLast(31)

    // Initialize sum of R, G, and B components
    var sumR = 0f
    var sumG = 0f
    var sumB = 0f
    var validDaysCount = 0 // Count of valid days (non-white days)

    // Sum up the R, G, B values of all colors, ignoring pure white days
    for (day in last31Days) {
        val color = day.color
        // Skip pure white days
        if (color == Color.White) continue

        sumR += color.red
        sumG += color.green
        sumB += color.blue
        validDaysCount++
    }

    // If no valid days were found (all days were white), return white color
    if (validDaysCount == 0) {
        return Color.White
    }

    // Calculate the average of each component
    val avgR = sumR / validDaysCount
    val avgG = sumG / validDaysCount
    val avgB = sumB / validDaysCount

    // Return the average color
    return Color(avgR, avgG, avgB)
}

fun getAverageColorWeek(days: List<DayData>): Color {
    // Get the last 7 days (if there are fewer than 7, take as many as available)
    val last7Days = days.takeLast(7)

    // Initialize sum of R, G, and B components
    var sumR = 0f
    var sumG = 0f
    var sumB = 0f
    var validDaysCount = 0 // Count of valid days (non-white days)

    // Sum up the R, G, B values of all colors, ignoring pure white days
    for (day in last7Days) {
        val color = day.color
        // Skip pure white days
        if (color == Color.White) continue

        sumR += color.red
        sumG += color.green
        sumB += color.blue
        validDaysCount++
    }

    // If no valid days were found (all days were white), return white color
    if (validDaysCount == 0) {
        return Color.White
    }

    // Calculate the average of each component
    val avgR = sumR / validDaysCount
    val avgG = sumG / validDaysCount
    val avgB = sumB / validDaysCount

    // Return the average color
    return Color(avgR, avgG, avgB)
}

fun getBrightness(color: Color): Color {
    // Extract the RGB components of the Color
    val red = color.red
    val green = color.green
    val blue = color.blue
    val dat = sqrt(0.299f * red * red + 0.587f * green * green + 0.114f * blue * blue)

    // Apply the luminance formula to get brightness
    return Color(dat, dat, dat)
}

fun getHue(color: Color): Float {
    val r = color.red
    val g = color.green
    val b = color.blue

    // Find the maximum and minimum values among the RGB components
    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    // If delta is 0, the color is achromatic (gray), so there is no hue.
    if (delta == 0f) {
        return 0f // Can also return an arbitrary value, as the hue is undefined for gray.
    }

    var hue: Float

    when (max) {
        r -> hue = (g - b) / delta // Red is the dominant color
        g -> hue = (b - r) / delta + 2f // Green is the dominant color
        b -> hue = (r - g) / delta + 4f // Blue is the dominant color
        else -> hue = 0f
    }

    // Hue is expressed in degrees, so we multiply by 60 to get the angle in degrees.
    hue *= 60f

    // Ensure hue is positive by adjusting it for negative values (angles less than 0)
    if (hue < 0f) {
        hue += 360f
    }

    return hue
}

fun hsvToRgb(hue: Float, saturation: Float = 1f, value: Float = 1f): Color {
    // Normalize the hue to be in the range 0-360
    val h = hue % 360
    val s = saturation.coerceIn(0f, 1f)
    val v = value.coerceIn(0f, 1f)

    // If saturation is 0, the result is a gray color
    if (s == 0f) {
        return Color(v, v, v) // Gray color, value determines brightness
    }

    // Calculate the sector of the color wheel (divide 360 by 6 to get the hue sections)
    val i = (h / 60f).toInt()
    val f = h / 60f - i
    val p = v * (1f - s)
    val q = v * (1f - f * s)
    val t = v * (1f - (1f - f) * s)

    val (r, g, b) = when (i) {
        0 -> Triple(v, t, p) // Red to Yellow
        1 -> Triple(q, v, p) // Yellow to Green
        2 -> Triple(p, v, t) // Green to Cyan
        3 -> Triple(p, q, v) // Cyan to Blue
        4 -> Triple(t, p, v) // Blue to Magenta
        5 -> Triple(v, p, q) // Magenta to Red
        else -> Triple(0f, 0f, 0f) // This should never happen
    }

    // Return the RGB color as a Color object
    return Color(r, g, b)
}

fun getSaturation(color: Color): Float {
    val r = color.red
    val g = color.green
    val b = color.blue

    // Find the maximum and minimum values among the RGB components
    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)

    // If the color is a shade of gray (max == min), saturation is 0
    if (max == 0f) {
        return 0f
    }

    // Calculate the saturation using the formula
    return (max - min) / max
}

@Composable
fun AnalysisScreen(modifier: Modifier = Modifier, context: Context) {

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
                arrayOf("Last Month Average ", getAverageColorMonth(getSavedDays(context)), getAverageColorMonth(getSavedDays(context))),
                arrayOf("Last Week Average ",getAverageColorWeek(getSavedDays(context)), getAverageColorWeek(getSavedDays(context))),
                arrayOf("Brightness Change % " + (getBrightness(getAverageColorMonth(getSavedDays(context))).red*100).roundToInt(), getBrightness(getAverageColorMonth(getSavedDays(context))), getBrightness(GlobalState.todayData.color)),
                arrayOf("Brightness Change % " + (getBrightness(getAverageColorWeek(getSavedDays(context))).red*100).roundToInt(), getBrightness(getAverageColorWeek(getSavedDays(context))), getBrightness(GlobalState.todayData.color)),
                arrayOf("Hue Change in Degrees " + (getHue(getAverageColorMonth(getSavedDays(context))) - getHue(GlobalState.todayData.color)).roundToInt(), hsvToRgb(getHue(getAverageColorMonth(getSavedDays(context))), 1f, 1f), hsvToRgb(getHue(GlobalState.todayData.color)), 1f, 1f),
                arrayOf("Hue Change in Degrees " + (getHue(getAverageColorWeek(getSavedDays(context))) - getHue(GlobalState.todayData.color)).roundToInt(), hsvToRgb(getHue(getAverageColorWeek(getSavedDays(context))), 1f, 1f), hsvToRgb(getHue(GlobalState.todayData.color)), 1f, 1f),
                arrayOf("Saturation Change % " + (0 - ((getSaturation(getAverageColorMonth(getSavedDays(context)))*100) - (getSaturation(GlobalState.todayData.color))*100).roundToInt()), hsvToRgb(getHue(getAverageColorMonth(getSavedDays(context))), getSaturation(getAverageColorMonth(getSavedDays(context))), 1f), hsvToRgb(getHue(getAverageColorMonth(getSavedDays(context))), getSaturation(GlobalState.todayData.color), 1f)),
                arrayOf("Saturation Change % " + (0 - ((getSaturation(getAverageColorWeek(getSavedDays(context)))*100) - (getSaturation(GlobalState.todayData.color))*100).roundToInt()), hsvToRgb(getHue(getAverageColorWeek(getSavedDays(context))), getSaturation(getAverageColorWeek(getSavedDays(context))), 1f), hsvToRgb(getHue(getAverageColorWeek(getSavedDays(context))), getSaturation(GlobalState.todayData.color), 1f)))

            items(dataPoints) { dataPoint ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(25.dp)) // Add this to round the corners
                            .background(Brush.verticalGradient(
                                colors = listOf(dataPoint[1] as Color, dataPoint[2] as Color)
                            ))
                            .border(6.dp, offWhiteColor(GlobalState.todayData.color, 32), RoundedCornerShape(24.dp)
                            )
                    )
                    Text(""+dataPoint[0],
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        fontWeight = FontWeight.Black,
                        color = contrastColor(GlobalState.todayData.color)
                    )
                }
            }
        }
    }
}