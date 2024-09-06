import service from ".";

const travelApi = {
    //查询所有
    selectAll() {
        return service.get("/admin/travel");
    },
    //获取所有在院客户
    allCustom(tid) {
        return service.get(`/admin/travel/allCustom/${tid}`);
    },
    //获取所有在职员工
    allStaff(tid) {
        return service.get(`/admin/travel/allStaff/${tid}`)
    },
    //分配客户
    insertTidAndCid(tid, cids) {
        return service.post("/admin/travel/tidAndCid", {
            tid,
            cids
        });
    },
    //分配员工
    insertTidAndSid(tid, sids) {
        return service.post("/admin/travel/tidAnSid", {
            tid,
            sids
        }) 
    },
    //删除路线
    delete(tid) {
        return service.delete(`/admin/travel/${tid}`)
    }, 
    //进度条进度
    getTravelProgress(tid) {
        return service.get(`/admin/travel/progress/${tid}`)
    },
    //修改
    update(travel) {
        return service.put('/admin/travel', travel);
    },
    //更新每日进度条
    clearProgress(travel) {
        return service.put('/admin/travel/progress', travel);
    }


};

export default travelApi;