<template>

    <el-row>
        <el-col :span="15">
            <el-card style="opacity: 0.9;">
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                        <el-button plain type="primary">添加</el-button>
                    </el-form-item>
                    <el-form-item style="float: right;">
                        <el-input v-model="hno" placeholder="请输入要搜索的宿舍号" @input="selectByPage(1)"
                            style="width: 200px; margin-right: 20px;">
                        </el-input>
                        <el-input v-model="dname" placeholder="宿舍所在院系" @input="selectByPage(1)"
                            style="width: 200px; margin-right: 20px;" />
                    </el-form-item>
                </el-form>
                <el-table :data="pageInfo.list" border style="width: 100%">
                    <el-table-column prop="hid" label="ID" align="center" width="60px" />
                    <el-table-column prop="hno" label="宿舍号" align="center" width="120px" />
                    <el-table-column prop="department.dname" label="所在系部" align="center" width="150px" />
                    <el-table-column prop="dnumber" label="宿舍人数" align="center" width="150px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success"
                                @click="showSetHostelDialog(scope.row.hid);">分配客户</el-button>
                            <el-popconfirm title="你确定要停用该宿舍吗?" confirm-button-text="确认" cancel-button-text="取消"
                                @confirm="deleteByHid(scope.row.hid);">
                                <template #reference>
                                    <el-button size="small" type="danger">停用宿舍</el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
                <el-row class="row-bg" justify="center">
                    <el-pagination background layout="prev, pager, next" :total="pageInfo.total"
                        :page-size="pageInfo.pageSize" @change="selectByPage" style="margin-top: 20px;" />
                </el-row>
            </el-card>
        </el-col>
    </el-row>

    <!-- 分配客户的对话框开始 -->
    <el-dialog v-model="setHostelDialogShow" title="分配客户">
        <!-- data：数据源  v-model：选中项绑定值 -->
        <el-transfer v-model="selectCids" :data="allCustom" :titles="['待分配客户', '已分配客户']" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="setHostelDialogShow = false">取消</el-button>
                <el-button id="customCommit" type="primary" @click="insertHidAndCid">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 分配客户的对话框结束 -->

</template>

<script setup>
import hostelApi from '@/api/hostelApi';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';

//搜索
const hno = ref('');
const dname = ref('');


//分页信息
const pageInfo = ref({
    total: 0,
    pageInfo: 0
})

//分配客户的对话框
const setHostelDialogShow = ref(false);
//所有在宿舍客户的信息
const allCustom = ref([]);

//宿舍列表
const hostelList = ref([]);
//部门信息
const departmentList = ref([]);

//被选中的客户的cid
const selectCids = ref([]);
//需要分配客户的宿舍id
const hostelSelectHid = ref(0);



//添加宿舍的信息
const hostelAdd = ref({
    hno: '',
    did: ''
})
//修改宿舍的信息
const hostelUpdate = ref({
    hno: '',
    did: ''
})


//查询所有在院客户的信息并显示分配客户的对话框
function showSetHostelDialog(hid) {
    //查询所有在院客户的信息
    hostelApi.allCustom(hid)
        .then(resp => {
            allCustom.value = resp.data.allCustom;
            selectCids.value = resp.data.selectCids;
            hostelSelectHid.value = hid;
            setHostelDialogShow.value = true;
        })
}
//分配客户的方法
function insertHidAndCid() {
    hostelApi.insertHidAndCid(hostelSelectHid.value, selectCids.value)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage.success(resp.msg);
                setHostelDialogShow.value = false;
                selectByPage();
            } else {
                ElMessage.error(resp.msg);
            }
        });
}
//停用宿舍
function deleteByHid(hid) {
    hostelApi.delete(hid)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });
                selectByPage();
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }
        })
}


//分页查询
function selectByPage(pageNum) {
    hostelApi.selectByPage(pageNum, hno.value, dname.value)
        .then(resp => {
            pageInfo.value = resp.data
        })
}

selectByPage();
</script>

<style scoped></style>