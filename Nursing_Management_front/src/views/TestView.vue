<template>
  <el-form :model="departmentAdd" :rules="state.rules" ref="addFormRef">
    <el-form-item prop="dname">
      <el-input v-model="departmentAdd.dname" type="textarea" @blur="submitForm" />
    </el-form-item>
  </el-form>

  <el-form :model="departmentAdd" :rules="state.rules" ref="addFormRef">
    <el-form-item label="院系名称" label-width="18%" prop="dname">
      <el-input v-model="departmentAdd.dname" autocomplete="off" @blur="submitForm" />
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive, ref } from 'vue'

const departmentAdd = ref({
  dname: '',
});


// 定义表单引用
const addFormRef = ref(null);

//验证规则
const state = reactive({
  // ruleForm: {
  //   dname: '',
  // },
  rules: {
    dname: [
      { required: true, message: 'Please input activity form', trigger: 'blur' },
      { min: 3, message: 'min: 3', trigger: 'blur' },
    ],
  }
})

const submitForm = async () => {
  const formEl = addFormRef.value;
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!', fields)
    }
  })
}
</script>