package lqh.hchomework.h4_18

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import lqh.hchomework.Utils
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
    GestureDetector.OnGestureListener {

    companion object {
        val IMAGE_WIDTH = Utils.dp2px(300f)
    }

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val bitmap: Bitmap

    private var smallScale: Float = 1f
    private var bigScale: Float = 1f

    private val decetor: GestureDetectorCompat

    init {
        bitmap = Utils.getAvatar(resources, Utils.dp2px(300f).toInt())
        decetor =
            GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {

            })
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        smallScale = min(width / bitmap.width.toFloat(), height / bitmap.height.toFloat())
    }

    override fun onShowPress(e: MotionEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDown(e: MotionEvent): Boolean {
        // 类似 OnTouchEvent 中的 MotionEvent.Down
        return true
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        // 惯性滑动
        return false
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLongPress(e: MotionEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}