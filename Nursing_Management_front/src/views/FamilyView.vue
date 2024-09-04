<template>
    <el-row>
        <el-col :span="17">
            <el-card>
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item style="float: right;">
                        <el-input v-model="fname" placeholder="请输入要搜索的姓名" @input="selectByPage(1)" />
                    </el-form-item>
                </el-form>
                <el-table :data="pageInfo.list" border style="width: 100%">
                    <el-table-column prop="fid" label="ID" align="center" width="60px" />
                    <el-table-column prop="fname" label="姓名" align="center" width="120px" />
                    <el-table-column prop="fage" label="年龄" align="center" width="70px" />
                    <el-table-column prop="fgender" label="性别" align="center" width="70px" />
                    <el-table-column prop="fphone" label="手机号" align="center" width="200px" />
                    <el-table-column prop="custom.cname" label="家人" align="center" width="150px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success"
                                @click="showUpdateDialog(scope.row.cid)">修改</el-button>
                            <el-popconfirm title="你确定要删除改家属吗?" confirm-button-text="确认" cancel-button-text="取消"
                                @confirm="deleteFamily(scope.row.fid);">
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
import familyApi from '@/api/familyApi';
import customApi from '@/api/customApi';
import { ref } from 'vue';
import { ElMessage } from 'element-plus';

//搜索
const fname = ref('');
//分页信息
const pageInfo = ref({
    total: 0,
    pageInfo: 0
})

//所有客户
const customList = ref([]);

//分页查询
function selectByPage(pageNum) {
    familyApi.selectByPage(pageNum, fname.value)
        .then(resp => {
            pageInfo.value = resp.data;
        })
}

//删除家属
function deleteFamily(fid) {
    familyApi.delete(fid)
        .then(resp => {
            console.log(resp);
            
            if (resp.code == 10000) {
                ElMessage.success(resp.msg);
                selectByPage(1)
            } else {
                ElMessage.error(resp.msg);
            }
        })
}

selectByPage(1);
</script>

<style scoped></style>