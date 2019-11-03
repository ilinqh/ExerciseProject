package lqh.hchomework.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-11-02
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class TouchView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val TAG = "TOUCH_VIEW"
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "ACTION_DOWN")
                println("ACTION_DOWN")
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "ACTION_UP")
                println("ACTION_UP")
                performClick()
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "ACTION_MOVE")
                println("ACTION_MOVE")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "ACTION_CANCEL")
                println("ACTION_CANCEL")
            }
            else -> {
                Log.d(TAG, "HHH")
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        Log.e(TAG, "performClick")
        return super.performClick()
    }

}