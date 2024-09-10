<template>
    <el-row>
        <el-col :span="24">
            <el-card style="opacity: 0.9;">
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                        <el-button plain type="primary" @click="showAddDialog">添加</el-button>
                        <el-button type="warning" plain @click="exportData">Excel导出</el-button>
                    </el-form-item>
                    <el-form-item style="float: right;">
                        <el-button :icon="Search" circle style="margin-right: 5px;" />
                        <el-input v-model="sname" placeholder="请输入要搜索的姓名" @input="selectByPage(1)"
                            style="width: 200px; margin-right: 20px;">
                        </el-input>
                        <el-input v-model="sgender" placeholder="请输入要搜索的性别" @input="selectByPage(1)"
                            style="width: 200px; margin-right: 20px;" />
                        <el-input v-model="ssalary" placeholder="请输入要搜索的薪资" @input="selectByPage(1)"
                            style="width: 200px; margin-right: 20px;" />
                    </el-form-item>
                </el-form>
                <el-table :data="pageInfo.list" border style="width: 100%">
                    <el-table-column prop="sid" label="ID" align="center" width="60px" />
                    <el-table-column prop="sno" label="工号" align="center" width="120px" />
                    <el-table-column prop="savatar" label="头像" align="center">
                        <template #default="scope">
                            <el-avatar :size="50" :src="`http://localhost:8080/upload/` + scope.row.savatar"
                                style="text-align: center;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="sname" label="姓名" align="center" width="70px" />
                    <el-table-column prop="sage" label="年龄" align="center" width="70px" />
                    <el-table-column prop="sgender" label="性别" align="center" width="100px" />
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
                    <el-pagination background layout="prev, pager, next" 
                         :current-page="pageInfo.pageNum" 
                         :page-count="pageInfo.pages"
                         @update:current-page="selectByPage" style="margin-top: 20px;" />
                </el-row>

            </el-card>
        </el-col>
    </el-row>

    <!-- 添加员工的对话框开始 -->
    <el-dialog v-model="addDialogShow" title="添加员工" width="500">
        <el-form :model="staffAdd" :rules="state.rules" ref="addFormRef">
            <el-form-item label="工号:" label-width="18%" prop="sno">
                <el-input v-model="staffAdd.sno" autocomplete="off" style="width: 300px" @input="handleAddNull" />
            </el-form-item>
            <el-form-item label="姓名:" label-width="18%" prop="sname">
                <el-input v-model="staffAdd.sname" autocomplete="off" style="width: 300px" @input="handleAddNull" />
            </el-form-item>
            <el-form-item label="年龄:" label-width="18%" prop="sage">
                <el-input v-model="staffAdd.sage" autocomplete="off" style="width: 300px" @input="handleAddNull" />
            </el-form-item>
            <el-form-item label="性别:" label-width="18%" prop="sgender">
                <el-radio-group v-model="staffAdd.sgender" style="width: 300px" @change="handleAddNull">
                    <el-radio value="男" size="large">男</el-radio>
                    <el-radio value="女" size="large">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="入职时间:" label-width="18%" prop="sentrydate">
                <el-date-picker v-model="staffAdd.sentrydate" type="date" placeholder="请选择入职日期" format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD" style="width: 300px" @change="handleAddNull" />
            </el-form-item>
            <el-form-item label="基本工资:" label-width="18%" prop="ssalary">
                <el-input v-model="staffAdd.ssalary" autocomplete="off" style="width: 300px" @input="handleAddNull" />
            </el-form-item>
            <el-form-item label="院系:" label-width="18%" prop="did">
                <el-select v-model="staffAdd.did" placeholder="请选择院系" size="large" style="width: 300px">
                    <el-option v-for="(department, index) in departmentList" :key="index" :label="department.dname"
                        :value="department.did" @click="handleAddNull" />
                </el-select>
            </el-form-item>
            <el-form-item label="头像:" label-width="18%" prop="savatar">
                <el-upload class="avatar-uploader" action="http://localhost:8080/admin/upload" name="pic" :headers="headers"
                    :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" @change="handleAddNull">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addDialogShow = false">取消</el-button>
                <el-button type="primary" :disabled="addButtonDisabled" @click="insert">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 添加客户的对话框结束 -->

    <!-- 修改员工的对话框开始 -->
    <el-dialog v-model="updateDialogShow" title="修改员工" width="500">
        <el-form :model="staffUpdate" :rules="state.rules" ref="updateFormRef">
            <el-form-item label="工号:" label-width="18%" prop="sno">
                <el-input v-model="staffUpdate.sno" autocomplete="off" style="width: 300px" disabled />
            </el-form-item>
            <el-form-item label="姓名:" label-width="18%" prop="sname">
                <el-input v-model="staffUpdate.sname" autocomplete="off" style="width: 300px" @blur="handleUpdateNull" />
            </el-form-item>
            <el-form-item label="年龄:" label-width="18%" prop="sage">
                <el-input v-model="staffUpdate.sage" autocomplete="off" style="width: 300px" @blur="handleUpdateNull" />
            </el-form-item>
            <el-form-item label="性别:" label-width="18%" prop="sgender">
                <el-radio-group v-model="staffUpdate.sgender" style="width: 300px" @input="handleUpdateNull">
                    <el-radio value="男" size="large">男</el-radio>
                    <el-radio value="女" size="large">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="入职时间:" label-width="18%" prop="sentrydate">
                <el-date-picker v-model="staffUpdate.sentrydate" type="date" placeholder="请选择入职日期" format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD" style="width: 300px" @input="handleUpdateNull" />
            </el-form-item>
            <el-form-item label="基本工资:" label-width="18%" prop="ssalary">
                <el-input v-model="staffUpdate.ssalary" autocomplete="off" style="width: 300px" @blur="handleUpdateNull" />
            </el-form-item>
            <el-form-item label="院系:" label-width="18%" prop="did">
                <el-select v-model="staffUpdate.did" placeholder="请选择院系" size="large" style="width: 300px">
                    <el-option v-for="(department, index) in departmentList" :key="index" :label="department.dname"
                        :value="department.did" @input="handleUpdateNull" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="updateDialogShow = false">取消</el-button>
                <el-button type="primary" :disabled="updateButtonDisabled" @click="update">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 修改客户的对话框结束 -->

</template>

<script setup>
import { computed, reactive, ref } from 'vue';
import staffApi from '@/api/staffApi';
import departmentApi from '@/api/departmentApi';
import { ElLoading, ElMessage } from 'element-plus';
import axios from 'axios';
//图标
import {
    Check,
    Delete,
    Edit,
    Message,
    Search,
    Star,
} from '@element-plus/icons-vue'

//搜索
const sname = ref('');
const sgender = ref('');
const ssalary = ref('');

//上传图片使用的token
const headers = computed(() => {
    let token = sessionStorage.getItem('token');
    return {
        token
    }
})

//分页信息
const pageInfo = ref({
    pages: 0,
    pageInfo: 0,
    pageNum: 0
})



// ----------------Excel----------------------------------------
// 导出Excel
function exportData() {
    // 根据搜索条件构建 URL
    const url = `http://localhost:8080/excel/exportStaff?sname=${sname.value}&sgender=${sgender.value}&ssalary=${ssalary.value}`;
    
    axios.get(url, {
        responseType: 'blob', // 确保响应类型为 blob
        headers: {
            'token': 'your_token_here' // 替换成实际的 token
        }
    })
    .then(response => {
        // 创建一个下载链接并触发下载
        const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedostaff.spreadsheetml.sheet' });
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.setAttribute('download', 'staff.xlsx'); // 指定下载文件名
        document.body.appendChild(link);
        link.click();
        
        // 清理临时链接
        document.body.removeChild(link);
    })
    .catch(error => {
        // 错误处理
        ElMessage.error('导出 Excel 失败，请稍后再试');
        console.error('导出 Excel 失败:', error);
    });
}
// ----------------Excel----------------------------------------



//员工列表
const staffList = ref([]);
//部门信息
const departmentList = ref([]);

//添加对话框显示状态
const addDialogShow = ref(false);
//修改对话框显示状态
const updateDialogShow = ref(false);

//上传图片的地址
const imageUrl = ref("");

//添加员工的信息
const staffAdd = ref({
    sno: '',
    sname: '',
    sage: '',
    sgender: '',
    sentrydate: '',
    ssalary: '',
    did: '',
    savatar: ''
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
    savatar: ''
})


// ---------非空校验开始------------------
//添加的确认按钮状态
const addButtonDisabled = ref(true);
//修改的确认按钮状态
const updateButtonDisabled = ref(false);
// 定义表单引用
const addFormRef = ref(null);
const updateFormRef = ref(null);
//验证规则
const state = reactive({
    rules: {
        sno: [
            { required: true, message: '请输入工号', trigger: 'blur' },
        ],
        sname: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        sage: [
            { required: true, message: '请输入年龄', trigger: 'blur' },
        ],
        sgender: [
            { required: true, message: '请输入性别', trigger: 'blur' },
        ],
        sentrydate: [
            { required: true, message: '请输入入职日期', trigger: 'blur' },
        ],
        ssalary: [
            { required: true, message: '请输入基本工资', trigger: 'blur' },
        ],
        did: [
            { required: true, message: '请选择部门', trigger: 'blur' },
        ],
        savatar: [
            { required: true, message: '请上传头像', trigger: 'blur' },
        ],
    }
});
// 处理添加数据的方法
const handleAddNull = async () => {
    const formEl = addFormRef.value; // 获取 el-form 实例
    if (!formEl) return;

    // 验证表单
    await formEl.validate((valid, fields) => {
        if (valid) {
            addButtonDisabled.value = false;
        } else {
            addButtonDisabled.value = true;
        }
    });
};
// 处理修改数据的方法
const handleUpdateNull = async () => {
    const formEl = updateFormRef.value; // 获取 el-form 实例  
    if (!formEl) return;

    // 验证表单
    await formEl.validate((valid, fields) => {
        if (valid) {       
            updateButtonDisabled.value = false;
        } else {
            updateButtonDisabled.value = true;
        }
    });
};
// ---------非空校验结束------------------

//分页查询
function selectByPage(pageNum) {
    staffApi.selectByPage(pageNum, sname.value, sgender.value, ssalary.value)
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

//上传图片之前的回调
function beforeAvatarUpload(rawFile) {
    if (rawFile.type !== 'image/jpeg') {
        ElMessage.error('图片仅支持jpg格式')
        return false
    } else if (rawFile.size / 1024 / 1024 > 2) {
        ElMessage.error('图片不能超过2M')
        return false
    }
    return true
}
//成功上传之后的回调
function handleAvatarSuccess(resp, uploadFile) {
    if (resp.code == 10000) {
        ElMessage.success(resp.msg);
        imageUrl.value = "http://localhost:8080/upload/" + resp.data;
        staffAdd.value.savatar = resp.data;
    }
    console.log(staffAdd.value.savatar);




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
            loading.close();
            if (resp.code == 10000) {
                ElMessage({
                    message: "员工添加成功",
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
                    savatar: ''
                };
                imageUrl.value = '';
                selectByPage(1);
                //刷新部门人数
                departmentApi.selectAll();                
            } else {
                ElMessage({
                    message: resp.msg,
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
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
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

<style scoped>
.avatar-uploader,
.avatar {
    width: 178px;
    height: 178px;
    display: block;
    border: 1px dotted #dcdfe6;
    border-radius: 5px;
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
}
</style>