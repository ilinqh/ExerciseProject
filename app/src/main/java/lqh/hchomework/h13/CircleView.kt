package lqh.hchomework.h13

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-08-14
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0) : View(context, attrs, def) {

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val radius = Utils.dp2px(80f)

    private val PADDING = Utils.dp2px(20f)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = (PADDING + radius).toInt() * 2
        val measuredWidth = resolveSize(size, widthMeasureSpec)
        val measuredHeight = resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLUE
        canvas.drawCircle(PADDING + radius, PADDING + radius, radius, paint)
    }
}