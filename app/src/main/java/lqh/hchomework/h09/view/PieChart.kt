package lqh.hchomework.h09.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.h09.Utils


/**
 * 功能：扇形图
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/7/17
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class PieChart(context: Context, attr: AttributeSet) : View(context, attr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val RADIUS = Utils.dp2px(150f)
    // 偏移量
    private val PULLED_LENGTH = Utils.dp2px(20f)
    // 扇形矩阵
    private val bounds = RectF()
    // 扇形颜色
    private val COLORS = intArrayOf(
        Color.parseColor("#448AFF"),
        Color.parseColor("#D81B60"),
        Color.parseColor("#43A047"),
        Color.parseColor("#FDD835")
    )
    // 扇形弧度
    private val ANGLES = intArrayOf(60, 100, 120, 80)
    // 偏移的扇形下标
    private val PULLED_INDEX = 1

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(width / 2f - RADIUS, height / 2f - RADIUS, width / 2f + RADIUS, height / 2f + RADIUS)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var currentAngle = 0
        for (i in COLORS.indices) {
            paint.color = COLORS[i]
            if (i == PULLED_INDEX) {
                canvas.save()
                canvas.translate(
                    Math.cos(Math.toRadians(currentAngle.toDouble() + ANGLES[i] / 2)).toFloat() * PULLED_LENGTH,
                    Math.sin(Math.toRadians(currentAngle.toDouble() + ANGLES[i] / 2)).toFloat() * PULLED_LENGTH
                )
            }
            canvas.drawArc(bounds, currentAngle.toFloat(), ANGLES[i].toFloat(), true, paint)
            if (i == PULLED_INDEX) {
                canvas.restore()
            }
            currentAngle += ANGLES[i]
        }
    }

}