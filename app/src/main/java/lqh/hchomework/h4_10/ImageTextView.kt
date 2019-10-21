package lqh.hchomework.h4_10

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：图片文字混排
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/18
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class ImageTextView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private val IMAGE_WIDTH = Utils.dp2px(150f).toInt()
        private val IMAGE_PADDING = Utils.dp2px(80f).toInt()

        private const val text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, sollicitudin in maximus a, " +
                    "vulputate id magna. Nulla non quam a massa sollicitudin commodo fermentum et est. Suspendisse potenti. " +
                    "Praesent dolor dui, dignissim quis tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. " +
                    "Quisque nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. Aenean aliquet " +
                    "dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi rhoncus lacus facilisis pellentesque " +
                    "nec vitae lorem. Donec et risus eu ligula dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum " +
                    "primis in faucibus orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum finibus, " +
                    "ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies metus at magna cursus congue. " +
                    "Nam eu sem eget enim pretium venenatis. Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor."
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val metrics = Paint.FontMetrics()
    private var measuredWidth = FloatArray(1)


    private val bitmap: Bitmap

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH)
        paint.textSize = Utils.sp2px(16f)
        paint.getFontMetrics(metrics)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画头像
        canvas.drawBitmap(bitmap, (width - IMAGE_WIDTH).toFloat(), IMAGE_PADDING.toFloat(), paint)

        // 画文字
        var usableWidth: Int
        // 当前行纵向偏移量
        var yOffset = paint.fontSpacing
        var start = 0

        // 文字长度
        val length = text.length
        // 当前行文字数量
        var count: Int
        while (start < length) {
            val textTop = yOffset + metrics.ascent
            val textBottom = yOffset + metrics.descent

            usableWidth = if (textBottom < IMAGE_PADDING || textTop > IMAGE_PADDING + IMAGE_WIDTH) {
                width
            } else {
                width - IMAGE_WIDTH
            }

            count = paint.breakText(text, start, length, true, usableWidth.toFloat(), measuredWidth)
            canvas.drawText(text, start, start + count, 0f, yOffset, paint)
            start += count
            yOffset += paint.fontSpacing
        }
    }

}