<template>
    <el-row :gutter="20" justify="space-between">
        <el-col :span="12">
            <el-card style="opacity: 0.9;" shadow="always">
                <div style="text-align: center; margin-bottom: 15px; opacity: 0.7;">护理收入</div>
                <el-table :data="expendList" border style="width: 100%">
                    <el-table-column prop="egrade" label="护理等级" align="center" />
                    <el-table-column prop="esalary" label="等级费用" align="center" />
                    <el-table-column prop="enumber" label="人数" align="center" width="100px" />
                    <el-table-column prop="eincome" label="总收入" align="center" width="100px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="warning" @click="selectByEid(scope.row.eid)">费用变更</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-col>
        <!-- <el-col :span="12">
            <el-card style="opacity: 0.9;" shadow="always">
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
        </el-col> -->
    </el-row>

    <!-- 修改费用的对话框开始 -->
    <el-dialog v-model="updateDialogShow" title="修改费用" width="500">
        <el-form :model="expendUpdate" :rules="state.rules" ref="updateFormRef">
            <el-form-item label="每月费用" label-width="18%" prop="esalary">
                <el-input v-model="expendUpdate.esalary" autocomplete="off" @input="handleUpdateNull" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="updateDialogShow = false">取消</el-button>
                <el-button type="primary" :disabled="updateButtonDisabled" @click="update">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 修改费用的对话框结束 -->

</template>

<script setup>
import expendApi from '@/api/expendApi';
import { reactive, ref, watch } from 'vue';

//保存护理费用
const expendList = ref([]);
//修改模态款显示状态
const updateDialogShow = ref(false);

//被修改费用的信息
const expendUpdate = ref({
    eid: 0,
    egarde: '',
    esalary: '',
    enumber: '',
    eincome: ''
});

//修改方法模态款
function selectByEid(eid) {
    expendApi.selectById(eid)
        .then(resp => {
            console.log(resp);

            expendUpdate.value = resp.data;
            //显示对话框
            updateDialogShow.value = true;
        })
}

function selectAll() {
    expendApi.selectAll()
        .then(resp => {
            expendList.value = resp.data;
        })
}

function update() {
    expendApi.update(expendUpdate.value)
        .then()
}

// ---------非空校验开始------------------
//修改的确认按钮状态
const updateButtonDisabled = ref(false);
// 定义表单引用
const updateFormRef = ref(null);
//验证规则
const state = reactive({
    rules: {
        esalary: [
            { required: true, message: '费用不能为空', trigger: 'blur' },
        ]
    }
});
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

// 监听 updateDialogShow 的变化(刷新表单修改验证)
watch(updateDialogShow, (newValue) => {
  if (!newValue) {
    // 当对话框关闭时，刷新页面
    window.location.reload();
  }
});
// ---------非空校验结束------------------


selectAll();
</script>

<style scoped>
.el-row {
    margin-bottom: 20px;
}

.el-row:last-child {
    margin-bottom: 0;
}

.el-col {
    border-radius: 4px;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}
</style>