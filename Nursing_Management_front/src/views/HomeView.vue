<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div>
          <img src="https://s21.ax1x.com/2024/09/05/pAZMVB9.png" style="width: 250px; margin-top: 12px;"></img>
        </div>
        <el-button type="danger" round style="float: right; margin-top: -35px;"
          @click="deleteDialog = true">退出登录</el-button>
        <el-dialog v-model="deleteDialog" title="确认退出？" width="500" style="background-color: #ffffff; opacity: 0.9;">
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="deleteDialog = false">取消</el-button>
              <el-button type="primary" @click="logout">确认</el-button>
            </div>
          </template>
        </el-dialog>
        <el-button type="info" title="请联系我" :icon="Message" circle style="float: right; margin-top: -35px; margin-right: 100px;" />
      </el-header>
      <el-container>
        <el-aside>
          <el-menu active-text-color="#ffd04b" background-color="#261E47" class="el-menu-vertical-demo"
            default-active="2" text-color="#fff" @select="toHref">
            <el-sub-menu index="1">
              <template #title>
                <el-icon>
                  <UserFilled />
                </el-icon>
                <span>客户管理</span>
              </template>
              <el-menu-item index="/cut"><el-icon>
                  <User />
                </el-icon>老人管理</el-menu-item>
              <el-menu-item index="/fam"><el-icon>
                  <User />
                </el-icon>家属管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <Avatar />
                </el-icon>
                <span>员工管理</span>
              </template>
              <el-menu-item index="/staff"><el-icon>
                  <User />
                </el-icon>员工列表</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="3">
              <template #title>
                <el-icon>
                  <OfficeBuilding />
                </el-icon>
                <span>院系管理</span>
              </template>
              <el-menu-item index="/dept"><el-icon>
                  <School />
                </el-icon>院系部门</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="4">
              <template #title>
                <el-icon>
                  <PriceTag />
                </el-icon>
                <span>服务管理</span>
              </template>
              <el-menu-item index="/travel"><el-icon>
                  <Bicycle />
                </el-icon>出行管理</el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main style="background-image: url(../../images/1.jpg);">
          <RouterView />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import router from '@/router';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { RouterView, RouterLink } from 'vue-router';

//退出对话框
const deleteDialog = ref(false);

//退出
function logout() {
  ElMessage.success({
    message: '退出成功',
    duration: 1200,
    onClose: () => { //提示信息消失时间回调
      //删除sessionStorage中的token
      sessionStorage.removeItem('token');
      //跳转到登录页
      router.push('/login');
    }
  })
}

//跳转
function toHref(indexPath) {
  router.push(indexPath);
}

//图标
import {
  Check,
  Delete,
  Edit,
  Message,
  Search,
  Star,
} from '@element-plus/icons-vue'
</script>

<style scoped>
/* 去掉下划线，并设置颜色 */
.el-menu {
  background-color: #000000;
  border-bottom: none !important;

}

.el-header {
  background-color: #000000;
}
</style>