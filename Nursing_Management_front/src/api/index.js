import router from "@/router";
import axios from "axios";
import { ElMessage } from "element-plus";
import { useTokenStore } from "@/stores/token";

const service = axios.create({
    baseURL: "http://localhost:8080"
});

//Axios的请求拦截器
service.interceptors.request.use(function (config) {
    const token = useTokenStore();
    // 如果不是登录请求就要在请求头中添加token
    if (!config.url.startsWith("/login")) {
        // config.headers.token = sessionStorage.getItem('token');
        //将store中的token放在请求头中
        config.headers.token = token.tokenStr;
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

//响应拦截器
service.interceptors.response.use(resp => {
    //获取续期的JWT
    let token = resp.headers.token;
    //将续期的JWT放在sessionStorage中
    // sessionStorage.setItem("token", token);
    // 将续期后的jwt放在store中
    const tokenStore = useTokenStore();
    tokenStore.update(token);

    return resp.data;
}, error => {
    if (error.status == 403) {
        ElMessage.error({
            message: '令牌错误，请重新登录',
            duration: 1200,
            onClose: () => {
                //删除sessionStorage的token
                // sessionStorage.removeItem('token');   
                const tokenStore = useTokenStore();
                tokenStore.$reset();
                //跳转到登录页
                router.push('/login');
            }
        });
    }

});

export default service;