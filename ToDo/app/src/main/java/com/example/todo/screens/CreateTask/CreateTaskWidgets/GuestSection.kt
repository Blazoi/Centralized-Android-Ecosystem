package com.example.todo.screens.CreateTask.CreateTaskWidgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.RoyalGold
import com.example.todo.ui.theme.SubtleGrey
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.TextBlack
import com.example.todo.ui.theme.secondaryDark
import com.example.todo.ui.theme.secondaryLight
import com.example.todo.ui.theme.tertiaryDark
import com.example.todo.ui.theme.tertiaryLight

@Composable
fun GuestSection(
    guestAdded: String,
    guestList: List<String>,
    onGuestAddedChange: (String) -> Unit,
    onAddGuest: () -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = guestAdded,
                onValueChange = onGuestAddedChange,
                label = { Text(text = "Ajouter un invité") },
                colors = colors,
                shape = shape,
                singleLine = true,
                modifier = Modifier.weight(2f)
            )

            Button(
                onClick = {
                    if (guestAdded.isNotBlank()) {
                        onAddGuest()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryLight,
                    contentColor = Color.White,
                ),
                shape = shape,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .border(
                        2.dp,
                        Color.Black,
                        RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    text = "Add",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(shape)
                .background(SurfaceCard)
                .padding(horizontal = 12.dp)
        ) {
            items(guestList) { guest ->
                Text(
                    text = guest,
                    color = TextBlack,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                HorizontalDivider(color = SubtleGrey)
            }
        }
    }
}