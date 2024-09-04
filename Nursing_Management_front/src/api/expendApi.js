import service from ".";

const expendApi = {
    //根据ID查询
    selectById(eid) {
        return service.get(`/admin/expend/${eid}`);
    }
};

export default expendApi;