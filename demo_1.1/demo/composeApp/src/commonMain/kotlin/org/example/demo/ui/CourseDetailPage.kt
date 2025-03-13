package org.example.demo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import demo.composeapp.generated.resources.Res
import demo.composeapp.generated.resources.back
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import org.example.demo.util.CourseRequest
import org.example.demo.util.client
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun CourseDetailPage(
    navController: NavController,
    id: Int,
    courseName: String,
    description: String,
    teacher: String,
    name: String,
    role: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            topBar = {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(painterResource(Res.drawable.back), "back")
                }
            },
            bottomBar = {
            },
            modifier = Modifier.fillMaxHeight().widthIn(max = 700.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center
            ) {
                var selected by remember { mutableStateOf(0) }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Text(courseName, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))
                        Text("课程码：$id", modifier = Modifier.padding(5.dp))
                        Text("教师：$teacher", modifier = Modifier.padding(5.dp))
                        Text(description, modifier = Modifier.padding(5.dp))
                    }
                    item {
                        TabRow(
                            selectedTabIndex = selected
                        ) {
                            Tab(
                                selected = selected == 0,
                                onClick = {selected = 0}
                            ) {
                                Text("课堂", modifier = Modifier.padding(10.dp))
                            }
                            Tab(
                                selected = selected == 1,
                                onClick = {selected = 1}
                            ) {
                                Text("讨论", modifier = Modifier.padding(10.dp))
                            }
                        }
                    }
                    when (selected) {
                        0 -> {
                            (1..10).reversed().forEach {
                                item {
                                    Column(modifier = Modifier.padding(5.dp)) {
                                        Text("X月X日", modifier = Modifier.padding(5.dp))
                                        Card(modifier = Modifier.fillParentMaxWidth().padding(5.dp)) {
                                            Text("第${it}周", modifier = Modifier.padding(5.dp))
                                        }
                                    }
                                }
                            }
                        }
                        1 -> {
                            item {
                                Text(
                                    "[讨论区]",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}