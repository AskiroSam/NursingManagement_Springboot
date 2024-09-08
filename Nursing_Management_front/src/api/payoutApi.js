import service from ".";

const payoutApi = {
    //根据ID查询
    selectById(pid) {
        return service.get(`/admin/payout/${pid}`);
    },
    //查询所有
    selectAll() {
        return service.get('/admin/payout');
    }
};

export default payoutApi;