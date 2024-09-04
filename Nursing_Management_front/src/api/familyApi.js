import service from ".";

const familyApi = {
    //添加
    insert(family) {
        return service.post('/admin/family', family);
    },
    //根据ID删除
    delete(fid) {
        return service.delete(`/admin/family/${fid}`);
    },
    //修改
    update(family) { 
        return service.put('/admin/family', family);
    },
    //查询所有
    selectByPage(pageNum, fname) {
        return service.get('/admin/family', {
            params: {
                pageNum,
                fname
            }
        });
    },
    //根据ID查询
    selectById(fid) {
        return service.get(`/admin/family/${fid}`);
    }
};

export default familyApi;