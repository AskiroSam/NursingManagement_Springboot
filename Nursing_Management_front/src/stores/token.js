import { defineStore } from "pinia";
import { computed, ref } from "vue";

export const useTokenStore = defineStore("token", () => {
    //定义state - 需要分享的数据都要定义为state
    const token = ref(null);

    //定义getter - 获取state/获取state经过转换之后的值
    const tokenStr = computed(() => {
        return token.value;
    })

    //定义action - 修改state
    function update(tk) {
        token.value = tk;
    }

    //重置state
    function $reset() {
        token.value = null;
    } 

    return {token, tokenStr, update, $reset};
}, {
    persist: {
        key: 'token',
        storage: sessionStorage,
        paths: ['token']
    }
})
