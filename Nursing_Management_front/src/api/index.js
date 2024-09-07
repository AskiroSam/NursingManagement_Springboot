import router from "@/router";
import axios from "axios";
import { ElMessage } from "element-plus";

const service = axios.create({
    baseURL: "http://localhost:8080"
});

//Axios的请求拦截器
service.interceptors.request.use(function (config) {
    // 如果不是登录请求就要在请求头中添加token
    if (!config.url.startsWith("/login")) {
        config.headers.token = sessionStorage.getItem('token');
    }
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

//响应拦截器
service.interceptors.response.use(resp => {
    return resp.data;
}, error => {
    if (error.status == 403) {
        ElMessage.error({
            message: '令牌错误，请重新登录',
            duration: 1200,
            onClose: () => {
                //删除sessionStorage的token
                sessionStorage.removeItem('token');                        
                //跳转到登录页
                router.push('/login');
            }
        });
    }
    
});

export default service;