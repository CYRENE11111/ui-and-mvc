package com.example.exp2

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var llContainer: LinearLayout
    private lateinit var tvResult: TextView
    private lateinit var spinnerLanguage: Spinner
    private var textViewCount = 0 // 用于动态生成TextView的文本计数

    // 初始化Model
    private val adviserModel = ProgramAdviser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 绑定View
        val btnAdd = findViewById<Button>(R.id.btn_add)
        val btnQuery = findViewById<Button>(R.id.btn_query)
        llContainer = findViewById(R.id.ll_container)
        tvResult = findViewById(R.id.tv_result)
        spinnerLanguage = findViewById(R.id.spinner_language)

        // 初始化区2的下拉选择框数据
        val languageAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.language_options,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerLanguage.adapter = languageAdapter

        // ================= 区1：动态添加TextView交互 =================
        btnAdd.setOnClickListener {
            textViewCount++

            // 动态创建TextView
            val newTextView = TextView(this).apply {
                // 使用字符串资源，附带动态参数（%1$d）
                text = getString(R.string.dynamic_text_prefix, textViewCount)
                textSize = 18f
                setPadding(16, 16, 16, 16)
            }

            // 设置布局参数并添加到LinearLayout中
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            llContainer.addView(newTextView, params)

            // 自动滚动到底部，提升用户体验
            val scrollView = llContainer.parent as ScrollView
            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }

        // ================= 区2：MVC模式查询交互 =================
        btnQuery.setOnClickListener {
            // 1. 获取下拉框选中的计算机语言
            val selectedLanguage = spinnerLanguage.selectedItem.toString()

            // 2. 通过Controller调用Model层获取数据（返回的是资源ID，避免硬编码）
            val adviceResId = adviserModel.getAdvice(selectedLanguage)

            // 3. 将Model返回的结果更新到View层，输出选中的语言名字
            tvResult.text = getString(adviceResId, selectedLanguage)
        }
    }
}