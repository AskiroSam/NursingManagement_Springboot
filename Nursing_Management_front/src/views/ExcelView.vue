<template>
    <div>
      <input type="file" ref="fileInput" style="display: none" @change="handleFileChange" />
      <button @click="triggerFileInput">选择文件并上传</button>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import axios from 'axios'
  import { ElMessage } from 'element-plus'
  
  // 定义一个 ref 来存储所选的文件
  const selectedFile = ref(null)
  const fileInput = ref(null)
  
  // 处理文件更改事件
  function handleFileChange(event) {
    const file = event.target.files[0]
    if (file) {
      selectedFile.value = file
      importData(file) // 文件选择后立即导入
    }
  }
  
  // 触发文件选择对话框
  function triggerFileInput() {
    fileInput.value.click()
  }
  
  // 导入文件的函数
  async function importData(file) {
    const formData = new FormData()
    formData.append('file', file)
  
    try {
      const response = await axios.post('http://localhost:8080/excel/importCustom', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'token': 'headers.token' // 替换成实际的 token
        }
      })
      ElMessage.success('文件导入成功')
    } catch (error) {
      ElMessage.error('文件导入失败，请稍后再试')
      console.error('文件导入失败:', error)
    }
  }
  </script>
  
  <style scoped>
  button {
    margin-top: 10px;
    padding: 5px 15px;
    background-color: #42b983;
    color: white;
    border: none;
    cursor: pointer;
  }
  
  button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  </style>
  