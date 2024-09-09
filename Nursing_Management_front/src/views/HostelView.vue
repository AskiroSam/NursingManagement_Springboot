<template>

    <el-row>
        <el-col :span="24">
            <el-card style="opacity: 0.9;">
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                        <!-- <el-button plain type="primary">添加</el-button> -->
                    </el-form-item>
                    <!-- <el-form-item style="float: right;">
                        <el-button :icon="Search" circle style="margin-right: 5px;" />
                        <el-input v-model="sname" placeholder="请输入要搜索的姓名" @input="selectByPage(1)"
                            style="width: 200px; margin-right: 20px;">
                        </el-input>
                        <el-input v-model="sgender" placeholder="请输入要搜索的性别" @input="selectByPage(1)"
                            style="width: 200px; margin-right: 20px;" />
                        <el-input v-model="ssalary" placeholder="请输入要搜索的薪资" @input="selectByPage(1)"
                            style="width: 200px; margin-right: 20px;" />
                    </el-form-item> -->
                </el-form>
                <el-table :data="pageInfo.list" border style="width: 100%">
                    <el-table-column prop="hid" label="ID" align="center" width="60px" />
                    <el-table-column prop="hno" label="宿舍号" align="center" width="120px" />
                    <el-table-column prop="department.dname" label="所在系部" align="center" width="150px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success"
                                @click="showUpdateDialog(scope.row.sid);">修改</el-button>
                            <el-popconfirm title="你确定要删除该员工吗?" confirm-button-text="确认" cancel-button-text="取消"
                                @confirm="delteteStaff(scope.row.sid);">
                                <template #reference>
                                    <el-button size="small" type="danger">删除</el-button>
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

</template>

<script setup>
import hostelApi from '@/api/hostelApi';
import { ref } from 'vue';

//搜索
const hno = ref('');
const dname = ref('');


//分页信息
const pageInfo = ref({
    total: 0,
    pageInfo: 0
})


//宿舍列表
const hostelList = ref([]);
//部门信息
const departmentList = ref([]);

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