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
            width / 2f - RADIUS - BORDER_WIDTH,
            height / 2f - RADIUS - BORDER_WIDTH,
            width / 2f + RADIUS + BORDER_WIDTH,
            height / 2f + RADIUS + BORDER_WIDTH
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.parseColor("#FDD835")
        paint.strokeWidth = BORDER_WIDTH
        canvas.drawOval(border, paint)

        val saved = canvas.saveLayer(cut, paint)
        canvas.drawOval(cut, paint)

        paint.xfermode = xfermode
        canvas.drawBitmap(
            avatar,
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            paint
        )
        paint.xfermode = null

        canvas.restoreToCount(saved)
    }

}