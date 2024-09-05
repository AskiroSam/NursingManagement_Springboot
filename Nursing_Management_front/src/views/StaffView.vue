<template>
    <el-row>
        <el-col :span="24">
            <el-card style="opacity: 0.9;">
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                        <el-button plain type="primary" @click="showAddDialog">添加</el-button>
                    </el-form-item>
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

    <!-- 添加员工的对话框开始 -->
    <el-dialog v-model="addDialogShow" title="添加员工" width="500">
        <el-form>
            <el-form-item label="工号:" label-width="18%">
                <el-input v-model="staffAdd.sno" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="姓名:" label-width="18%">
                <el-input v-model="staffAdd.sname" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="年龄:" label-width="18%">
                <el-input v-model="staffAdd.sage" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="性别:" label-width="18%">
                <el-radio-group v-model="staffAdd.sgender" style="width: 300px">
                    <el-radio value="男" size="large">男</el-radio>
                    <el-radio value="女" size="large">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="入职时间:" label-width="18%">
                <el-date-picker v-model="staffAdd.sentrydate" type="date" placeholder="请选择入职日期" format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD" style="width: 300px" />
            </el-form-item>
            <el-form-item label="基本工资:" label-width="18%">
                <el-input v-model="staffAdd.ssalary" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="院系:" label-width="18%">
                <el-select v-model="staffAdd.did" placeholder="请选择院系" size="large" style="width: 300px">
                    <el-option v-for="(department, index) in departmentList" :key="index" :label="department.dname"
                        :value="department.did" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addDialogShow = false">取消</el-button>
                <el-button type="primary" @click="insert">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 添加客户的对话框结束 -->

    <!-- 修改员工的对话框开始 -->
    <el-dialog v-model="updateDialogShow" title="修改员工" width="500">
        <el-form>
            <el-form-item label="工号:" label-width="18%">
                <el-input v-model="staffUpdate.sno" autocomplete="off" style="width: 300px" disabled />
            </el-form-item>
            <el-form-item label="姓名:" label-width="18%">
                <el-input v-model="staffUpdate.sname" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="年龄:" label-width="18%">
                <el-input v-model="staffUpdate.sage" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="性别:" label-width="18%">
                <el-radio-group v-model="staffUpdate.sgender" style="width: 300px">
                    <el-radio value="男" size="large">男</el-radio>
                    <el-radio value="女" size="large">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="入职时间:" label-width="18%">
                <el-date-picker v-model="staffUpdate.sentrydate" type="date" placeholder="请选择入职日期" format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD" style="width: 300px" />
            </el-form-item>
            <el-form-item label="基本工资:" label-width="18%">
                <el-input v-model="staffUpdate.ssalary" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="院系:" label-width="18%">
                <el-select v-model="staffUpdate.did" placeholder="请选择院系" size="large" style="width: 300px">
                    <el-option v-for="(department, index) in departmentList" :key="index" :label="department.dname"
                        :value="department.did" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="updateDialogShow = false">取消</el-button>
                <el-button type="primary" @click="update">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 修改客户的对话框结束 -->

</template>

<script setup>
import { ref } from 'vue';
import staffApi from '@/api/staffApi';
import departmentApi from '@/api/departmentApi';
import { ElLoading, ElMessage } from 'element-plus';

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

//添加对话框显示状态
const addDialogShow = ref(false);
//修改对话框显示状态
const updateDialogShow = ref(false);

//添加员工的信息
const staffAdd = ref({
    sno: '',
    sname: '',
    sage: '',
    sgender: '',
    sentrydate: '',
    ssalary: '',
    did: '',
})

//修改员工的信息
const staffUpdate = ref({
    sno: '',
    sname: '',
    sage: '',
    sgender: '',
    sentrydate: '',
    ssalary: '',
    did: '',
})


//分页查询
function selectByPage(pageNum) {
    staffApi.selectByPage(pageNum, sname.value)
        .then(resp => {
            pageInfo.value = resp.data
        })
}

//查询所有部门并显示添加对话框
function showAddDialog() {
    departmentApi.selectAll()
        .then(resp => {
            departmentList.value = resp.data;
            addDialogShow.value = true
        })
}

//查询所有部门并显示修改对话框
function showUpdateDialog(sid) {
    departmentApi.selectAll()
        .then(resp => {
            departmentList.value = resp.data;

            staffApi.selectById(sid)
                .then(resp => {
                    staffUpdate.value = resp.data;
                    updateDialogShow.value = true
                })
        })

}

//添加方法
function insert() {
    const loading = ElLoading.service({
        lock: true,
        text: '加载中',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    staffApi.insert(staffAdd.value)
        .then(resp => {
            if (resp.code == 10000) {
                loading.close();
                ElMessage({
                    message: "客户和家属信息提交成功",
                    type: 'success',
                    duration: 1200
                });

                //隐藏
                addDialogShow.value = false;
                staffAdd.value = {
                    sno: '',
                    sname: '',
                    sage: '',
                    sgender: '',
                    sentrydate: '',
                    ssalary: '',
                    did: '',
                };
                selectByPage(1);
                //刷新部门人数
                departmentApi.selectAll();
            } else {
                ElMessage({
                    message: "提交失败，请重试",
                    type: 'error',
                    duration: 1200
                });
            }
        })
}

//修改员工
function update() {
    staffApi.update(staffUpdate.value)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });
                //隐藏
                updateDialogShow.value = false;
                selectByPage(1);
                //刷新部门人数
                departmentApi.selectAll();
            }
        })
}

//删除员工（设置离职）
function delteteStaff(sid) {
    staffApi.delete(sid)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage.success(resp.msg);
                selectByPage(1)
                //刷新部门人数
                departmentApi.selectAll();
            } else {
                ElMessage.error(resp.msg);
            }
        })
}

selectByPage(1);
</script>

<style scoped></style>