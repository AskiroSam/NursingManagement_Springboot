<template>
    <el-row>
        <el-col :span="20">
            <el-card style="opacity: 0.9;">
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item style="float: right;">
                        <el-input v-model="fname" placeholder="请输入要搜索的姓名" @input="selectByPage(1)" />
                    </el-form-item>
                </el-form>
                <el-table :data="pageInfo.list" border style="width: 100%">
                    <el-table-column prop="fid" label="ID" align="center" width="80px" />
                    <el-table-column prop="fname" label="姓名" align="center" width="150px" />
                    <el-table-column prop="fage" label="年龄" align="center" width="100px" />
                    <el-table-column prop="fgender" label="性别" align="center" width="100px" />
                    <el-table-column prop="fphone" label="手机号" align="center" width="250px" />
                    <el-table-column prop="custom.cname" label="家人" align="center" width="200px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success"
                                @click="showUpdateDialog(scope.row.fid)">修改</el-button>
                            <el-popconfirm title="你确定要删除家属吗?" confirm-button-text="确认" cancel-button-text="取消"
                                @confirm="deleteFamily(scope.row.fid);">
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

    <!-- 修改客户的对话框开始 -->
    <el-dialog v-model="updateDialogShow" title="修改家属" width="500">
        <el-form :model="familyUpdate" :rules="state.rules" ref="updateFormRef">
            <el-form-item label="姓名:" label-width="18%" prop="fname">
                <el-input v-model="familyUpdate.fname" autocomplete="off" style="width: 300px" @blur="handleUpdateNull" />
            </el-form-item>
            <el-form-item label="年龄:" label-width="18%" prop="fage">
                <el-input v-model="familyUpdate.fage" autocomplete="off" style="width: 300px" @blur="handleUpdateNull" />
            </el-form-item>
            <el-form-item label="性别:" label-width="18%" prop="fgender">
                <el-radio-group v-model="familyUpdate.fgender" style="width: 300px" @blur="handleUpdateNull">
                    <el-radio value="男" size="large">男</el-radio>
                    <el-radio value="女" size="large">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="手机号:" label-width="18%" prop="fphone">
                <el-input v-model="familyUpdate.fphone" autocomplete="off" style="width: 300px" @blur="handleUpdateNull" />
            </el-form-item>
            <!-- <el-form-item label="亲人:" label-width="18%" prop="cname">
                <el-input v-model="customUpdate.cname"  autocomplete="off" style="width: 300px" @input="isNullUpdateCustom" />
            </el-form-item> -->
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="updateDialogShow = false">取消</el-button>
                <el-button type="primary" :disabled="updateDisabled" @click="update">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 修改客户的对话框结束 -->

</template>

<script setup>
import familyApi from '@/api/familyApi';
import customApi from '@/api/customApi';
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';

//被修改家属的信息
const familyUpdate = ref({
    fid: '',
    fname: '',
    fage: '',
    fgender: '',
    fphone: '',
    cid: ''
})

// //被修改客户的信息
// const customUpdate = ref({
//     cid: '',
//     cname: '',
//     cage: '',
//     cgender: '',
//     cphone: '',
//     centrydate: '',
//     caddress: '',
//     did: '',
//     fid: '',
//     eid: ''
// })


//搜索
const fname = ref('');
//分页信息
const pageInfo = ref({
    pages: 0,
    pageInfo: 0,
    pageNum: 0
})

//删除对话框显示状态
const updateDialogShow = ref(false);

// //所有客户
// const customList = ref([]);



// ---------------------参数校验-----------------------
//修改的确认按钮状态
const updateDisabled = ref(false);
//定义表单引用
const updateFormRef = ref(null);
//验证规则
const state = reactive({
    rules: {
        fname: [
            { required: true, message: '请输入名称', trigger: 'blur' },
        ],
        fage: [
            { required: true, message: '请输入年龄', trigger: 'blur' },
        ],
        fgender: [
            { required: true, message: '请输入性别', trigger: 'blur' },
        ],
        fphone: [
            { required: true, message: '请输入手机号', trigger: 'blur' },
        ]
    }
});
// 处理添加数据的方法
const handleUpdateNull = async () => {
    const formEl = updateFormRef.value; // 获取 el-form 实例
    if (!formEl) return;
    // 验证表单
    await formEl.validate((valid, fields) => {
        if (valid) {
            updateDisabled.value = false;
            isNullUpdateCustom();
        } else {
            updateDisabled.value = true;
        }
    });

};

// function isNullUpdateCustom() {
//     if (customUpdate.value.cname) {
//         updateDisabled.value = false; 
//     } else {
//         updateDisabled.value = true;
//         ElMessage({
//             message: '客户姓名不能为空',
//             type: 'error',
//             duration: 1200,
//         })
//     }
    
    
// }
//-----------------------------------------------------



//分页查询
function selectByPage(pageNum) {
    familyApi.selectByPage(pageNum, fname.value)
        .then(resp => {
            pageInfo.value = resp.data;
        })
}
// //获取所有用户的信息
// function selectByPage2(pageNum) {
//     customApi.selectByPage(pageNum)
//         .then(resp => {
//             customList.value = resp.data.list;
//         })
// }

//查询所有家属并显示修改对话框
function showUpdateDialog(fid) {
    familyApi.selectById(fid)
        .then(resp => {
            familyUpdate.value = resp.data;
            updateDialogShow.value = true

            // customApi.selectById(resp.data.cid)
            //     .then(resp => {
            //         customUpdate.value = resp.data
            //         updateDialogShow.value = true
            //     })

        })
}


// //修改家属
// function update() {
//     familyApi.update(familyUpdate.value)
//         .then(resp => {
//             if (resp.code == 10000) {
//                 customApi.update(customUpdate.value)
//                     .then(resp => {
//                         if (resp.code == 10000) {
//                             ElMessage({
//                                 message: resp.msg,
//                                 type: 'success',
//                                 duration: 1200
//                             });

//                             //隐藏
//                             updateDialogShow.value = false;
//                             selectByPage(1);
//                         } else {
//                             ElMessage({
//                                 message: resp.msg,
//                                 type: 'error',
//                                 duration: 1200
//                             });
//                         }
//                     })
//             } else {
//                 ElMessage({
//                     message: resp.msg,
//                     type: 'error',
//                     duration: 1200
//                 });
//             }


//         })
// }

//修改家属
function update() {
    familyApi.update(familyUpdate.value)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200,
                });
                updateDialogShow.value = false;
                selectByPage(1);
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }


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
// selectByPage2(1);
</script>

<style scoped></style>