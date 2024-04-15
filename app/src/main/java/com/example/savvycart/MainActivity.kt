package com.example.savvycart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.savvycart.ui.theme.SavvyCartTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SavvyCartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(MainViewModel())
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel) {
    //val groceryItems by remember { viewModel.groceryItems }.collectAsState(initial = emptyList())
    val groceryItems = groceryItemsPlaceholder
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Navigate to AddScreen */ },
                //containerColor = Color.,
            ) {
                Text("+", fontSize = 24.sp)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to SavvyCart!", fontSize = 28.sp, modifier = Modifier.padding(16.dp))
            LazyColumn {
                items(groceryItems) { item ->
                    GroceryItemCard(item)
                }
            }
        }
    }
}

@Composable
fun GroceryItemCard(item: GroceryItem) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = item.item)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$${item.price}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.groceryStore)
        }
    }
}

@Composable
fun AddScreen(viewModel: MainViewModel) {
    var selectedDateTime by remember { mutableStateOf(Date()) }
    var isDateTimePickerOpen by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var groceryStore by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Grocery Item Price", fontSize = 28.sp, modifier = Modifier.padding(16.dp))
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Item Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = itemPrice,
            onValueChange = { itemPrice = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Price") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = groceryStore,
            onValueChange = { groceryStore = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Grocery Store") }
        )
        Spacer(modifier = Modifier.height(32.dp))
//        Spacer(modifier = Modifier.height(16.dp))
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text("Date & Time: ", modifier = Modifier.padding(end = 16.dp))
//            Text(
//                text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(selectedDateTime),
//                modifier = Modifier.weight(1f)
//            )
//            Button(
//                onClick = { isDateTimePickerOpen = true },
//                modifier = Modifier.wrapContentWidth()
//            ) {
//                Text("Select")
//            }
//        }
//        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            coroutineScope.launch {
//                viewModel.addGroceryItem(
//                    itemName,
//                    itemPrice.toDouble(),
//                    groceryStore,
//                    System.currentTimeMillis().toString() // Timestamp
//                )
//                // Navigate back to the main screen
//                (context as? AppCompatActivity)?.onBackPressed()
            }
        }) {
            Text("Submit!")
        }
    }
}

class MainViewModel : ViewModel() {
    private val _groceryItems = MutableStateFlow<List<GroceryItem>>(emptyList())
    val groceryItems: StateFlow<List<GroceryItem>> = _groceryItems
}

data class GroceryItem(
    val itemId: Int,
    val item: String,
    val price: Double,
    val groceryStore: String,
    val timestamp: String
)

val groceryItemsPlaceholder = listOf(
    GroceryItem(
        itemId = 1,
        item = "Apples",
        price = 2.99,
        groceryStore = "Fresh Mart - Downtown",
        timestamp = "2024-04-07T10:00:00Z"
    ),
    GroceryItem(
        itemId = 2,
        item = "Bananas",
        price = 1.49,
        groceryStore = "Green Grocers - Uptown",
        timestamp = "2024-04-07T09:30:00Z"
    ),
    GroceryItem(
        itemId = 3,
        item = "Milk",
        price = 3.25,
        groceryStore = "Dairy Delight - Suburbia",
        timestamp = "2024-04-07T11:15:00Z"
    ),
    GroceryItem(
        itemId = 1,
        item = "Apples",
        price = 2.79,
        groceryStore = "Fresh Mart - Uptown",
        timestamp = "2024-04-07T12:30:00Z"
    ),
    GroceryItem(
        itemId = 1,
        item = "Rotisserie Chicken",
        price = 5.99,
        groceryStore = "Costco",
        timestamp = "2024-04-07T12:30:00Z"
    ),
    GroceryItem(
        itemId = 1,
        item = "Dinner Rolls (12pk)",
        price = 4.69,
        groceryStore = "Walmart Neighborhood Market",
        timestamp = "2024-04-07T12:30:00Z"
    ),
    GroceryItem(
        itemId = 1,
        item = "Orange Juice",
        price = 4.69,
        groceryStore = "Safeway",
        timestamp = "2024-04-07T12:30:00Z"
    )
)