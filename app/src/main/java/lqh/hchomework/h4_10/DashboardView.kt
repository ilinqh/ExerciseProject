package lqh.hchomework.h4_10

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：仪表盘
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-10-15
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class DashboardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    companion object {
        private val RADIUS = Utils.dp2px(150f)

        private val OPEN_ANGLE = 120

        private val STROKE_WIDTH = Utils.dp2px(3f)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dash = Path()

    private lateinit var effect: PathDashPathEffect

    private lateinit var pathMeasure: PathMeasure

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = STROKE_WIDTH
        dash.addRect(0f, 0f, Utils.dp2px(2f), Utils.dp2px(10f), Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        effect = PathDashPathEffect(dash, 50f, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画弧
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            90 - OPEN_ANGLE / 2f,
            false,
            paint
        )
    }

}