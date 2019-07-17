package lqh.hchomework.h09.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.h09.Utils

/**
 * 功能：刻度盘
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/7/17
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class DashBoard(context: Context, attr: AttributeSet) : View(context, attr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 刻度
     */
    private val dash = Path()

    private val bigDash = Path()
    /**
     * 圆弧
     */
    private val path = Path()

    /**
     * 测量圆弧长度
     */
    private lateinit var pathMeasure: PathMeasure

    private lateinit var pathEffect: PathDashPathEffect

    private lateinit var bigPathEffect: PathDashPathEffect

    private val RADIUS = Utils.dp2px(150f)
    private val ANGLE = 120f
    private val LENGTH = Utils.dp2px(100f)

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(3f)
        dash.addRect(0f, 0f, Utils.dp2px(2f), Utils.dp2px(10f), Path.Direction.CCW)
        bigDash.addRect(0f, 0f, Utils.dp2px(2f), Utils.dp2px(18f), Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.addArc(
            width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS,
            90 + ANGLE / 2, 360 - ANGLE
        )
        // 测量圆弧长度
        pathMeasure = PathMeasure(path, false)
        pathEffect = PathDashPathEffect(
            dash, (pathMeasure.length - Utils.dp2px(2f)) / 20, 0f,
            PathDashPathEffect.Style.ROTATE
        )

        bigPathEffect = PathDashPathEffect(
            bigDash, (pathMeasure.length - Utils.dp2px(2f)) / 5, 0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画弧度
        canvas.drawArc(
            width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS,
            90 + ANGLE / 2, 360 - ANGLE, false, paint
        )

        // 画刻度
        paint.pathEffect = pathEffect
        canvas.drawArc(
            width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS,
            90 + ANGLE / 2, 360 - ANGLE, false, paint
        )
        paint.pathEffect = null

        // 画长刻度
        paint.pathEffect = bigPathEffect
        canvas.drawArc(
            width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS,
            90 + ANGLE / 2, 360 - ANGLE, false, paint
        )
        paint.pathEffect = null

        // 画指针
        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + Math.cos(Math.toRadians(getAngleForMark(5).toDouble())).toFloat() * LENGTH,
            height / 2f + Math.sin(Math.toRadians(getAngleForMark(5).toDouble())).toFloat() * LENGTH,
            paint
        )
    }

    private fun getAngleForMark(mark: Int) = 90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark
}