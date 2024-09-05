<template>
    <el-row>
        <el-col :span="24">
            <el-card>
                <el-button type="primary" plain style="margin-bottom: 10px;">添加</el-button>
                <el-table :data="tarvelList" border style="width: 100%">
                    <el-table-column prop="tid" label="ID" align="center" width="60px" />
                    <el-table-column prop="tlocation" label="目的地" align="center" width="100px" />
                    <el-table-column prop="tstart" label="出发时间" align="center" width="90px" />
                    <el-table-column prop="tend" label="返回时间" align="center" width="90px" />
                    <el-table-column prop="tprogress" label="路线进度" align="center" width="300px">
                        <template #default="score">
                            <el-progress :percentage="score.row.tprogress" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="tdescription" label="路线描述" align="center" width="300px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success" @click="showSetCustomDialog(scope.row.tid)">分配客户</el-button>
                            <el-button size="small" type="success" @click="showSetStaffDialog(scope.row.tid)">分配员工</el-button>
                            <el-button size="small" type="success">修改</el-button>
                            <el-popconfirm title="你确定要删除吗?" confirm-button-text="确认" cancel-button-text="取消">
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
    <el-dialog v-model="setCustomDialogShow" title="添加院系">
        <!-- data：数据源  v-model：选中项绑定值 -->
        <el-transfer v-model="selectCids" :data="allCustom" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="setCustomDialogShow = false">取消</el-button>
                <el-button type="primary" @click="insertTidAndCid">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 分配客户的对话框结束 -->

    <!-- 分配员工的对话框开始 -->
    <el-dialog v-model="setStaffDialogShow" title="添加院系">
        <el-transfer v-model="selectSids" :data="allStaff" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="setStaffDialogShow = false">取消</el-button>
                <el-button type="primary" @click="insertTidAnSid">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 分配员工的对话框结束 -->

</template>

<script setup>
import travelApi from '@/api/travelApi';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';

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

//被选中的客户的cid
const selectCids = ref([]);
//被选中的员工的sid
const selectSids = ref([]);

//需要分配客户的项目id
const customSelectTid = ref(0);
//需要分配员工的项目id
const staffSelectTid = ref(0);

//查询所有在院客户的信息并显示分配客户的对话框
function showSetCustomDialog(tid) {
    //查询所有在院客户的信息
    travelApi.allCustom()
        .then(resp => {
            allCustom.value = resp.data;
            customSelectTid.value = tid;
            setCustomDialogShow.value = true;
        })
}

//查询所有在职员工的信息并显示分配员工的对话框
function showSetStaffDialog(tid) {
    //查询所有在院客户的信息
    travelApi.allStaff()
        .then(resp => {
            allStaff.value = resp.data;
            staffSelectTid.value = tid; 
            setStaffDialogShow.value = true;
        })
}

//查询所有路线信息
function selectAll() {
    travelApi.selectAll()
        .then(resp => {
            tarvelList.value = resp.data;
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

//分配员工的方法
function insertTidAnSid() {
    travelApi.insertTidAndSid(staffSelectTid.value, selectSids.value)
        .then(resp => {
            ElMessage.success(resp.msg);
            setStaffDialogShow.value = false;
        })
}

selectAll();
</script>

<style scoped></style>