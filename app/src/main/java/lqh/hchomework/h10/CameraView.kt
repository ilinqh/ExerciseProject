package lqh.hchomework.h10

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

class CameraView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) :
    View(context, attrs, def) {

    companion object {
        private val IMAGE_WIDTH = Utils.dp2px(300f).toInt()
        private val IMAGE_PADDING = Utils.dp2px(50f).toInt()
    }

    private val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val camera by lazy {
        Camera()
    }

    private val bitmap: Bitmap

    var radius: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH)

        camera.rotateX(45f)
        // 三维旋转需要做多这一步处理，避免一些高密度手机出现糊脸效果
        camera.setLocation(0f, 0f, Utils.getZForCamera())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 三维变换太难想象了，根据常规思维逆向顺序转换
        /**
         * 常规思维 ->
         * 1. bitmap 移动到左上角原点位置
         * 2. 旋转 Camera
         * 3. bitmap 移动到右下角，恢复到原先位置
         *
         * 实际代码 -> 与常规思维相反
         */
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_WIDTH / 2f, IMAGE_PADDING + IMAGE_WIDTH / 2f)
        canvas.rotate(-radius)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-IMAGE_WIDTH.toFloat(), 0f, IMAGE_WIDTH.toFloat(), IMAGE_WIDTH.toFloat())
        canvas.rotate(radius)
        canvas.translate(-(IMAGE_PADDING + IMAGE_WIDTH / 2f), -(IMAGE_PADDING + IMAGE_WIDTH / 2f))

        canvas.drawBitmap(bitmap, IMAGE_PADDING.toFloat(), IMAGE_PADDING.toFloat(), paint)
        canvas.restore()

        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_WIDTH / 2f, IMAGE_PADDING + IMAGE_WIDTH / 2f)
        canvas.rotate(-radius)
        canvas.clipRect(-IMAGE_WIDTH.toFloat(), -IMAGE_WIDTH.toFloat(), IMAGE_WIDTH.toFloat(), 0f)
        canvas.rotate(radius)
        canvas.translate(-(IMAGE_PADDING + IMAGE_WIDTH / 2f), -(IMAGE_PADDING + IMAGE_WIDTH / 2f))

        canvas.drawBitmap(bitmap, IMAGE_PADDING.toFloat(), IMAGE_PADDING.toFloat(), paint)
        canvas.restore()


    }
}