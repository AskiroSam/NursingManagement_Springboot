import service from ".";
// import qs from 'qs';

const adminApi = {
    // login(admin) {
    //     return service.post('/login', qs.stringify(admin));
    // },
    topDepartment() {
        return service.get('/admin/info/topDepartment');
    }
};

export default adminApi;