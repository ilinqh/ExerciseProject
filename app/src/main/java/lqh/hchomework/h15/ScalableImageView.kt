package lqh.hchomework.h15

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.OverScroller
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GestureDetectorCompat
import lqh.hchomework.Utils
import kotlin.math.max
import kotlin.math.min

/**
 * 功能：可缩放的 ImageView
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-08-20
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class ScalableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : AppCompatImageView(context, attrs, def), Runnable {

    companion object {
        private val IMAGE_WIDTH = Utils.dp2px(300f)

        private const val OVER_SCALE_FACTOR = 1.5f
    }

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private var originalOffsetX: Float = 0f
    private var originalOffsetY: Float = 0f

    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    private var isBig: Boolean = false

    private var smallScale = 1f
    private var bigScale = 1f

    private val bitmap: Bitmap

    private val detector: GestureDetectorCompat

    private val scaleDetector: ScaleGestureDetector
    private val scaleListener by lazy {
        object : ScaleGestureDetector.OnScaleGestureListener{
            override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector?) {
            }

            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                return false
            }

        }
    }

    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    private val scroller: OverScroller

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())

        scroller = OverScroller(context)
        detector =
            GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent?): Boolean {
                    return true
                }

                override fun onDoubleTap(e: MotionEvent): Boolean {
                    if (!isBig) {
                        val whiteHeight = (height - bitmap.height * smallScale) / 2f
                        if (e.y > whiteHeight && e.y < height - whiteHeight) {
                            offsetX = (e.x - width / 2f) * (1 - bigScale / smallScale)
                            offsetY = (e.y - height / 2f) * (1 - bigScale / smallScale)
                            fixOffsets()
                            animator.start()
                            isBig = !isBig
                        }
                    } else {
                        animator.reverse()
                        isBig = !isBig
                    }
                    return super.onDoubleTap(e)
                }

}