<template>
    <el-row :gutter="20" justify="space-between">
        <el-col :span="12">
            <el-card style="opacity: 0.9;" shadow="always">
                <el-button type="primary" plain style="margin-bottom: 10px;">添加</el-button>
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
        <el-col :span="12">
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
        </el-col>
    </el-row>
</template>

<script setup>

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