import service from ".";

const hostelApi = {
    //查询所有
    selectByPage(pageNum, hno, dname) {
        return service.get('/admin/hostel', {
            params: {
                pageNum,
                hno,
                dname
            }
        });
    },
    //获取所有在宿舍客户
    allCustom(hid) {
        return service.get(`/admin/hostel/allCustom/${hid}`);
    },
    //分配客户
    insertHidAndCid(hid, cids) {
        return service.post("/admin/hostel/hidAndCid", {
            hid,
            cids
        });
    },
    //删除宿舍
    delete(hid) {
        return service.delete(`/admin/hostel/${hid}`)
    },
    //添加
    insert(hostel) {
        return service.post('/admin/hostel', hostel);
    },
};

export default hostelApi;