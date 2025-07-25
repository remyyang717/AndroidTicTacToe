package com.example.compoststudio.X6ren


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun D6renScreen() {
    var info1 by remember { mutableStateOf("算命算命~~~") }
    var info2 by remember { mutableStateOf("") }
    var info3 by remember { mutableStateOf("") }
    var pre by remember { mutableStateOf("0") }
    var current by remember { mutableStateOf("0") }
    var post by remember { mutableStateOf("0") }
    var goal by remember { mutableStateOf("") }

    val clipboardManager = LocalClipboardManager.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = info1,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = info2,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = info3,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = pre,
            onValueChange = { pre = it },
            label = { Text("第一个数字") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
            suffix = {
                Button(
                    onClick = { pre = randomNumber().toString() },
                    shape = RoundedCornerShape(12.dp)
                ) { Text("随机") }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = current,
            onValueChange = { current = it },
            label = { Text("第二个数字") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
            suffix = {
                Button(
                    onClick = { current = randomNumber().toString() },
                    shape = RoundedCornerShape(12.dp)
                ) { Text("随机") }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = post,
            onValueChange = { post = it },
            label = { Text("第三个数字") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
            suffix = {
                Button(
                    onClick = { post = randomNumber().toString() },
                    shape = RoundedCornerShape(12.dp)
                ) { Text("随机") }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = goal,
            onValueChange = { goal = it },
            label = { Text("算什么") },
            singleLine = true,
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(56.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val temp: List<String> = calculate(pre.toInt(),current.toInt(),post.toInt())
                    info1 = "第一卦： ${temp[0]}"
                    info2 = "第二卦： ${temp[1]}"
                    info3 = "第三卦： ${temp[2]}"

                    clipboardManager.setText(AnnotatedString(
                        """
                            「天德」
                            五行：金 
                            方位：东南 
                            解释：
                            天德是最吉利的神之一，有贵人相助、转危为安的象征。
                            有“化凶为吉”的作用，逢凶不怕。
                            适合解决纠纷、化解仇怨、修复关系、见官求助。
                            宜：求和、调解、求人、签合约
                            忌：冲动行事、讼争主动挑事
                            「大安」
                            五行：木 
                            方位：东 
                            解释：
                            表示安稳、吉祥、圆满。
                            适合一切平稳发展的事情，结果多为善终。
                            宜：结婚、定亲、安葬、稳定事务
                            忌：冒险、搬迁、变动性大的计划
                            常被看作**“主吉”之象**，尤其适合结局型问卜（如是否能顺利结束）
                            「留连」
                            五行：木 
                            方位：西北 
                            解释：
                            “拖拉、反复、纠缠”之象。
                            表示事情不顺利、延误、不断反复。
                            易有旧事重提、难分难解。
                            宜：思考、等待、避免仓促决定
                            忌：结婚、签约、急办要事
                            在感情上常表示纠缠不清、旧情复燃
                            「速喜」
                            五行：火 
                            方位：南 
                            解释：
                            主快速成就、喜庆临门。
                            有突然好消息、意外好运的意味，尤其在感情和金钱上。
                            宜：求财、求偶、应试、求子、谈合作
                            忌：过分贪图快速回报，易有“喜中带惊”之象
                            若伴“赤口”或“病符”，则易“喜极生悲”
                            「赤口」
                            五行：金 
                            方位：西 
                            解释：
                            主是非、争执、口舌之灾。
                            易与人产生矛盾、误解、打官司。
                            宜：避免争论、冷静处事
                            忌：签约、谈判、诉讼、聚会
                            在疾病方面常主牙齿、喉咙、火气上升
                            「小吉」
                            五行：水 
                            方位：北 
                            解释：
                            虽不是最好的，但象征“安稳中的进步”，小福小运。
                            多主家庭平和、人际关系顺畅。
                            宜：积蓄、学习、打基础、内部事务
                            忌：贪多图大、过于冒进
                            情感方面主温情细水长流型，非轰轰烈烈型
                            「空亡」
                            五行：土 
                            方位：中 
                            解释：
                            空虚、失败、失落、无果。
                            表示计划泡汤、事与愿违，特别是目标不明确时。
                            宜：反思、自省、撤退、重整旗鼓
                            忌：投资、出门、表白、开业
                            对应“中宫”，代表中心之地，但空有其表，无实质
                            「病符」
                            五行：土 
                            方位：东北 
                            解释：
                            顾名思义，与疾病、身体不适、隐患有关。
                            精神压力也算“病”，容易出现焦虑、头晕等亚健康状态。
                            宜：体检、调养、避免劳累
                            忌：远行、开刀、剧烈运动、应酬
                            情绪易不稳，有“烦闷难解”之象
                            「桃花」
                            五行：土 
                            方位：西南 
                            解释：
                            主人缘、情感、社交、异性缘。
                            可能是好事，也可能是桃色是非。
                            宜：约会、求偶、提升魅力、交朋友
                            忌：沉迷酒色、不辨真伪
                            在感情卜中若“桃花”配“留连”则多为烂桃花
                            ________________________________________
                            以上为九宫小六任的卦象和解释
                          
                            我这次算的卦象是 
                            第一卦 $info1
                            第二卦 $info2
                            第三卦 $info3
                            我想算的事情是 $goal
                            
                            我想要你根据以上的信息，结合每个神宫的五行和方位来做详细的解卦
                        """.trimIndent()
                    ))
                          },
            ) {
                Text(
                    text = "让我来给你算算",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }




}


fun randomNumber(): Int {
    return Random.nextInt(1, 366)
}

fun calculate(a:Int, b: Int, c: Int) : List<String>{

    val temp = mapOf(
        0 to "天德 金 东南",
        1 to "大安 木 东",
        2 to "留连 木 西北",
        3 to "速喜 火 南",
        4 to "赤口 金 西",
        5 to "小吉 水 北",
        6 to "空亡 土 中",
        7 to "病符 土 东北",
        8 to "桃花 土 西南"
    )

    val first = a % 9
    val second = (first + b - 1) % 9
    val third = (second + c - 1) % 9

    return listOf( temp[first].toString() , temp[second].toString() , temp[third].toString())

}



