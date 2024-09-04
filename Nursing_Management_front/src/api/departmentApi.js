import service from ".";

const departmentApi = {
    //添加
    insert(department) {
        return service.post('/admin/department', department);
    },
    //根据ID删除
    delete(did) {
        return service.delete(`/admin/department/${did}`);
    },
    //修改
    update(department) {
        return service.put('/admin/department', department);
    },
    //查询所有
    selectAll() {
        return service.get('/admin/department');
    },
    //根据ID查询
    selectById(did) {
        return service.get(`/admin/department/${did}`);
    }
};

export default departmentApi;