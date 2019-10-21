package lqh.hchomework.h4_11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：动画-放大
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/21
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class CircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var radius = Utils.dp2px(50f)
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }

}