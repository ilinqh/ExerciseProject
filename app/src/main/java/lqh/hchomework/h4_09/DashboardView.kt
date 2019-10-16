package lqh.hchomework.h4_09

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils
import kotlin.math.cos
import kotlin.math.sin

/**
 * 功能：刻度盘
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class DashboardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        val RADIUS = Utils.dp2px(150f)

        /**
         * 仪表盘空白部分对应弧度
         */
        const val ANGLE = 120

        val STROKE_WIDTH = Utils.dp2px(4f)

        val DASH_WIDTH = Utils.dp2px(2f)
        val DASH_HEIGHT = Utils.dp2px(10f)

        /**
         * 仪表盘刻度数量
         */
        const val SECTION_COUNT = 20

        val LENGTH = Utils.dp2px(100f)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // 圆弧 path
    private val path = Path()
    private lateinit var pathMeasure: PathMeasure
    private lateinit var pathEffect: PathDashPathEffect

    /**
     * 刻度
     */
    private val dash = Path()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = STROKE_WIDTH
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + ANGLE / 2f,
            360f - ANGLE
        )
        // 测量圆弧长度
        pathMeasure = PathMeasure(path, false)
        val pathLength = pathMeasure.length

        pathEffect = PathDashPathEffect(
            dash,
            (pathLength - DASH_WIDTH) / SECTION_COUNT,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制圆弧
        canvas.drawPath(path, paint)

        // 绘制刻度
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        // 画指针
        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + cos(Math.toRadians(getAngleForMark(5).toDouble())).toFloat() * LENGTH,
            height / 2f + sin(Math.toRadians(getAngleForMark(5).toDouble())).toFloat() * LENGTH,
            paint
        )
    }

    private fun getAngleForMark(mark: Int) = 90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark

}