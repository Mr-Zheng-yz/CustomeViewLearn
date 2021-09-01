package com.example.customviewprojdct.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.recyclerview.widget.RecyclerView
import com.example.customviewprojdct.R
import com.example.customviewprojdct.adapter.ClassButtonAdapter
import com.example.customviewprojdct.adapter.getFlexManager
import com.example.customviewprojdct.extensions.showToast

open class BaseClassActivity : AppCompatActivity() {

    lateinit var fl_content: FrameLayout
    lateinit var rv_button: RecyclerView

    val buttonAdapter by lazy {
        ClassButtonAdapter(::onButtonItemClick)
    }

    private val cacheView = HashMap<String, View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (useDefaultOnCreate()) {
            setContentView(R.layout.activity_view_content)
            fl_content = findViewById(R.id.fl_content)
            rv_button = findViewById(R.id.rv_button)
            rv_button.apply {
                layoutManager = getFlexManager(this@BaseClassActivity)
                adapter = buttonAdapter
            }
            buttonAdapter.data.addAll(initData())
            buttonAdapter.notifyDataSetChanged()
        }
    }

    open fun useDefaultOnCreate() = true

    open fun initData(): MutableList<Triple<String, String, Class<out View>?>> {
        return mutableListOf()
    }

    open fun onButtonItemClick(triple: Triple<String, String, Class<out View>?>) {
        triple.third?.let { clazz ->
            getInstance(clazz)?.let { view ->
                replaceView(view)
                onButtonItemExtraEvent(view)
            } ?: showToast("view为空！")
        }
    }

    open fun onButtonItemExtraEvent(view: View) { //首次添加
    }

    fun getInstance(clazz: Class<out View>): View? {
        if (cacheView.containsKey(clazz.simpleName)) {
            return cacheView.get(clazz.simpleName)
        }
        val constructor = clazz.getConstructor(Context::class.java)
        constructor.isAccessible = true
        val view = constructor.newInstance(this@BaseClassActivity)
        cacheView.put(clazz.simpleName, view)
        return view
    }

    fun replaceView(view: View): Boolean {
        val has = fl_content.contains(view)
        if (!has) {
            fl_content.removeAllViews()
            fl_content.addView(view)
        }
        return has
    }

}