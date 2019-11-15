package lqh.hchomework.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.R
import lqh.hchomework.Utils

/**
 * 功能：闪光灯
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/22
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class FlashLightView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private val RADIUS = Utils.dp2px(150f)

        private val DASH_WIDTH = Utils.dp2px(2f)
        private val DASH_HEIGHT = Utils.dp2px(10f)

        private const val COUNT = 36

        private val LINE_WIDTH = Utils.dp2px(20f)
        private val LINE_HEIGHT = Utils.dp2px(20f)
    }

    private val dash = Path()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // 圆弧 path
    private val path = Path()
    private lateinit var pathMeasure: PathMeasure
    private lateinit var pathEffect: PathDashPathEffect

    private val bitmap: Bitmap

    private val drawFilter: PaintFlagsDrawFilter

    var alpha = 255
        set(value) {
            field = value
            invalidate()
        }

    init {
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW)

        bitmap = Utils.getDrawableRes(resources, R.drawable.icon_flash, Utils.dp2px(60f).toInt())

        drawFilter =
            PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.addCircle(width / 2f, height / 2f, RADIUS, Path.Direction.CCW)
        pathMeasure = PathMeasure(path, true)
        pathEffect = PathDashPathEffect(
            dash,
            pathMeasure.length / COUNT,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawFilter = drawFilter
        canvas.drawColor(Color.BLACK)

        // 散点
        paint.color = Color.parseColor("#F4EE2A")
        paint.pathEffect = pathEffect
        paint.alpha = 255 - alpha
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        // 画闪电标志
        paint.alpha = 255
        canvas.drawBitmap(
            bitmap,
            width / 2f - Utils.dp2px(30f),
            height / 2f - Utils.dp2px(30f),
            paint
        )

        // 画斜线
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = Utils.dp2px(6f)
        paint.alpha = alpha
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(
            width / 2f - LINE_WIDTH,
            height / 2f - LINE_HEIGHT,
            width / 2 + LINE_WIDTH,
            height / 2f + LINE_HEIGHT,
            paint
        )
    }
}