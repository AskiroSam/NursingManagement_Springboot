<template>
    <el-row>
        <el-col :span="24">
            <el-card style="opacity: 0.9;">
                <el-button type="primary" plain style="margin-bottom: 10px;"
                    @click="addTravelShow = true">添加</el-button>
                <el-button type="primary" style="margin-bottom: 10px; float: right;"
                    @click="calculateProgress">刷新路线进度(在规定时间内显示)</el-button>
                <el-table :data="tarvelList" border style="width: 100%">
                    <el-table-column prop="tid" label="ID" align="center" width="60px" />
                    <el-table-column prop="tlocation" label="目的地" align="center" width="100px" />
                    <el-table-column prop="tstart" label="出发时间" align="center" width="90px" />
                    <el-table-column prop="tend" label="返回时间" align="center" width="90px" />
                    <el-table-column prop="tprogress" label="路线进度" align="center" width="300px">
                        <template #default="score">
                            <el-progress :text-inside="true" :stroke-width="26" :percentage="score.row.tprogress" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="tdescription" label="路线描述" align="center" width="300px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success"
                                @click="showSetCustomDialog(scope.row.tid)">分配客户</el-button>
                            <el-button size="small" type="success"
                                @click="showSetStaffDialog(scope.row.tid)">分配员工</el-button>
                            <el-button size="small" type="success" @click="selectByTid(scope.row.tid)">修改</el-button>
                            <el-popconfirm title="你确定要删除吗?" confirm-button-text="确认" cancel-button-text="取消"
                                @confirm="deleteByTid(scope.row.tid)">
                                <template #reference>
                                    <el-button size="small" type="danger">删除路线</el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-col>
    </el-row>

    <!-- 分配客户的对话框开始 -->
    <el-dialog v-model="setCustomDialogShow" title="分配客户">
        <!-- data：数据源  v-model：选中项绑定值 -->
        <el-transfer v-model="selectCids" :data="allCustom" :titles="['待分配客户', '已分配客户']" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="setCustomDialogShow = false">取消</el-button>
                <el-button id="customCommit" type="primary" @click="insertTidAndCid">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 分配客户的对话框结束 -->

    <!-- 分配员工的对话框开始 -->
    <el-dialog v-model="setStaffDialogShow" title="分配员工">
        <el-transfer v-model="selectSids" :data="allStaff" :titles="['待分配员工', '已分配员工']" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="setStaffDialogShow = false">取消</el-button>
                <el-button type="primary" @click="insertTidAnSid">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 分配员工的对话框结束 -->

    <!-- 添加路线的对话框开始 -->
    <el-dialog v-model="addTravelShow" title="添加路线" width="500">
        <el-form>
            <el-form-item label="目的地：" label-width="18%">
                <el-input v-model="travelAdd.tlocation" autocomplete="off" style="width: 300px;" />
            </el-form-item>
            <el-form-item label="出发时间：" label-width="18%">
                <el-time-select v-model="travelAdd.tstart" style="width: 300px" start="06:00" step="00:15" end="23:30"
                    placeholder="请选择时间" />
            </el-form-item>
            <el-form-item label="返回时间：" label-width="18%">
                <el-time-select v-model="travelAdd.tend" style="width: 300px" start="06:00" step="00:15" end="23:30"
                    placeholder="请选择时间" />
            </el-form-item>
            <el-form-item label="路线描述：" label-width="18%">
                <el-input v-model="travelAdd.tdescription" autocomplete="off" placeholder="若不填写，则为空" style="width: 300px;" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addTravelShow = false">取消</el-button>
                <el-button type="primary" @click="insert">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 添加路线的对话框结束 -->

    <!-- 修改路线的对话框开始 -->
    <el-dialog v-model="updateDialogShow" title="添加院系" width="500">
        <el-form>
            <el-form-item label="目的地：" label-width="18%">
                <el-input v-model="travelUpdate.tlocation" autocomplete="off" />
            </el-form-item>
            <el-form-item label="出发时间：" label-width="18%">
                <el-time-select v-model="travelUpdate.tstart" style="width: 300px" start="06:00" step="00:15" end="23:30"
                    placeholder="请选择时间" />
            </el-form-item>
            <el-form-item label="返回时间：" label-width="18%">
                <el-time-select v-model="travelUpdate.tend" style="width: 300px" start="06:00" step="00:15" end="23:30"
                    placeholder="请选择时间" />
            </el-form-item>
            <el-form-item label="路线描述：" label-width="18%">
                <el-input v-model="travelUpdate.tdescription" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="updateDialogShow = false">取消</el-button>
                <el-button type="primary" @click="update">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 修改路线的对话框结束 -->

</template>

<script setup>
import travelApi from '@/api/travelApi';
import { ElLoading, ElMessage } from 'element-plus';
import { onMounted, onUnmounted, ref, watch } from 'vue';

//存放所有路线的数组
const tarvelList = ref([]);
//分配客户的对话框
const setCustomDialogShow = ref(false);
//所有在院客户的信息
const allCustom = ref([]);

//分配员工的对话框
const setStaffDialogShow = ref(false);
//所有在职员工的信息
const allStaff = ref([]);

//添加路线对话框
const addTravelShow = ref(false);
//修改路线对话框
const updateDialogShow = ref(false);

//被选中的客户的cid
const selectCids = ref([]);
//被选中的员工的sid
const selectSids = ref([]);

//需要分配客户的项目id
const customSelectTid = ref(0);
//需要分配员工的项目id
const staffSelectTid = ref(0);

//最大客户分配数量
const maxCustomNum = 5;
//最大员工分配数量
const maxStaffNum = 2;

//进度条信息
const progressList = ref({
    tid: '',
    tprogress: ''
});

//被添加路线的信息
const travelAdd = ref({
    tlocation: '',
    tstart: '',
    tend: '',
    tprogress: '0',
    tdescription: ''
})


//被添加路线信息
const travelUpdate = ref({
    tlocation: '',
    tstart: '',
    tend: '',
    tprogress: '',
    tdescription: ''
});

//修改方法模态款
function selectByTid(tid) {
    travelApi.selectByTid(tid)
        .then(resp => {
            travelUpdate.value = resp.data;
            //显示对话框
            updateDialogShow.value = true;
        })
}

//查询所有在院客户的信息并显示分配客户的对话框
function showSetCustomDialog(tid) {
    //查询所有在院客户的信息
    travelApi.allCustom(tid)
        .then(resp => {
            allCustom.value = resp.data.allCustom;
            selectCids.value = resp.data.selectCids;
            customSelectTid.value = tid;
            setCustomDialogShow.value = true;
        })
}

//查询所有在职员工的信息并显示分配员工的对话框
function showSetStaffDialog(tid) {
    //查询所有在院客户的信息
    travelApi.allStaff(tid)
        .then(resp => {
            allStaff.value = resp.data.allStaff;
            selectSids.value = resp.data.selectSids;
            staffSelectTid.value = tid;
            setStaffDialogShow.value = true;
        })

}


//查询所有路线信息
function selectAll() {
    travelApi.selectAll()
        .then(resp => {
            tarvelList.value = resp.data;
            progressList.value = resp.data;


        })

}


//分配客户的方法
function insertTidAndCid() {
    travelApi.insertTidAndCid(customSelectTid.value, selectCids.value)
        .then(resp => {
            ElMessage.success(resp.msg);
            setCustomDialogShow.value = false;

        });
}

//添加路线方法
function insert() {
    const loading = ElLoading.service({
        lock: true,
        text: '加载中',
        background: 'rgba(0, 0, 0, 0.7)',
    })    
    travelApi.insert(travelAdd.value)    
        .then(resp => {
            loading.close();
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });

                addTravelShow.value = false;
                travelAdd.value = {
                    tlocation: '',
                    tstart: '',
                    tend: '',
                    tdescription: ''
                }
                selectAll();
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }
        });

}

//修改路线方法
function update() {
    travelApi.update(travelUpdate.value)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });

                //隐藏
                updateDialogShow.value = false;
                selectAll();
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }
        })
}

//分配员工的方法
function insertTidAnSid() {
    travelApi.insertTidAndSid(staffSelectTid.value, selectSids.value)
        .then(resp => {
            ElMessage.success(resp.msg);
            setStaffDialogShow.value = false;
        })
}

//删除路线
function deleteByTid(tid) {
    travelApi.delete(tid)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });
                selectAll();
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }
        })
}

//进度条开始-------------------------
function calculateProgress() {
    progressList.value.forEach(p => {
        if (p && p.tprogress !== undefined) {
            const tid = p.tid;

            travelApi.getTravelProgress(tid)
                .then(resp => {
                    p.tprogress = resp.progress;
                    tarvelList.value.tprogress = resp.progress;

                    travelApi.update(p);

                })
        }
    });
}
//进度条结束---------------------------------------

selectAll();
</script>

<style scoped></style>