package lqh.hchomework.h4_16

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
 * 创建日期：2019/10/30
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class CircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {

        private val RADIUS = Utils.dp2px(80f)

        private val PADDING = Utils.dp2px(20f)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = (RADIUS + PADDING).toInt() * 2
        val mWidth = resolveSize(size, widthMeasureSpec)
        val mHeight = resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.RED)
        canvas.drawCircle(RADIUS + PADDING, RADIUS + PADDING, RADIUS, paint)
    }

}