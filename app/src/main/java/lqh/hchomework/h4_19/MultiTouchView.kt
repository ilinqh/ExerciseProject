package lqh.hchomework.h4_19

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/11/8
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class MultiTouchView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val bitmap: Bitmap = Utils.getAvatar(resources, Utils.dp2px(200f).toInt())

    private var offsetX = 0f
    private var offsetY = 0f

    private var downX = 0f
    private var downY = 0f

    private var originOffsetX = 0f
    private var originOffsetY = 0f

    private var trackingPointerId = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                originOffsetX = offsetX
                originOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(trackingPointerId)
                offsetX = event.getX(index) - downX + originOffsetX
                offsetY = event.getY(index) - downY + originOffsetY
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                trackingPointerId = event.getPointerId(event.actionIndex)
                downX = event.getX(trackingPointerId)
                downY = event.getY(trackingPointerId)
                originOffsetX = offsetX
                originOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerId = event.getPointerId(event.actionIndex)
                if (pointerId == trackingPointerId) {
                    val newIndex = if (event.actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    trackingPointerId = event.getPointerId(newIndex)

                    downX = event.getX(trackingPointerId)
                    downY = event.getY(trackingPointerId)
                    originOffsetX = offsetX
                    originOffsetY = offsetY
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }
}