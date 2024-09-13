<template>
    <div class="container" style="height: 300px;">
        <h1 style="margin-bottom: 30px; opacity: 0.8">用户登录</h1>
        <el-form ref="form" label-width="85px" :model="admin" :rules="state.rules">
            <el-form-item label="用户名" :style="{ width: '450px', height: 'auto' }" prop="username">
                <el-input type="text" v-model="admin.username"
                    :style="{ backgroundColor: 'black', color: 'white', opacity: 0.8 }" @blur="handleLoginNull"></el-input>
            </el-form-item>
            <el-form-item label="密码" :style="{ width: '450px', height: 'auto' }" prop="password">
                <el-input type="password" v-model="admin.password"
                    :style="{ backgroundColor: 'black', color: 'white', opacity: 0.8 }" @blur="handleLoginNull"></el-input>
            </el-form-item>
            <el-form-item label="验证码" :style="{ width: '450px', height: 'auto' }" prop="password">
                <el-input type="text" v-model="admin.captcha"
                    :style="{ backgroundColor: 'black', color: 'white', opacity: 0.8 }" @blur="handleLoginNull">
                </el-input>
                <el-image style="width: 120px; height: 38px; margin-top: 20px; margin-left: 105px;" :src="captchaImageBase64Data" @click=getCaptcha />
            </el-form-item>
            <el-form-item style="text-align: center; width: 700px; margin-top: 30px; margin-left: 150px;">
                <el-button type="primary"
                    style="background-color: black; width: 280px; border: 1px solid black; margin-left: -120px;"
                    @click=login :disabled="loginButtonDisabled">登录</el-button>
            </el-form-item>
        </el-form>
    </div>

</template>

<script setup>
import adminApi from '@/api/adminApi';
import router from '@/router';
import { color } from 'echarts';
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';
import { useTokenStore } from '@/stores/token';

const tokenStore = useTokenStore();

const admin = ref({
    username: '',
    password: '',
    captchaId: '',
    captcha: ''
});
const captchaImageBase64Data = ref('');

// ---------非空校验开始------------------
//登录按钮状态
const loginButtonDisabled = ref(true);
// 定义表单引用
const form = ref(null);
//验证规则
const state = reactive({
    rules: {
        username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' },
        ],
        password: [
            { required: true, message: '密码不能为空', trigger: 'blur' },
        ]
    }
});
// 处理添加数据的方法
const handleLoginNull = async () => {
    const formEl = form.value; // 获取 el-form 实例
    if (!formEl) return;

    // 验证表单
    await formEl.validate((valid, fields) => {
        if (valid) {
            loginButtonDisabled.value = false;
        } else {
            loginButtonDisabled.value = true;
        }
    });
};

// ---------非空校验结束------------------



//登录
function login() {
    adminApi.login(admin.value)
        .then(resp => {            
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200,
                    onClose: () => {
                        //保存JWT到sessionStorage
                        // sessionStorage.setItem('token', resp.data);    
                              
                        //将jwt保存在tokenStore中
                        tokenStore.update(resp.data);
                        
;                       //跳转到后台首页
                        router.push('/index');
                    }
                });
            } else {
                ElMessage.error(resp.msg);
            }
        });

}

function getCaptcha() {
    adminApi.captcha()
        .then(resp => {
            admin.value.captchaId = resp.data.captchaId;
            captchaImageBase64Data.value = resp.data.captchaImageBase64Data;
        });
}
getCaptcha();
</script>

<style>
.container {
    width: 500px;
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


/* .el-input {
    background-color: red;
    color: red;
} */


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