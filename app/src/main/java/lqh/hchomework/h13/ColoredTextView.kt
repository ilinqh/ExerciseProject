package lqh.hchomework.h13

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import lqh.hchomework.Utils
import java.util.*


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
class ColoredTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : AppCompatTextView(context, attrs, def) {

    companion object {
        private val COLORS = intArrayOf(
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548")
        )
        private val TEXT_SIZES = intArrayOf(16, 22, 28)
        private val random = Random()
        private val CORNER_RADIUS = Utils.dp2px(4f)
        private val X_PADDING = Utils.dp2px(16f)
        private val Y_PADDING = Utils.dp2px(8f)
    }

    val paint = Paint(ANTI_ALIAS_FLAG)

    init {
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZES[random.nextInt(3)].toFloat()
        paint.color = COLORS[random.nextInt(COLORS.size)]
        setPadding(X_PADDING.toInt(), Y_PADDING.toInt(), X_PADDING.toInt(), Y_PADDING.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            CORNER_RADIUS,
            CORNER_RADIUS,
            paint
        )
        super.onDraw(canvas)
    }

}
