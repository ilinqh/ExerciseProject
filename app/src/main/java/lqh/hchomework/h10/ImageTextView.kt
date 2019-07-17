package lqh.hchomework.h10

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：图片文字混排
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/7/17
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class ImageTextView(context: Context, attr: AttributeSet) : View(context, attr) {

    companion object {
        private val IMAGE_WIDTH = Utils.dp2px(150f).toInt()
        private val IMAGE_PADDING = Utils.dp2px(80f).toInt()
    }

    private val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, sollicitudin in maximus a, " +
                "vulputate id magna. Nulla non quam a massa sollicitudin commodo fermentum et est. Suspendisse potenti. " +
                "Praesent dolor dui, dignissim quis tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. " +
                "Quisque nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. Aenean aliquet " +
                "dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi rhoncus lacus facilisis pellentesque " +
                "nec vitae lorem. Donec et risus eu ligula dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum " +
                "primis in faucibus orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum finibus, " +
                "ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies metus at magna cursus congue. " +
                "Nam eu sem eget enim pretium venenatis. Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor."

    private val bitmap: Bitmap

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint()
    private val metrics = Paint.FontMetrics()
    private var measuredWidth = FloatArray(1)

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH)
        textPaint.textSize = Utils.sp2px(15f)
        paint.textSize = Utils.sp2px(16f)
        paint.getFontMetrics(metrics)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, width - IMAGE_WIDTH.toFloat(), IMAGE_PADDING.toFloat(), paint)

        val length = text.length
        var yOffset = paint.fontSpacing
        var usableWidth: Int

        var start = 0
        var count: Int
        while (start < length) {
            val textTop = yOffset + metrics.ascent
            val textBottom = yOffset + metrics.descent
            usableWidth = if (textTop > IMAGE_PADDING && textTop < IMAGE_PADDING + IMAGE_WIDTH ||
                textBottom > IMAGE_PADDING && textBottom < IMAGE_PADDING + IMAGE_WIDTH) {
                width - IMAGE_WIDTH
            } else {
                width
            }
            count = paint.breakText(text, start, length, true, usableWidth.toFloat(), measuredWidth)
            canvas.drawText(text, start,start + count, 0f, yOffset, paint)
            start += count
            yOffset += paint.fontSpacing
        }

    }

}