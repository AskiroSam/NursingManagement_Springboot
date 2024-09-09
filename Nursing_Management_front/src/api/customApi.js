import service from ".";

const customApi = {
    //添加
    insert(custom, family) {
        return service.post('/admin/custom', custom, family);
    },
    //根据ID删除
    delete(cid) {
        return service.delete(`/admin/custom/${cid}`);
    },
    //修改
    update(custom) {
        return service.put('/admin/custom', custom);
    },
    //修改fid
    updateFid(cid, fid) {
        return service.put('/admin/custom/updateFid', null, {
            params: {
                cid,
                fid
            }
        });
    },
    //查询所有
    selectByPage(pageNum, cname, cgender, caddress) {
        return service.get('/admin/custom', {
            params: {
                pageNum,
                cname,
                cgender,
                caddress
            }
        });
    },
    //根据ID查询
    selectById(cid) {
        return service.get(`/admin/custom/${cid}`);
    },
    //查询宿舍中的成员
    selectByHid(hid) {
        return service.get(`/admin/custom/hostel/${hid}`);
    }
};

export default customApi;