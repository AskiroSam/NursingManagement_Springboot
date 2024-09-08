import service from ".";

const expendApi = {
    //根据ID查询
    selectById(eid) {
        return service.get(`/admin/expend/${eid}`);
    },
    //查询所有
    selectAll() {
        return service.get('/admin/expend');
    }
};

export default expendApi;