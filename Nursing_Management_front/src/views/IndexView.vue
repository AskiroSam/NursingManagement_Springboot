<template>
    <el-row>
        <el-col :span="14">
            <el-card style="opacity: 0.9;">
                <div id="chart01" style="width: 600px; height: 450px;"></div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup>
import * as echarts from "echarts";
import { onMounted } from "vue";

//显示图表
function chart01() {
    let chart01 = echarts.init(document.getElementById('chart01'));

    let options = {
    title: {
      text: "ECharts 入门示例",
    },
    tooltip: {},
    xAxis: {
      data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
    },
    yAxis: {},
    series: [
      {
        name: "销量",
        type: "bar",
        data: [5, 20, 36, 10, 10, 20],
      },
    ],
  };

  // 渲染图表
  chart01.setOption(options);

}
//等待页面渲染完再掉用方法
onMounted(() => {
    chart01();
})
</script>

<style scoped></style>