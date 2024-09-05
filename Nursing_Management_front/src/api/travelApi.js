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
    }


};

export default travelApi;