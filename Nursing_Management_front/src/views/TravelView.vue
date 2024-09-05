<template>
    <el-row>
        <el-col :span="24">
            <el-card>
                <el-button type="primary" plain style="margin-bottom: 10px;">添加</el-button>
                <el-table :data="tarvelList" border style="width: 100%">
                    <el-table-column prop="tid" label="ID" align="center" width="60px" />
                    <el-table-column prop="tlocation" label="目的地" align="center" width="100px" />
                    <el-table-column prop="tstart" label="出发时间" align="center" width="90px" />
                    <el-table-column prop="tend" label="返回时间" align="center" width="90px" />
                    <el-table-column prop="tprogress" label="路线进度" align="center" width="300px">
                        <template #default="score">
                            <el-progress :percentage="score.row.tprogress" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="tdescription" label="路线描述" align="center" width="300px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="success">分配客户</el-button>
                            <el-button size="small" type="success">分配员工</el-button>
                            <el-button size="small" type="success">修改</el-button>
                            <el-popconfirm title="你确定要删除吗?" confirm-button-text="确认" cancel-button-text="取消">
                                <template #reference>
                                    <el-button size="small" type="danger">删除路线</el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-col>
    </el-row>

    <!-- 分配客户的对话框开始 -->
    <el-dialog v-model="setCustomDialogShow" title="添加院系" width="500">
        <el-form>
            <el-form-item label="院系名称" label-width="18%">
                <el-input v-model="departmentAdd.dname" autocomplete="off" />
            </el-form-item>
            <el-form-item label="院系位置" label-width="18%">
                <el-input v-model="departmentAdd.dlocation" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="setCustomDialogShow = false">取消</el-button>
                <el-button type="primary" @click="insert">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 分配客户的对话框结束 -->

    <!-- 分配员工的对话框开始 -->
    <el-dialog v-model="setStaffDialogShow" title="添加院系" width="500">
        <el-form>
            <el-form-item label="院系名称" label-width="18%">
                <el-input v-model="departmentAdd.dname" autocomplete="off" />
            </el-form-item>
            <el-form-item label="院系位置" label-width="18%">
                <el-input v-model="departmentAdd.dlocation" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="setStaffDialogShow = false">取消</el-button>
                <el-button type="primary" @click="insert">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 分配员工的对话框结束 -->

</template>

<script setup>
import travelApi from '@/api/travelApi';
import { ref } from 'vue';

//存放所有路线的数组
const tarvelList = ref([]);
//分配客户的对话框
const setCustomDialogShow = ref(false);
//所有在院客户的信息
const allCustom = ref([]);

//分配员工的对话框
const setStaffDialogShow = ref(false);
//所有在职员工的信息
const allStaff = ref([]);

//查询所有在院客户的信息并显示分配客户的对话框
function setCustomDialogShow() {
    //查询所有在院客户的信息
    travelApi.allCustom()
        .then(resp => {
            allCustom.value = resp.data;
            setTravelDialogShow = true;
        })
}

//查询所有在职员工的信息并显示分配员工的对话框
function setStaffDialogShow() {
    //查询所有在院客户的信息
    travelApi.allStaff()
        .then(resp => {
            allStaff.value = resp.data;
            setStaffDialogShow = true;
        })
}


//查询所有路线信息
function selectAll() {
    travelApi.selectAll()
        .then(resp => {
            tarvelList.value = resp.data;
        })
}

selectAll();
</script>

<style scoped></style>