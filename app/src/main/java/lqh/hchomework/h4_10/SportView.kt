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

    }

    private val metrics = Paint.FontMetrics()

    private val bound = Rect()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.textSize = Utils.dp2px(100f)
        paint.typeface = Typeface.createFromAsset(getContext().assets, "Quicksand-Regular.ttf")
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画圆弧
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = RING_WIDTH
        paint.color = CIRCLE_COLOR
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        // 画进度
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = HIGHLIGHT_COLOR
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

        // 画文字
        paint.style = Paint.Style.FILL
        paint.getFontMetrics(metrics)
        val offset = (metrics.ascent + metrics.descent) / 2f
        canvas.drawText(text, width / 2f, height / 2f - offset, paint)
    }

}