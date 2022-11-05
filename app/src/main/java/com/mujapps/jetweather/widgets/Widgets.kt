package com.mujapps.jetweather.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mujapps.jetweather.model.WeatherData
import com.mujapps.jetweather.screens.main.MainViewModel
import com.mujapps.jetweather.screens.main.WeatherStateImage

@Composable
fun WeatherDetailsRow(
    weather: WeatherData,
    dateMilli: Long,
    mMainViewModel: MainViewModel
) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.icon}.png"

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(8.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = mMainViewModel.getFormattedDate(dateMilli).substringBefore(","),
                modifier = Modifier.padding(4.dp)
            )
            WeatherStateImage(imageUrl)
            Surface(modifier = Modifier.padding(0.dp)) {
                Text(
                    text = weather.description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold,
                    )
                ) {
                    append("Max")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Red.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("Min")
                }
            })
        }
    }
}