package lqh.hchomework.h4_18

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import lqh.hchomework.Utils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * 功能：可缩放的 ImageView
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-11-03
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {

    companion object {
        val IMAGE_WIDTH = Utils.dp2px(300f)

        const val OVER_SCALE_FACTOR = 1.5f
    }

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val bitmap: Bitmap

    private var smallScale: Float = 1f
    private var bigScale: Float = 1f

    private var offsetX = 0f
    private var offsetY = 0f
    private var originOffsetX: Float = 0f
    private var originOffsetY: Float = 0f

    private var maxOffsetX = 0f
    private var maxOffsetY = 0f

    private val detector: GestureDetectorCompat

    private var big: Boolean = false

    var scaleFraction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    private val scroller: OverScroller

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        detector = GestureDetectorCompat(context, this)
        detector.setOnDoubleTapListener(this)

        scroller = OverScroller(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        smallScale = min(width / bitmap.width.toFloat(), height / bitmap.height.toFloat())
        bigScale = max(
            width / bitmap.width.toFloat(),
            height / bitmap.height.toFloat()
        ) * OVER_SCALE_FACTOR

        originOffsetX = (width - bitmap.width) / 2f
        originOffsetY = (height - bitmap.height) / 2f

        maxOffsetX = abs((width - bitmap.width * bigScale) / 2f)
        maxOffsetY = abs((height - bitmap.height * bigScale) / 2f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.translate(offsetX, offsetY)
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent) {
        // 显示按下效果
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        // 单次点击，抬起手指
        // 若在长按时间内，抬起手指，则会触发该方法
        // 相反，若超过长按时间后，抬起则无法触发该方法
        return false
    }

    override fun onDown(e: MotionEvent): Boolean {
        // 类似 OnTouchEvent 中的 MotionEvent.ACTION_DOWN
        // 必须返回 true，否则时间序列无法拦截
        return true
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        // 快划/ 惯性滑动
        if (big) {
            scroller.fling(
                offsetX.toInt(),
                offsetY.toInt(),
                velocityX.toInt(),
                velocityY.toInt(),
                -maxOffsetX.toInt(),
                maxOffsetX.toInt(),
                -maxOffsetY.toInt(),
                maxOffsetY.toInt()
            )
            postOnAnimation(this)
        }
        return false
    }

    override fun run() {
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
            postOnAnimation(this)
        }
    }

    /**
     * @param e1 初始按下事件
     * @param e2 当前事件
     * @param distanceX 上一个点的 X - 当前点的 X
     * @param distanceY 上一个点的 Y - 当前点的 Y
     */
    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        // 相当于 MOVE
        if (big) {
            offsetX -= distanceX
            offsetY -= distanceY
            offsetX = max(offsetX, -maxOffsetX)
            offsetX = min(offsetX, maxOffsetX)
            offsetY = max(offsetY, -maxOffsetY)
            offsetY = min(offsetY, maxOffsetY)
            invalidate()
        }
        return false
    }

    override fun onLongPress(e: MotionEvent) {
        // 长按
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        // 双击事件，第二次点击事件中，仅按下(ACTION_DOWN)时触发
        big = !big
        if (big) {
            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
        }
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        // 双击事件，第二次点击的所有事件都会触发
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        // 确认单击事件
        return false
    }

}