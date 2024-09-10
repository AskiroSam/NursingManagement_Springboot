<template>
    <el-row>
        <el-col :span="15">
            <el-card style="opacity: 0.9;">
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                        <el-button plain type="primary" @click="addHostelShow = true">添加</el-button>
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
                            <el-button size="small" type="success" @click="showDialog(scope.row.hid)">查看宿舍成员</el-button>
                            <el-popconfirm title="你确定要停用该宿舍吗(停用后将会在此删除)?" confirm-button-text="确认"
                                cancel-button-text="取消" @confirm="deleteByHid(scope.row.hid);">
                                <template #reference>
                                    <el-button size="small" type="danger">停用宿舍</el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
                <el-row class="row-bg" justify="center">
                    <el-pagination background layout="prev, pager, next" :current-page="pageInfo.pageNum"
                        :page-count="pageInfo.pages" @update:current-page="selectByPage" style="margin-top: 20px;" />
                </el-row>
            </el-card>
        </el-col>
    </el-row>

    <!-- 添加宿舍的对话框开始 -->
    <el-dialog v-model="addHostelShow" title="添加宿舍" width="500" style="width: 600px;">
        <el-form :model="hostelAdd" :rules="state.rules" ref="addFormRef">
            <el-form-item label="宿舍号：" label-width="18%" prop="hno">
                <el-input v-model="hostelAdd.hno" autocomplete="off" style="width: 400px;" @input="handleAddNull" />
            </el-form-item>
            <el-form-item label="所属部门：" label-width="18%">
                <el-input v-model="hostelAdd.dname" autocomplete="off" disabled style="width: 400px;" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addHostelShow = false">取消</el-button>
                <el-button type="primary" :disabled="addButtonDisabled" @click="insert">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 添加宿舍的对话框结束 -->

    <!-- 查看成员的对话框开始 -->
    <el-dialog v-model="checkCustomShow" title="分配宿舍成员" width="500" style="width: 600px;">
        <el-table :data="customList" style="width: 100%">
            <el-table-column prop="cname" label="姓名" width="120" align="center" />
            <el-table-column prop="cage" label="年龄" align="center" width="80" />
            <el-table-column prop="cgender" label="性别" align="center" width="80" />
            <el-table-column prop="cphone" label="手机号" align="center" width="160" />
            <el-table-column label="操作" width="80" align="center">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" circle @click="showUpdateDialog(scope.row.cid)" />
                </template>
            </el-table-column>
        </el-table>
    </el-dialog>
    <!-- 查看成员的对话框结束 -->

    <!-- 修改宿舍的对话框开始 -->
    <el-dialog v-model="updateCustomShow" title="修改宿舍" width="500" style="width: 600px;">
        <el-form :model="hostelUpdate" :rules="state.rules" ref="addFormRef">
            <el-form-item label="当前宿舍：" label-width="18%">
                <el-input v-model="hostelUpdate.hno" autocomplete="off" disabled style="width: 400px;" />
            </el-form-item>
            <el-form-item label="修改宿舍：" label-width="18%" prop="hno" @input="handleAddNull" >
                <el-input v-model="hostelUpdate.hno" autocomplete="off" style="width: 400px;" @change="findHid" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="updateCustomShow = false">取消</el-button>
                <el-button type="primary" @click="update" :disabled="addButtonDisabled">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 修改宿舍的对话框结束 -->


</template>

<script setup>
import customApi from '@/api/customApi';
import hostelApi from '@/api/hostelApi';
import { ElLoading, ElMessage } from 'element-plus';
import { computed, reactive, ref } from 'vue';
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
const hno = ref('');
const dname = ref('');

//添加宿舍对话框
const addHostelShow = ref(false);
//查看成员对话框
const checkCustomShow = ref(false);
//修改成员的对话框
const updateCustomShow = ref(false);

//所有成员
const customList = ref([]);


//分页信息
const pageInfo = ref({
    pages: 0,
    pageInfo: 0,
    pageNum: 0
})



//添加宿舍的信息
const hostelAdd = ref({
    hno: '',
    did: '',
    dname: ''
})
//修改宿舍的信息
const hostelUpdate = ref({
    hid: '',
    hno: '',
    did: '',
    dname: ''
})
//修改宿舍的信息
const customUpdate = ref({
    cname: '',
    cage: '',
    cgender: '',
    cphone: '',
    centrydate: '',
    caddress: '',
    cgender: '',
    did: '',
    fid: '',
    eid: '',
    hid: '',
    cid: ''
})

//查看宿舍成员的信息
function showDialog(hid) {
    customApi.selectByHid(hid)
        .then(resp => {
            customList.value = resp.data;
            checkCustomShow.value = true;
        })
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
//添加宿舍方法
function insert() {
    const loading = ElLoading.service({
        lock: true,
        text: '加载中',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    hostelApi.insert(hostelAdd.value)
        .then(resp => {
            loading.close();
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });

                addHostelShow.value = false;
                hostelAdd.value = {
                    tlocation: '',
                    tstart: '',
                    tend: '',
                    tdescription: ''
                }
                selectByPage();
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }
        });

}
//跟踪新宿舍号
function findHid() {
    hostelApi.selectByHno(hostelUpdate.value.hno)
        .then(resp => {
            customUpdate.value.hid = resp.data.hid;
        })
}


//查询客户的信息并显示修改对话框
function showUpdateDialog(cid) {
    customApi.selectById(cid)
        .then(resp => {
            customUpdate.value = resp.data;
            console.log(customUpdate.value.hid);
            hostelUpdate.value.hid = resp.data.hid;
            return hostelApi.selectByHid(resp.data.hid);
        })
        .then(resp => {
            hostelUpdate.value = resp.data;
            updateCustomShow.value = true;
        })
        .catch(error => {
            console.error("Error loading data", error);
        });
}

//修改宿舍方法
function update() {
    hostelApi.selectByHno(hostelUpdate.value.hno)
        .then(resp => {
            if (resp.code != 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            } else {
                customApi.update(customUpdate.value)
                    .then(resp => {
                        if (resp.code == 10000) {
                            ElMessage({
                                message: resp.msg,
                                type: 'success',
                                duration: 1200
                            });
                            updateCustomShow.value = false;
                            checkCustomShow.value = false;
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
        })

}


// ---------非空校验开始------------------
//添加的确认按钮状态
const addButtonDisabled = ref(true);
// 定义表单引用
const addFormRef = ref(null);
//验证规则
const state = reactive({
    rules: {
        hno: [
            { required: true, message: '请输入4位宿舍号', trigger: 'blur' },
            
        ]
    }
});
// 处理添加数据的方法
const handleAddNull = async () => {
    const formEl = addFormRef.value; // 获取 el-form 实例
    if (!formEl) return;

    // 根据宿舍号的首位数字设置所属部门
    const hnoFirstChar = hostelAdd.value.hno.charAt(0);
    if (hnoFirstChar === '1') {
        hostelAdd.value.dname = '护理中心'; // 修改为实际部门名
        hostelAdd.value.did = 1;
    } else if (hnoFirstChar === '2') {
        hostelAdd.value.dname = '护理分院'; // 修改为实际部门名
        hostelAdd.value.did = 2;
    } else if (hnoFirstChar == '3') {
        hostelAdd.value.dname = '药物院';
        hostelAdd.value.did = 3;
    } else {
        hostelAdd.value.dname = ''; // 如果没有匹配的条件，可以清空
    }

    // 验证表单
    await formEl.validate((valid, fields) => {
        if (valid) {
            addButtonDisabled.value = false;
        } else {
            addButtonDisabled.value = true;
        }
    });
    console.log(hostelAdd.value);

};


// ---------非空校验结束------------------



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