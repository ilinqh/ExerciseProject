package lqh.hchomework.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import lqh.hchomework.Utils
import kotlin.math.min
import kotlin.math.sin

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-10-31
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class WaveView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var radius: Float = 0f

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val path by lazy {
        Path()
    }

    private val wavePath by lazy {
        Path()
    }
    private val wavePath2 by lazy {
        Path()
    }

    var waveHeight: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var index = 0
    /**
     * 振幅
     */
    private val amplitude = 20
    private val wPath by lazy {
        Path()
    }
    private val wPath2 by lazy {
        Path()
    }

    var mTheta: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        radius = size / 2f
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        paint.isFilterBitmap = true
        path.addCircle(width / 2f, height / 2f, radius, Path.Direction.CCW)
        canvas.clipPath(path)

        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(2f)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)

        wavePath.reset()
        wPath.reset()
        if (waveHeight > height) {
            waveHeight = height.toFloat()
        }

        index = 0
        wPath.moveTo(0f, waveHeight)
        while (index < width) {
            val endY: Float =
                (amplitude * sin((index.toFloat() / width + mTheta) * 2f * Math.PI) + waveHeight).toFloat()
            wPath.lineTo(index.toFloat(), endY)
            index++
        }
//        canvas.drawPath(wPath, paint)

        paint.style = Paint.Style.FILL

        wavePath.moveTo(0f, waveHeight)
        wavePath.addPath(wPath)
//        wavePath.lineTo(width.toFloat(), waveHeight)
        wavePath.lineTo(width.toFloat(), height.toFloat())
        wavePath.lineTo(0f, height.toFloat())
        wavePath.close()
        paint.color = Color.CYAN
        canvas.drawPath(wavePath, paint)

        paint.style = Paint.Style.STROKE
        wavePath2.reset()
        wPath2.reset()
        if (waveHeight > height) {
            waveHeight = height.toFloat()
        }

        index = 0
        wPath2.moveTo(0f, waveHeight)
        while (index < width) {
            val endY: Float =
                (amplitude * sin((index.toFloat() / width + mTheta + Math.PI / 2f) * 2f * Math.PI) + waveHeight).toFloat()
            wPath2.lineTo(index.toFloat(), endY)
            index++
        }

        paint.style = Paint.Style.FILL
        wavePath2.apply {
            moveTo(0f, waveHeight)
            addPath(wPath2)
            lineTo(width.toFloat(), height.toFloat())
            lineTo(0f, height.toFloat())
            close()
        }
        paint.color = Color.YELLOW
        canvas.drawPath(wavePath2, paint)

//        canvas.drawColor(Color.RED)
        canvas.restore()
    }

}