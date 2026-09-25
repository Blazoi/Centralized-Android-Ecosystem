package com.example.todo.screens.TaskList

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.CanvasBackground
import com.example.todo.ui.theme.SubtleGrey
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.TextBlack
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ToDoItem(
    id: Int,
    title: String,
    description: String,
    date: LocalDate?,
    location: String,
    icon: ImageVector,
    isCompleted: Boolean,
    onCheckedChange: () -> Unit,
    moreInfo: (Int) -> Unit,
//    changePosition: () -> Unit
) {
    var isChecked by remember { mutableStateOf(isCompleted) }

    val context = LocalContext.current
    val shape = RoundedCornerShape(16.dp)
    val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = SubtleGrey, shape = shape)
            .clip(shape)
            .background(SurfaceCard)
            .clickable { moreInfo(id) }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked, onCheckedChange = {
                onCheckedChange()
                isChecked = !isChecked
            }, colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF1B4D3E),
                uncheckedColor = TextBlack.copy(alpha = 0.4f),
                checkmarkColor = SurfaceCard
            )
        )

        Column(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isChecked) TextBlack.copy(alpha = 0.4f) else TextBlack,
                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (description.isNotBlank()) {
                Text(
                    text = description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(200.dp),
                    color = if (isChecked) TextBlack.copy(alpha = 0.3f) else TextBlack.copy(alpha = 0.8f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date?.format(dateFormatter) ?: "",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextBlack.copy(alpha = 0.6f)
                )

                if (location.isNotBlank()) {
                    Text(
                        text = "•  $location",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextBlack.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        IconButton(
            onClick = {
                Toast.makeText(
                    context, date?.dayOfWeek?.name, Toast.LENGTH_SHORT
                ).show()
            }, modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(CanvasBackground)
        ) {
            Icon(
                imageVector = icon, contentDescription = null, tint = Color(0xFF1B4D3E)
            )
        }
    }
}