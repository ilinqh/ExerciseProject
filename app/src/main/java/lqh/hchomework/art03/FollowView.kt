package lqh.hchomework.art03

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * 功能：跟随手指移动
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class FollowView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mLastX = 0
    private var mLastY = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val rawX = event.rawX.toInt()
        val rawY = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = rawX - mLastX
                val deltaY = rawY - mLastY
                Log.d("OnTouchEvent", "move, deltaX: $deltaX deltaY: $deltaY")
                Log.d("OnTouchEvent", "x: $x y: $y")

                if (y <= 0) {
                    this.translationY += 1f
                } else {
                    this.translationY += deltaY
                }
                if (x <= 0) {
                    this.translationX += 1f
                } else {
                    this.translationX += deltaX
                }
            }
        }
        mLastX = rawX
        mLastY = rawY
        return true
    }

}