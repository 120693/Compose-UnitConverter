package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
//                    CaptainGame()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverter() {

    // 입력값, 출력값
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }

    // 입출력값 단위
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }

    //  입출력값 드롭박스 오픈 여부
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    // 입출력 단위변환에 사용하는 배수?!
    val iConversionFactor = remember { mutableStateOf(1.00) }
    val oConversionFactor = remember { mutableStateOf(1.00) }

    // Compose는 함수 안에 함수 넣기도 가능하다
    // 단위 변환 함수
    fun convertUnits() {
        val inputValueToDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueToDouble * iConversionFactor.value * 100.0 / oConversionFactor.value).roundToInt() / 100
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", modifier = Modifier.padding(0.dp, 0.dp))  // padding 사용법
        Spacer(modifier = Modifier.height(16.dp))  // Spacer 사용법
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()},
            label = { Text(text = "Enter the value")})
        // onValueChange : 값이 바뀔 때의 동작 -> 없으면 {}
        Spacer(modifier = Modifier.height(16.dp))
        Row {
//            val context = LocalContext.current
//            Button(onClick = {
//                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
//            }) {
//                Text(text = "MyButton")
//            }
            Box {
                Button(onClick = { iExpanded = true }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Drop Down")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            // 입력한 값의 단위를 설정하고
                            inputUnit = "Centimeters"
                            // 선택하면 드롭다운이 사라지도록
                            iExpanded = false
                            iConversionFactor.value = 0.01
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            inputUnit = "Meters"
                            iExpanded = false
                            iConversionFactor.value = 1.0
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            inputUnit = "Feet"
                            iExpanded = false
                            iConversionFactor.value = 0.3048
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            inputUnit = "Millimeters"
                            iExpanded = false
                            iConversionFactor.value = 0.001
                            convertUnits()
                        })
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { oExpanded = true }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Drop Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false}) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            outputUnit = "Centimeters"
                            oExpanded = false
                            oConversionFactor.value = 0.01
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            outputUnit = "Meters"
                            oExpanded = false
                            oConversionFactor.value = 1.00
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            outputUnit = "Feet"
                            oExpanded = false
                            oConversionFactor.value = 0.3048
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            outputUnit = "Millimeters"
                            oExpanded = false
                            oConversionFactor.value = 0.001
                            convertUnits()
                        })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Result: $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun CaptainGame() {
    // remember : Compose에서 상태를 보존하는 데 사용, Composable 함수 내에서 상태를 보존하고, 해당 함수가 재렌더링될 때마다 이전 상태가 보존됩니다.
    // mutableStateOf는 변경 가능한 상태를 나타냄. (0)은 초기값, 상태를 변경할 때마다 Compose는 화면을 다시 그리도록 강제합니다.
    // mutableState 클래스는 Compose에 의해 읽기와 쓰기를 관찰하는 단일 값 보유자 - value
    // MutableState 객체는 내부의 상태를 변경할 수 있습니다.
    // 이것이 가능한 이유는 MutableState가 가변(mutable) 상태를 관리하기 위해 사용되기 때문입니다.
    // .value를 통해 MutableState 객체의 상태를 변경할 수 있으며, 이에 따라 UI도 업데이트됩니다.
    /*
    * val treasuresFound는 변수 자체가 가리키는 MutableState 객체에 대한 참조를 변경할 수 없지만, MutableState 객체 내부의 상태를 변경할 수 있습니다.
    * 따라서 treasuresFound.value++는 MutableState 객체의 내부 상태를 변경하는 것이므로 값이 바뀌는 것입니다.
    * */

//    val treasuresFound = remember { mutableStateOf(0) }

    // by : .value를 사용하지 않아도 바로 값을 얻을 수 있음
    // by 키워드는 프로퍼티 위임(delegated properties)을 사용할 때 주로 사용됩니다.
    // 프로퍼티 위임은 프로퍼티의 getter와 setter 동작을 다른 객체에게 위임할 수 있는 기능을 제공하는 것
    var treasuresFound by remember { mutableStateOf(0) }

    val direction = remember { mutableStateOf("North") }

    val stormOrTreasure = remember { mutableStateOf("") }

    Column {
//        Text("Treasures Found: ${treasuresFound.value}")
        // by
        Text("Treasures Found: $treasuresFound")
        Text("Currnet Direction: ${direction.value}")

        Button(onClick = {
            direction.value = "East"
            if (Random.nextBoolean()) {
//                treasuresFound.value++
                // by
                treasuresFound++
                stormOrTreasure.value = "Found a Treasure"
            } else {
                stormOrTreasure.value = "Storm Ahead"
            }
        }) {
            Text(text = "sail East")
        }

        Button(onClick = {
            direction.value = "West"
            if (Random.nextBoolean()) {
//                treasuresFound.value++
                // by
                treasuresFound++
                stormOrTreasure.value = "Found a Treasure"
            } else {
                stormOrTreasure.value = "Storm Ahead"
            }
        }) {
            Text(text = "sail West")
        }

        Button(onClick = {
            direction.value = "North"
            if (Random.nextBoolean()) {
//                treasuresFound.value++
                // by
                treasuresFound++
                stormOrTreasure.value = "Found a Treasure"
            } else {
                stormOrTreasure.value = "Storm Ahead"
            }
        }) {
            Text(text = "sail North")
        }

        Button(onClick = {
            direction.value = "South"
            if (Random.nextBoolean()) {
//                treasuresFound.value++
                // by
                treasuresFound++
                stormOrTreasure.value = "Found a Treasure"
            } else {
                stormOrTreasure.value = "Storm Ahead"
            }
        }) {
            Text(text = "sail South")
        }

        Text(text = stormOrTreasure.value)
    }
}

// 미리봐보고싶은 Composable 을 실행해 볼 수 있음 - 제일 마지막에 Preview()를 붙여야함
@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}