package lqh.hchomework.h4_18

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
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

    /**
     * 最大偏移量，防止移除边界
     */
    private var maxOffsetX = 0f
    private var maxOffsetY = 0f

    private val detector: GestureDetectorCompat

    private var big: Boolean = false

    private var currentScale: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var scaleAnimator: ObjectAnimator? = null
        get() {
            if (field == null) {
                field = ObjectAnimator.ofFloat(this, "currentScale", 0f)
            }
            field?.setFloatValues(smallScale, bigScale)
            return field
        }

    private val scroller: OverScroller

    private val scaleDetector: ScaleGestureDetector

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        detector = GestureDetectorCompat(context, this)
        detector.setOnDoubleTapListener(this)

        scroller = OverScroller(context)
        scaleDetector = ScaleGestureDetector(context, MyScaleListener())
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

        currentScale = smallScale
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var result = scaleDetector.onTouchEvent(event)
        if (!scaleDetector.isInProgress) {
            result = detector.onTouchEvent(event)
        }
        return result
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
                maxOffsetY.toInt(),
                Utils.dp2px(20f).toInt(),
                Utils.dp2px(20f).toInt()
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
            fixOffset()
            invalidate()
        }
        return false
    }

    private fun fixOffset() {
        offsetX = max(offsetX, -maxOffsetX)
        offsetX = min(offsetX, maxOffsetX)
        offsetY = max(offsetY, -maxOffsetY)
        offsetY = min(offsetY, maxOffsetY)
    }

    override fun onLongPress(e: MotionEvent) {
        // 长按
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        // 双击事件，第二次点击事件中，仅按下(ACTION_DOWN)时触发
        big = !big
        if (big) {
            offsetX = (e.x - width / 2f) * (1 - bigScale / smallScale)
            offsetY = (e.y - height / 2f) * (1 - bigScale / smallScale)
            fixOffset()
            scaleAnimator?.start()
        } else {
            scaleAnimator?.reverse()
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

    inner class MyScaleListener : ScaleGestureDetector.OnScaleGestureListener {

        private var initialCurrentScale = 0f

        /**
         * @return 是否消费缩放时间
         */
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            initialCurrentScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // 放缩倍数     detector.scaleFactor
            // 放缩中心     detector.focusX
            // 放缩中心     detector.focusY
            currentScale = initialCurrentScale * detector.scaleFactor
            invalidate()
            return false
        }

    }
}

