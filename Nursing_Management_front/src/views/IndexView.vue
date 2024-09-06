<template>
  <!-- <el-row>
    <el-col :span="12">
      
    </el-col>
  </el-row>

  <el-row>
    <el-col :span="12">
      <el-card style="opacity: 0.9;">
        <div id="chart02"></div>
      </el-card>
    </el-col>
  </el-row> -->
  <el-card style="opacity: 0.9; float: left; width: 650px;">
    <div id="chart01"></div>
  </el-card>
  <el-card style="opacity: 0.9; float: right; width: 650px;">
    <div id="chart02"></div>
  </el-card>
</template>

<script setup>
import adminApi from "@/api/adminApi";
import * as echarts from "echarts";
import { onMounted } from "vue";

//显示图表
function chart01() {
  adminApi.topDepartment()
    .then(resp => {
      let data = new Array();
      let count = new Array();
      for (let index = 0; index < resp.data.length; index++) {
        data.push(resp.data[index].dname);
        count.push(resp.data[index].personnum);
      }

      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('chart01'));

      // 指定图表的配置项和数据
      var option = {
        title: {
          text: '院系人数统计',
          textStyle: {
            color: "#000000"
          }
        },
        tooltip: {},
        legend: {
          data: ['人数'],
          right: "10%",
          textStyle: {
            color: "#000000"
          }
        },
        xAxis: {
          data: data,
          axisLine: {
            show: true,
            lineStyle: {
              color: "#000000"
            }
          },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          axisLine: {
            show: true,
            lineStyle: {
              color: "#000000"
            }
          }
        },
        series: [
          {
            name: '人数',
            type: 'bar',
            data: count,
            color: "#893448",

          }
        ]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    });
}

function chart02() {
  adminApi.selectAgeCount()
    .then(resp => {
      let data = new Array();
      let count = new Array();
      for (let index = 0; index < resp.data.length; index++) {
        data.push(resp.data[index].ageRange);
        count.push(resp.data[index].count);
      }

      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('chart02'));

      // 指定图表的配置项和数据
      var option = {
        legend: {
          top: 'bottom'
        },
        series: [
          {
            
          }

        ]
        // title: {
        //   text: '客户年龄分布',
        //   textStyle: {
        //     color: "#000000"
        //   }
        // },
        // tooltip: {},
        // legend: {
        //   data: ['人数'],
        //   right: "10%",
        //   textStyle: {
        //     color: "#000000"
        //   }
        // },
        // xAxis: {
        //   data: data,
        //   axisLine: {
        //     show: true,
        //     lineStyle: {
        //       color: "#000000"
        //     }
        //   },
        //   axisTick: {
        //     show: false
        //   }
        // },
        // yAxis: {
        //   axisLine: {
        //     show: true,
        //     lineStyle: {
        //       color: "#000000"
        //     }
        //   }
        // },
        // series: [
        //   {
        //     name: '人数',
        //     type: 'bar',
        //     data: count,
        //     color: "#893448",

        //   }
        // ]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    });
}

//等待页面渲染完再掉用方法
onMounted(() => {
  chart01();
  chart02();
})
</script>

<style scoped>
#chart01 {
  width: 600px;
  height: 450px;
  float: left;
}

#chart02 {
  width: 600px;
  height: 450px;
  float: right;
}
</style>