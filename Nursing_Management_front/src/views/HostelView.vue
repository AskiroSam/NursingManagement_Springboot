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
                            <el-button size="small" type="success"
                                @click="showSetHostelDialog(scope.row.hid);">分配客户</el-button>
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
                    <el-pagination background layout="prev, pager, next" 
                         :current-page="pageInfo.pageNum" 
                         :page-count="pageInfo.pages"
                         @update:current-page="selectByPage" style="margin-top: 20px;" />
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


</template>

<script setup>
import hostelApi from '@/api/hostelApi';
import { ElLoading, ElMessage } from 'element-plus';
import { computed, reactive, ref } from 'vue';

//搜索
const hno = ref('');
const dname = ref('');

//添加宿舍对话框
const addHostelShow = ref(false);


//分页信息
const pageInfo = ref({
    pages: 0,
    pageInfo: 0,
    pageNum: 0
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
    did: '',
    dname: ''
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

            // 获取所有客户数据和当前宿舍已分配的客户数据
            const allCustomData = resp.data.allCustom;
            const selectCidsData = resp.data.selectCids;

            // 获取当前宿舍ID
            const currentHostelId = hid;
            // allCustom.value = resp.data.allCustom;\

            // 已分配客户无法再分配到别的宿舍，需要先移出当前宿舍重新分配
            allCustom.value = resp.data.allCustom.map(custom => ({
                ...custom,
                disabled: selectCids.value.includes(custom.key) // 设置已分配的客户为禁用
            }));
            selectCids.value = resp.data.selectCids;
            hostelSelectHid.value = hid;
            setHostelDialogShow.value = true;
            console.log(allCustom.value);
            console.log(selectCids.value);

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