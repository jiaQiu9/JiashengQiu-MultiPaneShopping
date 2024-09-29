package com.example.jiashengqiu_multipaneshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // For layout components
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.* // For state management
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleContentPanesApp()
        }
    }
}

@Composable
fun SimpleContentPanesApp() {
    val windowInfo = calculateCurrentWindowInfo()
    val items = listOf("Task 1", "Task 2", "Task 3", "Task 4") // sample tasks
    val shoppingList = listOf(Triple("A","$2","Good Product"),
                                Triple("B", "$10","Better Product"),Triple("C","$20","Improved Product"),
        Triple("D","$30","The Final Product"))
    var selectedItem by remember { mutableStateOf<Triple<String, String, String>?>(null) }

    if (windowInfo.isWideScreen) {
        // Two-pane layout for wide screens, one for the task list
        // the other for the task details
        Row(modifier = Modifier.fillMaxSize()) {
            TaskList(sList = shoppingList, onItemSelected = { selectedItem = it }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            TaskDetailPane(task = selectedItem, modifier = Modifier.weight(1f))
        }
    } else {
        // Single-pane layout for narrow screens
        if (selectedItem == null) {
            TaskList(sList = shoppingList, onItemSelected = { selectedItem = it }, modifier = Modifier.fillMaxSize())
        } else {
            TaskDetailPane(task = selectedItem, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun TaskList(sList: List<Triple<String, String, String>>, onItemSelected: (Triple<String,String,String>) -> Unit, modifier: Modifier = Modifier) {
    // Tasks displayed in a column in the task list pane
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(
            text = "Shopping List",
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier=Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier=Modifier.fillMaxSize()
        ){
            // List Title
            items(sList){
                    triple ->
                Row(){
                    Text(
                        text= "Product ${triple.first}",
                        textAlign = TextAlign.Center,
                        modifier= Modifier
                            .fillMaxWidth()
                            .clickable { onItemSelected(triple) }
                            .padding(3.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }


            }
        }
    }
}

@Composable
fun TaskDetailPane(task: Triple<String,String,String>?, modifier: Modifier = Modifier) {
    // Task details pane used when the user selects a particular task
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (task != null) {
            // Task Detail
            Text(
                text = "Details for Product ${task.first}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This is the detailed description of ${task.third}.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "The price of product ${task.first} is ${task.second}.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        } else {
            // No task selected
            Text(
                text = "No task selected",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun calculateCurrentWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    // Set a breakpoint for wide vs narrow screens (600dp is commonly used)
    val isWideScreen = screenWidth >= 600

    return WindowInfo(
        isWideScreen = isWideScreen
    )
}

data class WindowInfo(
    val isWideScreen: Boolean
)

@Preview(showBackground = true)
@Composable
fun SimpleContentPanesAppPreview() {
    SimpleContentPanesApp()
}