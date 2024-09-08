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
                            <el-button size="small" type="warning" @click="selectByEid(scope.row.cid)">费用变更</el-button>
                            <!-- <el-popconfirm title="你确定要变更吗?" confirm-button-text="确认" cancel-button-text="取消"
                                @confirm="updateEid(scope.row.eid)">
                                <template #reference>
                                    
                                </template>
</el-popconfirm> -->
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
    <el-dialog v-model="updateDialogShow" title="修改院系" width="500">
        <el-form :model="departmentUpdate" :rules="state.rules" ref="updateFormRef">
            <el-form-item label="院系名称" label-width="18%" prop="dname">
                <el-input v-model="departmentUpdate.dname" autocomplete="off" @input="handleUpdateNull" />
            </el-form-item>
            <el-form-item label="院系位置" label-width="18%" prop="dlocation">
                <el-input v-model="departmentUpdate.dlocation" autocomplete="off" @input="handleUpdateNull" />
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
import { ref } from 'vue';

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

}
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