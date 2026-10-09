package com.example.exp2

class ProgramAdviser {
    // 模拟查询模型层，根据选择的语言返回相应的结果（字符串资源ID）
    fun getAdvice(selectedLanguage: String): Int {
        // 实际开发中这里可能涉及数据库或网络查询
        return R.string.query_result_prefix
    }
}