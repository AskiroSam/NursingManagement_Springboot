<template>
    <div class="container">
        <h1 style="margin-bottom: 30px; margin-right: 5px;  opacity: 0.8">用户登录</h1>
        <el-form rel="form" label-width="60px">
            <el-form-item label="用户名" :style="{ width: '450px', height: 'auto'}">
                <el-input type="text" v-model="admin.username"  :style="{ backgroundColor: 'black', color: 'white', opacity: 0.8 }"></el-input>
            </el-form-item>
            <el-form-item label="密码" :style="{ width: '450px', height: 'auto'}">
                <el-input type="password" v-model="admin.password"  :style="{ backgroundColor: 'black', color: 'white', opacity: 0.8 }"></el-input>
            </el-form-item>
            <el-form-item style="text-align: center; width: 700px; margin-top: 30px; margin-left: 150px;">
                <el-button type="primary" style="background-color: black; width: 300px; border: 1px solid black; margin-left: -120px;" @click=login>登录</el-button>
            </el-form-item>
        </el-form>
    </div>

</template>

<script setup>
import adminApi from '@/api/adminApi';
import router from '@/router';
import { color } from 'echarts';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';


const admin = ref({
    username: '',
    password: ''
})

//登录
function login() {
    //跳转到后台首页
    router.push('/index');
}
</script>

<style>
.container {
    width: 480px;
    height: 200px;
    border: 1px solid black;
    background-color: #00000080;
    text-align: center;
    color: #fff;
    padding: 40px;
    border-radius: 30px;
    position: absolute;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);
}

/* input {
    border: 0;
    width: 60%;
    font-size: 15px;
    color: #fff;
    background: transparent;
    border-bottom: 2px solid #fff;
    padding: 5px 10px;
    outline: none;
    margin-top: 10px;
}

button {
    margin-top: 50px;
    width: 60%;
    height: 30px;
    border-radius: 10px;
    border: 0;
    color: #fff;
    text-align: center;
    line-height: 30px;
    font-size: 15px;
    background-image: linear-gradient(to right, #30cfd0, #330867);
} */

.el-input {
    background-color: red;
    color: red;
}


.app {
    position: fixed;
    /* 固定位置 */
    width: 100%;
    /* 使 .app 占据整个视口宽度 */
    height: 100%;
    /* 使 .app 占据整个视口高度 */
    top: 0;
    /* 从视口顶部开始 */
    left: 0;
    /* 从视口左侧开始 */
    display: flex;
    /* 使用 Flexbox 布局 */
    align-items: center;
    /* 垂直居中 */
    justify-content: center;
    /* 水平居中 */
    overflow: hidden;
    /* 隐藏溢出内容，防止滚动条出现 */
}

body {
    background-image: url('../../images/background.jpeg');
    background-size: cover;
    /* 背景图片完全覆盖容器 */
    background-position: center;
    /* 背景图片居中 */
    background-repeat: no-repeat;
    /* 背景图片不重复 */
    margin: 0;
    /* 清除默认边距 */
    padding: 0;
    /* 清除默认内边距 */
}
</style>