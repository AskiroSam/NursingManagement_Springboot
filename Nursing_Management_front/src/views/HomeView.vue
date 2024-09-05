<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div>
          <img src="https://s21.ax1x.com/2024/09/05/pAZMVB9.png" style="width: 250px; margin-top: 12px;"></img>
        </div>
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
              <el-menu-item index="/"><el-icon>
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
          </el-menu>
        </el-aside>
        <el-main>
          <RouterView />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import router from '@/router';
import { RouterView, RouterLink } from 'vue-router';

//跳转
function toHref(indexPath) {
  router.push(indexPath);
}
</script>

<style scoped>
.el-menu {
  background-color: #000000;
  border-bottom: none !important;

}

.el-header {
  background-color: #000000;
}

</style>