import service from ".";
// import qs from 'qs';

const adminApi = {
    // login(admin) {
    //     return service.post('/login', qs.stringify(admin));
    // },
    //部门人数图表
    topDepartment() {
        return service.get('/admin/info/topDepartment');
    },
    //客户年龄分布
    selectAgeCount() {
        return service.get('/admin/info/ageCount')
    }
};

export default adminApi;