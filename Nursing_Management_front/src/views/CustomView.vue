<template>
    <el-row>
        <el-col :span="24">
            <el-card>
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                        <el-button plain type="primary" @click="showAddDialog">添加</el-button>
                    </el-form-item>
                    <el-form-item style="float: right;">
                        <el-input v-model="cname" placeholder="请输入要搜索的姓名" @input="selectByPage(1)" />
                    </el-form-item>
                </el-form>
                <el-table :data="pageInfo.list" border style="width: 100%">
                    <el-table-column prop="cid" label="ID" align="center" width="50px" />
                    <el-table-column prop="cname" label="姓名" align="center" />
                    <el-table-column prop="cage" label="年龄" align="center" width="60px" />
                    <el-table-column prop="cgender" label="性别" align="center" width="60px" />
                    <el-table-column prop="cphone" label="手机号" align="center" />
                    <el-table-column prop="centrydate" label="入院日期" align="center" />
                    <el-table-column prop="cstate" label="在院状态" align="center" width="90px">
                        <template #default="scope">
                            <el-tag type="primary" v-if="scope.row.cstate == 1">在院</el-tag>
                            <el-tag type="info" v-else>离院</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="caddress" label="家庭住址" align="center" />
                    <el-table-column prop="department.dname" label="院系" align="center" />
                    <el-table-column prop="family.fname" label="家属" align="center" />
                    <el-table-column prop="expend.egrade" label="护理等级" align="center" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success"
                                @click="showUpdateDialog(scope.row.cid)">修改</el-button>
                            <el-popconfirm title="你确定要删除改客户吗?" confirm-button-text="确认" cancel-button-text="取消"
                                @confirm="deleteCustom(scope.row.cid);">
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

    <!-- 添加客户的对话框开始 -->
    <el-dialog v-model="addDialogShow" title="添加客户" width="500">
        <el-form>
            <el-form-item label="姓名:" label-width="18%">
                <el-input v-model="customAdd.cname" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="年龄:" label-width="18%">
                <el-input v-model="customAdd.cage" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="性别:" label-width="18%">
                <el-radio-group v-model="customAdd.cgender" style="width: 300px">
                    <el-radio value="男" size="large">男</el-radio>
                    <el-radio value="女" size="large">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="手机号:" label-width="18%">
                <el-input v-model="customAdd.cphone" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="入院时间:" label-width="18%">
                <el-date-picker v-model="customAdd.centrydate" type="date" placeholder="请选择入职日期" format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD" style="width: 300px" />
            </el-form-item>
            <el-form-item label="家庭住址:" label-width="18%">
                <el-input v-model="customAdd.caddress" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="院系:" label-width="18%">
                <el-select v-model="customAdd.did" placeholder="请选择院系" size="large" style="width: 300px">
                    <el-option v-for="(department, index) in departmentList" :key="index" :label="department.dname"
                        :value="department.did" :disabled="isDisabled(department)" />
                </el-select>
            </el-form-item>
            <el-form-item label="家属姓名:" label-width="18%">
                <el-input v-model="familyAdd.fname" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="家属年龄:" label-width="18%">
                <el-input v-model="familyAdd.fage" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="家属性别:" label-width="18%">
                <el-input v-model="familyAdd.fgender" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="家属手机:" label-width="18%">
                <el-input v-model="familyAdd.fphone" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="护理等级:" label-width="18%">
                <el-radio-group v-model="customAdd.eid" style="width: 300px">
                    <el-radio :value="1" size="large">一级</el-radio>
                    <el-radio :value="2" size="large">二级</el-radio>
                </el-radio-group>
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

    <!-- 修改客户的对话框开始 -->
    <el-dialog v-model="updateDialogShow" title="修改客户" width="500">
        <el-form>
            <el-form-item label="姓名:" label-width="18%">
                <el-input v-model="customUpdate.cname" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="年龄:" label-width="18%">
                <el-input v-model="customUpdate.cage" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="性别:" label-width="18%">
                <el-radio-group v-model="customUpdate.cgender" style="width: 300px">
                    <el-radio value="男" size="large">男</el-radio>
                    <el-radio value="女" size="large">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="手机号:" label-width="18%">
                <el-input v-model="customUpdate.cphone" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="入院时间:" label-width="18%">
                <el-date-picker v-model="customUpdate.centrydate" type="date" placeholder="请选择入职日期" format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD" style="width: 300px" />
            </el-form-item>
            <el-form-item label="家庭住址:" label-width="18%">
                <el-input v-model="customUpdate.caddress" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="部门:" label-width="18%">
                <el-select v-model="customUpdate.did" placeholder="请选择部门" size="large" style="width: 300px">
                    <el-option v-for="(department, index) in departmentList" :key="index" :label="department.dname"
                        :value="department.did" :disabled="isDisabled(department)" />
                </el-select>
            </el-form-item>
            <el-form-item label="家属:" label-width="18%">
                <el-input v-model="familyUpdate.fname" autocomplete="off" style="width: 300px" />
            </el-form-item>
            <el-form-item label="护理等级:" label-width="18%">
                <el-radio-group v-model="customUpdate.eid" style="width: 300px">
                    <el-radio :value="1" size="large">一级</el-radio>
                    <el-radio :value="2" size="large">二级</el-radio>
                </el-radio-group>
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
import customApi from '@/api/customApi';
import departmentApi from '@/api/departmentApi';
import expendApi from '@/api/expendApi';
import familyApi from '@/api/familyApi';
import { ElLoading, ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';

//搜索
const cname = ref('');
//分页信息
const pageInfo = ref({
    total: 0,
    pageInfo: 0
});

//被添加客户信息
const customAdd = ref({
    cname: '',
    cage: '',
    cgender: '男',
    cphone: '',
    centrydate: '2024-09-03',
    caddress: '',
    did: '',
    fid: '',
    eid: ''
});

//被添加家属的信息
const familyAdd = ref({
    fname: '',
    fage: '',
    fgender: '',
    fphone: '',
    cid: ''
})


//被修改客户的信息
const customUpdate = ref({
    cid: '',
    cname: '',
    cage: '',
    cgender: '',
    cphone: '',
    centrydate: '',
    caddress: '',
    did: '',
    fid: '',
    eid: ''
})

//被修改家属的信息
const familyUpdate = ref({
    fid: '',
    fname: '',
    fage: '',
    fgender: '',
    fphone: '',
    cid: ''
})


//所有部门
const departmentList = ref([]);
//所有家属
const familyList = ref([]);
//添加对话框显示状态
const addDialogShow = ref(false);
//删除对话框显示状态
const updateDialogShow = ref(false);

//分页查询
function selectByPage(pageNum) {
    customApi.selectByPage(pageNum, cname.value)
        .then(resp => {
            pageInfo.value = resp.data;
        })
}

// 判断某个部门是否禁用
function isDisabled(department) {
    return department.did === 3;
}


//查询所有部门并显示添加对话框
function showAddDialog() {
    departmentApi.selectAll()
        .then(resp => {
            departmentList.value = resp.data;
            addDialogShow.value = true
        })
    familyApi.selectByPage()
        .then(resp => {
            familyList.value = resp.data.list;
        })
}

//查询所有部门即员工的信息并显示修改对话框
function showUpdateDialog(cid) {
    departmentApi.selectAll()
        .then(resp => {
            departmentList.value = resp.data;

            customApi.selectById(cid)
                .then(resp => {
                    customUpdate.value = resp.data;
                    console.log(customUpdate);
                    familyApi.selectById(resp.data.fid)
                        .then(resp => [
                            familyUpdate.value = resp.data,
                            updateDialogShow.value = true,
                        ])
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
    customApi.insert(customAdd.value)
        .then(resp => {
            if (resp.code == 10000) {
                const newCid = resp.data.cid;
                familyAdd.value.cid = newCid;

                familyApi.insert(familyAdd.value)
                    .then(resp => {
                        if (resp.code == 10000) {
                            const newFid = resp.data.fid;

                            customApi.updateFid(newCid, newFid)
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
                                        //清空
                                        customAdd.value = {
                                            cname: '',
                                            cage: '',
                                            cgender: '男',
                                            cphone: '',
                                            centrydate: '2024-09-03',
                                            caddress: '',
                                            did: '',
                                            fid: '',
                                            eid: ''
                                        };
                                        familyAdd.value = {
                                            fname: '',
                                            fage: '',
                                            fgender: '',
                                            fphone: '',
                                            cid: ''
                                        }
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
                    })
            }
        })
}

//修改方法
function update() {
    customApi.update(customUpdate.value)
        .then(resp => {

            familyApi.update(familyUpdate.value)
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
                    } else {
                        ElMessage({
                            message: resp.msg,
                            type: 'error',
                            duration: 1200
                        });
                    }
                })

        });
}


//删除客户 - 设置客户离院
function deleteCustom(cid) {
    customApi.delete(cid)
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