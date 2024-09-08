<template>
    <el-row>
        <el-col :span="24">
            <div class="flex flex-wrap gap-4">
                <el-card style="width: 480px" shadow="always">Always</el-card>
            </div>
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