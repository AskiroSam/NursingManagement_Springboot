import axios from "axios";

const service = axios.create({
    baseURL: "http://localhost:8080"

});

//响应拦截器
service.interceptors.response.use(resp => {
    return resp.data;
}, error => {
    Promise.reject(error);
});

export default service;