<template>
    <el-row>
        <el-col :span="24">
            <el-card>
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item style="float: right;">
                        <el-input v-model="sname" placeholder="请输入要搜索的姓名" @input="selectByPage(1)" />
                    </el-form-item>
                </el-form>
                <el-table :data="pageInfo.list" border style="width: 100%">
                    <el-table-column prop="sid" label="ID" align="center" width="60px" />
                    <el-table-column prop="sno" label="工号" align="center" width="120px" />
                    <el-table-column prop="sname" label="姓名" align="center" width="70px" />
                    <el-table-column prop="sage" label="年龄" align="center" width="70px" />
                    <el-table-column prop="sgender" label="性别" align="center" width="200px" />
                    <el-table-column prop="sentrydate" label="入职日期" align="center" width="150px" />
                    <el-table-column prop="ssalary" label="基本工资" align="center" width="150px" />
                    <el-table-column prop="sstate" label="在职状态" align="center" width="150px">
                        <template #default="scope">
                            <el-tag type="primary" v-if="scope.row.sstate == 1">在职</el-tag>
                            <el-tag type="info" v-else>离职</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="department.dname" label="所在系部" align="center" width="150px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success">修改</el-button>
                            <el-popconfirm title="你确定要删除改家属吗?" confirm-button-text="确认" cancel-button-text="取消">
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
import { ref } from 'vue';
import staffApi from '@/api/staffApi';
import departmentApi from '@/api/departmentApi';

//搜索
const sname = ref('');

//分页信息
const pageInfo = ref({
    total: 0,
    pageInfo: 0
})

//员工列表
const staffList = ref([]);
//部门信息
const departmentList = ref([]);

//分页查询
function selectByPage(pageNum) {
    staffApi.selectByPage(pageNum, sname.value)
        .then(resp => {
            pageInfo.value = resp.data        
        })
}

selectByPage(1);
</script>

<style scoped></style>