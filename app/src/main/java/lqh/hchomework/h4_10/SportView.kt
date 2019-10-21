package lqh.hchomework.h4_10

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：静态仿运动界面
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/18
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class SportView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private val RADIUS = Utils.dp2px(150f)
        private val RING_WIDTH = Utils.dp2px(20f)
        private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
        private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")

        private const val text = "good"

        private val fontMetrics = Paint.FontMetrics()
    }

    private val bound = Rect()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.textSize = Utils.dp2px(100f)
        paint.typeface = Typeface.createFromAsset(getContext().assets, "Quicksand-Regular.ttf")
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画圆环
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = RING_WIDTH
        paint.color = CIRCLE_COLOR
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        // 画进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            -90f,
            225f,
            false,
            paint
        )

        // 绘制文字
        paint.getFontMetrics(fontMetrics)
        paint.style = Paint.Style.FILL
        canvas.drawText(
            text,
            width / 2f,
            height / 2f - (fontMetrics.descent + fontMetrics.ascent) / 2,
            paint
        )

        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, bound)
        canvas.drawText(text, 0f, -bound.top.toFloat(), paint)
    }

}