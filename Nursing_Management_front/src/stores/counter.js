import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  // 响应式数据 - 状态 - 可以在不同的页面或者组件之间被分享
  const count = ref(0)
  // 计算属性 - getter
  const doubleCount = computed(() => count.value * 2)
  // 对状态进行修改 - action
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})
