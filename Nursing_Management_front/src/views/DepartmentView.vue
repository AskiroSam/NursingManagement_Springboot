<template>
    <el-row>
        <el-col :span="14">
            <el-card style="opacity: 0.9;">
                <el-button type="primary" plain style="margin-bottom: 10px;"
                    @click="addDialogShow = true">添加</el-button>
                <el-table :data="departmentList" border style="width: 100%">
                    <el-table-column prop="did" label="院系ID" align="center" width="100px" />
                    <el-table-column prop="dname" label="系部名称" align="center" />
                    <el-table-column prop="dlocation" label="系部位置" align="center" />
                    <el-table-column prop="personnum" label="系部人数" align="center" width="100px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success" @click="selectByDid(scope.row.did)">修改</el-button>
                            <el-popconfirm title="你确定要删除吗?" confirm-button-text="确认" cancel-button-text="取消"
                                @confirm="deleteByDid(scope.row.did)">
                                <template #reference>
                                    <el-button size="small" type="danger">删除</el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-col>
    </el-row>

    <!-- 添加院系的对话框开始 -->
    <el-dialog v-model="addDialogShow" title="添加院系" width="500">
        <el-form :model="departmentAdd" :rules="state.rules" ref="addFormRef">
            <el-form-item label="院系名称" label-width="18%" prop="dname">
                <el-input v-model="departmentAdd.dname" autocomplete="off" @input="handleInsert" />
            </el-form-item>
            <el-form-item label="院系位置" label-width="18%" prop="dlocation" @input="handleInsert">
                <el-input v-model="departmentAdd.dlocation" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addDialogShow = false">取消</el-button>
                <el-button type="primary" :disabled="isButtonDisabled" @click="insert">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 添加院系的对话框结束 -->

    <!-- 修改院系的对话框开始 -->
    <el-dialog v-model="updateDialogShow" title="添加院系" width="500">
        <el-form>
            <el-form-item label="院系名称" label-width="18%">
                <el-input v-model="departmentUpdate.dname" autocomplete="off" />
            </el-form-item>
            <el-form-item label="院系位置" label-width="18%">
                <el-input v-model="departmentUpdate.dlocation" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="updateDialogShow = false">取消</el-button>
                <el-button type="primary" @click="update">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 修改院系的对话框结束 -->

</template>

<script setup>
import departmentApi from '@/api/departmentApi';
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';
import { ElLoading } from 'element-plus'


//保存院系信息的数组
const departmentList = ref([]);

//添加模态款显示状态
const addDialogShow = ref(false);
//修改模态款显示状态
const updateDialogShow = ref(false);

//被添加院系的信息
const departmentAdd = ref({
    dname: '',
    dlocation: ''
});
//被修改院系的信息
const departmentUpdate = ref({
    did: 0,
    dname: '',
    dlocation: ''
});

//添加的确认按钮状态
const isButtonDisabled = ref(true);
// 定义表单引用
const addFormRef = ref(null);
//验证规则
const state = reactive({
    rules: {
        dname: [
            { required: true, message: '请输入院系名称', trigger: 'blur' },
        ],
        dlocation: [
            { required: true, message: '请输入部门地址', trigger: 'blur' },
        ]
    }
});
// 处理插入数据的方法
const handleInsert = async () => {
    const formEl = addFormRef.value; // 获取 el-form 实例
    if (!formEl) return;

    // 验证表单
    await formEl.validate((valid, fields) => {
        if (valid) {
            isButtonDisabled.value = false;
        } else {
            isButtonDisabled.value = true;
        }
    });
};

//添加方法
function insert() {
    const loading = ElLoading.service({
        lock: true,
        text: '加载中',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    departmentApi.insert(departmentAdd.value)
        .then(resp => {
            loading.close();
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });

                //隐藏
                addDialogShow.value = false;
                //清空
                departmentAdd.value = {
                    dname: '',
                    dlocation: ''
                }
                selectAll();
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }
        });
}


//修改方法模态款
function selectByDid(did) {
    departmentApi.selectById(did)
        .then(resp => {
            departmentUpdate.value = resp.data;
            //显示对话框
            updateDialogShow.value = true;
        })
}
//修改
function update() {
    departmentApi.update(departmentUpdate.value)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });

                //隐藏
                updateDialogShow.value = false;
                selectAll();
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }
        });
}


//获取所有院系的信息
function selectAll() {
    departmentApi.selectAll()
        .then(resp => {
            departmentList.value = resp.data;
        })
}

//删除院系
function deleteByDid(did) {
    departmentApi.delete(did)
        .then(resp => {
            if (resp.code == 10000) {
                ElMessage({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                });
                selectAll();
            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }
        });
}

//获取院系信息
selectAll();
</script>

<style scoped></style>