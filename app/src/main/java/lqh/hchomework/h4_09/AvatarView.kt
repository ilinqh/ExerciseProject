package lqh.hchomework.h4_09

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：圆形头像
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class AvatarView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {

        private val RADIUS = Utils.dp2px(120f)
        private val BORDER_WIDTH = Utils.dp2px(4f)

        private val cut = RectF()
        private val border = RectF()

        private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }

    private var avatar: Bitmap
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        avatar = Utils.getAvatar(resources, (RADIUS * 2).toInt())
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cut.set(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS
        )
        border.set(
            width / 2f - RADIUS - BORDER_WIDTH / 2,
            height / 2f - RADIUS - BORDER_WIDTH / 2,
            width / 2f + RADIUS + BORDER_WIDTH / 2,
            height / 2f + RADIUS + BORDER_WIDTH / 2
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawOval(cut, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(
            avatar,
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            paint
        )
        paint.xfermode = null

        paint.color = Color.parseColor("#FDD835")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = BORDER_WIDTH
        canvas.drawArc(border, 0f, 360f, false, paint)

    }

}