<template>
  <el-card style="opacity: 0.9; height: 190px;">
    <div class="block text-center">
      <el-carousel height="150px" indicator-position="none">
        <el-carousel-item v-for="(item, index) in img" :key="item">
          <img :src="item" style="width: 100%; height: 100%; object-fit: cover;" />
        </el-carousel-item>
      </el-carousel>
    </div>
  </el-card>

  <el-card style="opacity: 0.9; height: 210px; width: 650px; margin-top: 10px;">
    <el-timeline style="max-width: 600px; float: left;">
      <el-timeline-item v-for="(activity, index) in activities" :key="index" :timestamp="activity.timestamp" :color="activity.color">
        {{ activity.content }}
      </el-timeline-item>
    </el-timeline>
    <div style="float: right;">
      <el-alert title="客户管理系统正常工作......" :closable="false" type="success" center show-icon style="width: 400px;" />
      <el-alert title="员工管理系统正常工作......" :closable="false" type="success" center show-icon style="width: 400px; margin-top: 5px;" />
      <el-alert title="院系管理系统正常工作......" :closable="false" type="success" center show-icon style="width: 400px; margin-top: 5px;" />
      <el-alert title="服务管理系统正常工作......" :closable="false"  type="success" center show-icon style="width: 400px; margin-top: 5px;" />
    </div>
  </el-card>


  <el-card style="opacity: 0.9; float: left; width: 650px; height: 280px; margin-top: 10px;">
    <div id="chart01"></div>
  </el-card>


  <el-card style="opacity: 0.9; float: right; width: 690px; height: 500px; margin-top: -210px;">
    <div id="chart02"></div>
  </el-card>

</template>

<script setup>
import adminApi from "@/api/adminApi";
import * as echarts from "echarts";
import { onMounted, ref } from "vue";

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

const activities = [
  {
    content: 'System start',
    timestamp: '2024-05-16',
    size: 'large',
    type: 'primary',
    color: 'red',
  },
  {
    content: 'System work',
    timestamp: '2024-06-18',
    color: '#0bbd87',
    hollow: true,
  },
  {
    content: 'System update',
    timestamp: '2024-09-7',
    color: '#000000',
  },
]


const img = ref({
  img1: 'images/项目logo.png',
  img2: 'images/img1.png'
});

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
        title: {
          text: '客户年龄分布',
        },
        series: [
          {
            name: '年龄分布',
            type: 'pie',
            radius: [50, 120],
            center: ['50%', '50%'],
            // roseType: 'area',
            itemStyle: {
              borderRadius: 0
            },
            label: {
              show: true, // 显示标签
              position: 'outside', // 标签位置，'inside' 表示内部
              formatter: '{c} ({d}%)' // 标签格式，{b} 为扇区名称，{c} 为数据值，{d} 为百分比
            },
            data: data.map((item, index) => ({
              value: count[index],
              name: item
            }))
          }
        ]
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
  height: 300px;
  float: left;
}

#chart02 {
  width: 600px;
  height: 450px;
  float: right;
}

.el-carousel__item img {
  color: #475669;
  opacity: 1;
  line-height: 150px;
  margin: 0;
  text-align: center;
}


</style>