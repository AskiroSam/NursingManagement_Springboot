<template>

    <el-row style="justify-content: center;">
        <el-col :span="15">
            <div
                style="background-color: white; border: 1px solid whi; border-radius: 50%; height: 150px; opacity: 0.9;">
                <el-countdown format="DD [days] HH:mm:ss" :value="value2">
                    <template #title>
                        <div style="display: inline-flex; align-items: center; margin-top: 20px;">
                            <el-icon style="margin-right: 4px;" :size="12">
                                <Calendar />
                            </el-icon>
                            到下个月还有：
                        </div>
                        <div class="countdown-footer">{{ value2.format('YYYY-MM-DD') }}</div>
                    </template>
                </el-countdown>
            </div>
        </el-col>
    </el-row>

    <el-row :gutter="20" justify="space-between">
        <el-col :span="12">
            <el-card style="opacity: 0.9;" shadow="always">
                <div style="text-align: center; margin-bottom: 15px; opacity: 0.7;">护理收入</div>
                <el-table :data="expendList" border style="width: 100%;">
                    <el-table-column prop="egrade" label="护理等级" align="center" />
                    <el-table-column prop="esalary" label="等级费用" align="center" />
                    <el-table-column prop="enumber" label="人数" align="center" width="100px" />
                    <el-table-column prop="eincome" label="总收入" align="center" width="100px" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <el-button size="small" type="warning" style="height: 35px;" @click="selectByEid(scope.row.eid)">费用变更</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-col>
        <el-col :span="12">
            <el-card style="opacity: 0.9; height: 200px;" shadow="always">
                <div style="text-align: center; margin-bottom: 15px; opacity: 0.7;">员工支出</div>
                <el-table :data="payoutList" border style="width: 100%">
                    <el-table-column prop="pnumber" label="员工总数" align="center" />
                    <el-table-column prop="pout" label="需支付总工资" align="center" />
                    <el-table-column label="操作" align="center">
                        <template #default="scope">
                            <!-- <el-badge class="item">
                                <el-button title="发布" class="share-button" :icon="Share" type="primary"
                                    @click="putShow(scope.row.pid)"
                                    style="height: 50px; margin-left: 40px; margin-bottom: 25px; width: 100px;" />
                            </el-badge> -->
                            <el-button type="warning"  @click="exportData" style="height: 50px; margin-left: 0px; margin-bottom: 0px; width: 150px;">导出每个员工的薪资</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-col>
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

    <!-- 发布的对话框开始 -->
    <el-dialog v-model="putOutShow" title="发布" width="500">
        <el-form :model="expendUpdate" :rules="state.rules" ref="updateFormRef">
            <el-form-item label="需支付的总工资" label-width="25%">
                <el-input v-model="payoutSee.pout" autocomplete="off" @input="handleUpdateNull" disabled />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="putOutShow = false">取消</el-button>
                <el-button type="primary" @click="push">发布</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 发布的对话框结束 -->


</template>

<script setup>
import expendApi from '@/api/expendApi';
import payoutApi from '@/api/payoutApi';
import { Share } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';
import { reactive, ref, watch } from 'vue';
import dayjs from 'dayjs'
import { Calendar } from '@element-plus/icons-vue'
import axios from 'axios';

// -------------时间插件-------------------------------------
const value = ref(Date.now() + 1000 * 60 * 60 * 7)
const value1 = ref(Date.now() + 1000 * 60 * 60 * 24 * 2)
const value2 = ref(dayjs().add(1, 'month').startOf('month'))
function reset() {
    value1.value = Date.now() + 1000 * 60 * 60 * 24 * 2
}
// -------------时间插件------------------------------------------



// ------------工资Excel导出------------------------------------
// 导出Excel
function exportData() {
    // 根据搜索条件构建 URL
    const url = `http://localhost:8080/excel/exportStaffSalary`;

    axios.get(url, {
        responseType: 'blob', // 确保响应类型为 blob
        headers: {
            'token': 'headers.token' // 替换成实际的 token
        }
    })
        .then(response => {
            // 创建一个下载链接并触发下载
            const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            const link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.setAttribute('download', 'customsSalary.xlsx'); // 指定下载文件名
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
//-----------工资Excel导出-----------------------------------------------



//保存护理费用
const expendList = ref([]);
//保存员工支出
const payoutList = ref([]);
//修改模态款显示状态
const updateDialogShow = ref(false);
//发布模态框的显示状态
const putOutShow = ref(false);

//被修改费用的信息
const expendUpdate = ref({
    eid: 0,
    egarde: '',
    esalary: '',
    enumber: '',
    eincome: ''
});
const payoutSee = ref({
    pid: '',
    pnunmber: '',
    pout: ''
})

//修改方法模态款
function selectByEid(eid) {
    expendApi.selectById(eid)
        .then(resp => {
            expendUpdate.value = resp.data;
            //显示对话框
            updateDialogShow.value = true;
        })
}
// //发布模态框
// function putShow(pid) {
//     payoutApi.selectById(pid)
//         .then(resp => {
//             payoutSee.value = resp.data;
//             putOutShow.value = true;
//         })
// }


function selectAll() {
    expendApi.selectAll()
        .then(resp => {
            expendList.value = resp.data;
        })
    payoutApi.selectAll()
        .then(resp => {
            payoutList.value = resp.data;
        })

}

//修改方法
function update() {
    expendApi.update(expendUpdate.value)
        .then(resp => {
            console.log(expendUpdate.value);

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
        })
}

// //发布方法
// function push() {
//     ElMessage({
//         message: '发布成功',
//         type: 'success',
//         duration: 1200
//     });
//     putOutShow.value = false;

// }

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
        setTimeout(() => {
            window.location.reload();
        }, 500); // 1000 毫秒（1秒）的延时
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

.item {
    margin-top: 10px;
    margin-right: 40px;
}

.el-col {
    text-align: center;
}

.countdown-footer {
    margin-top: 8px;
}
</style>