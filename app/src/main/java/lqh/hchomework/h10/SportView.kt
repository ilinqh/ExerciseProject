package lqh.hchomework.h10

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
 * 创建日期：2019/7/17
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class SportView(context: Context, attr: AttributeSet) : View(context, attr) {

    companion object {
        private val RING_WIDTH = Utils.dp2px(20f)
        private val RADIUS = Utils.dp2px(150f)
        private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
        private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
        private const val text = "good"
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var bounds = Rect()
    var metrics = Paint.FontMetrics()

    init {
        paint.textSize = Utils.sp2px(100f)
        paint.typeface = Typeface.createFromAsset(getContext().assets, "Quicksand-Regular.ttf")
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画环形
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = RING_WIDTH
        paint.color = CIRCLE_COLOR
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        // 画进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f,
            225f,
            false,
            paint
        )
        paint.strokeCap = Paint.Cap.BUTT

        // 绘制文字
        paint.style = Paint.Style.FILL
        paint.getFontMetrics(metrics)
        val offset = (metrics.ascent + metrics.descent) / 2f
        canvas.drawText(text, width / 2f, height / 2f - offset, paint)

        // 绘制文字：贴边
        paint.textAlign = Paint.Align.LEFT
//        paint.textSize = Utils.sp2px(50f)
//        paint.getTextBounds(text, 0, text.length, bounds)
//        canvas.drawText(text, -bounds.left.toFloat(), -bounds.top.toFloat(), paint)
//
//        val textOffset = paint.fontSpacing
        paint.textSize = Utils.dp2px(15f)
        paint.getTextBounds(text, 0, text.length, bounds)
        canvas.drawText(text, -bounds.left.toFloat(), -bounds.top + paint.fontSpacing, paint)
    }
}