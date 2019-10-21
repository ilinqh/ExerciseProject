package lqh.hchomework.h4_09

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils
import kotlin.math.cos
import kotlin.math.sin

/**
 * 功能：饼状图
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class PieChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        // 扇形半径
        private val RADIUS = Utils.dp2px(150f)

        // 扇形颜色
        private val COLORS = intArrayOf(
            Color.parseColor("#448AFF"),
            Color.parseColor("#D81B60"),
            Color.parseColor("#43A047"),
            Color.parseColor("#FDD835")
        )
        // 扇形弧度
        private val ANGLES = floatArrayOf(60f, 100f, 120f, 80f)

        // 偏移的扇形下标
        private const val PULLED_INDEX = 0
        // 偏移量
        private val PULLED_LENGTH = Utils.dp2px(20f)

        private val bounds = RectF()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var currentAngle = 0f
        for (i in COLORS.indices) {
            paint.color = COLORS[i]
            if (i == PULLED_INDEX) {
                canvas.save()
                canvas.translate(
                    cos(Math.toRadians(currentAngle.toDouble() + ANGLES[i] / 2)).toFloat() * PULLED_LENGTH,
                    sin(Math.toRadians(currentAngle.toDouble() + ANGLES[i] / 2)).toFloat() * PULLED_LENGTH
                )
            }
            canvas.drawArc(
                bounds,
                currentAngle,
                ANGLES[i],
                true,
                paint
            )
            if (i == PULLED_INDEX) {
                canvas.restore()
            }
            currentAngle += ANGLES[i]
        }
    }
}