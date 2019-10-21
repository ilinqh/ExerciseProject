package lqh.hchomework.h4_11

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils

/**
 * 功能：几何变换 + 动画
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/21
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class FancyView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private val IMAGE_WIDTH = Utils.dp2px(150f)
        private val IMAGE_PADDING = Utils.dp2px(50f)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap: Bitmap

    private val camera = Camera()

    var radius: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())

        camera.rotateX(45f)
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
        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0f)
        canvas.rotate(radius)
        canvas.translate(-(IMAGE_PADDING + IMAGE_WIDTH / 2f), -(IMAGE_PADDING + IMAGE_WIDTH / 2f))

        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_WIDTH / 2f, IMAGE_PADDING + IMAGE_WIDTH / 2f)
        canvas.rotate(-radius)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-IMAGE_WIDTH, 0f, IMAGE_WIDTH, IMAGE_WIDTH)
        canvas.rotate(radius)
        canvas.translate(-(IMAGE_PADDING + IMAGE_WIDTH / 2f), -(IMAGE_PADDING + IMAGE_WIDTH / 2f))

        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
    }

}