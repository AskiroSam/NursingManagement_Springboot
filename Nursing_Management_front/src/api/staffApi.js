import service from ".";

const staffApi = {
    //添加
    insert(staff) {
        return service.post('/admin/staff', staff);
    },
    //根据ID删除
    delete(sid) {
        return service.delete(`/admin/staff/${sid}`);
    },
    //修改
    update(staff) { 
        return service.put('/admin/staff', staff);
    },
    //查询所有
    selectByPage(pageNum, sname) {
        return service.get('/admin/staff', {
            params: {
                pageNum,
                sname
            }
        });
    },
    //根据ID查询
    selectById(sid) {
        return service.get(`/admin/staff/${sid}`);
    }
};

export default staffApi;