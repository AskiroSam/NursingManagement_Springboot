<template>
  <div class="common-layout">
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside>
          <el-menu active-text-color="#ffd04b" background-color="#545c64" class="el-menu-vertical-demo"
            default-active="2" text-color="#fff" @select="toHref">
            <el-sub-menu index="1">
              <template #title>
                <el-icon>
                  <UserFilled />
                </el-icon>
                <span>客户管理</span>
              </template>
              <el-menu-item index="/cut"><el-icon><User/></el-icon>老人管理</el-menu-item>
              <el-menu-item index="/fam"><el-icon><User/></el-icon>家属管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <OfficeBuilding />
                </el-icon>
                <span>院内管理</span>
              </template>
              <el-menu-item index="/dept"><el-icon><School/></el-icon>院系管理</el-menu-item>
              <el-menu-item index="/">区域管理</el-menu-item>
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
