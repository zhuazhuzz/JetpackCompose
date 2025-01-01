package com.example.retrofitlazyload

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val peopleList = remember { mutableStateOf<List<Result>>(emptyList()) }
            LaunchedEffect(Unit) {
                fetchPeople(peopleList)
            }
            renderPeopleList(peopleList.value)
        }
    }

    private fun fetchPeople(peopleListState: MutableState<List<Result>>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.apiService.getUsers(100)
                withContext(Dispatchers.Main) {
                    if (response.results.isNotEmpty()) {
                        peopleListState.value = response.results
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", "Failed to fetch users", e)
            }
        }
    }
}

@Composable
fun renderPeopleList(peopleList: List<Result>) {
    Log.d("PeopleList", "People list size: ${peopleList.size}")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)
    ) {
        items(peopleList) { person ->
            personItem(person)

        }
    }
}

@Composable
fun personItem(person: Result) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(9.dp)
            .fillMaxWidth()
            .background(color = Color.White)
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
            .padding(10.dp)
        ) {
            AsyncImage(
                model = person.picture.large,
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .padding(6.dp)
                    .clip(RoundedCornerShape(40.dp))
            )
            Column(
                modifier = Modifier
                    .padding(6.dp)

            ) {
                Text(
                    text = person.name.first + " " + person.name.last,
                    fontWeight = FontWeight.Bold,

                    )
                Text(person.email)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPersonItem() {
    val mockPerson = returnAPerson()
    personItem(person = mockPerson)
}



