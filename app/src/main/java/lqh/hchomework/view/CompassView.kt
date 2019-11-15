package lqh.hchomework.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import lqh.hchomework.R
import kotlin.math.min
import kotlin.math.sqrt

/**
 * 功能：指南针
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/11/5
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class CompassView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private lateinit var mCanvas: Canvas
    /**
     * 指南针圆心点坐标
     */
    private var mCenterX: Int = 0
    private var mCenterY: Int = 0

    /**
     * View矩形的宽度
     */
    private var mWidth: Int = 0
    /**
     * 外圆半径
     */
    private var mOutSideRadius: Int = 0
    /**
     * 外接圆半径
     */
    private var mCircumRadius: Int = 0
    /**
     * 指南针文字大小控件高度
     */
    private var mTextHeight: Int = 0
    /**
     * 暗红色 外圈笔
     */
    private val mDarkRedPaint = Paint()
    /**
     * 深灰 外圈笔
     */
    private val mDeepGrayPaint = Paint()
    /**
     * 外三角笔
     */
    private val mOutSideCircumPaint = Paint()
    /**
     * 浅灰 外圈笔
     */
    private val mLightGrayPaint = Paint()
    /**
     * 指南针上面 文字笔
     */
    private val mTextPaint = Paint()
    /**
     * 外接圆，三角形笔
     */
    private val mCircumPaint = Paint()
    /**
     * 指南针上面文字的外接矩形,用来测文字大小让文字居中
     */
    private val mTextRect = Rect()
    /**
     * 外圈小三角形的Path
     */
    private val mOutsideTriangle = Path()
    /**
     * 外接圆小三角形的Path
     */
    private val mCircumTriangle = Path()

    /**
     * NESW 文字笔 和文字外接矩形
     */
    private val mNorthPaint = Paint()
    private val mOthersPaint = Paint()
    private val mPositionRect = Rect()
    /**
     * 小刻度文字大小矩形和画笔
     */
    private val mSmallDegreePaint = Paint()
    /**
     * 两位数的
     */
    private val mSecondRect = Rect()
    /**
     * 三位数的
     */
    private val mThirdRect = Rect()
    /**
     * 圆心数字矩形
     */
    private val mCenterTextRect = Rect()

    /**
     * 中心文字笔
     */
    private val mCenterPaint = Paint()

    /**
     * 内心圆是一个颜色辐射渐变的圆
     */
    private var mInnerShader: Shader? = null
    private val mInnerPaint = Paint()

    /**
     * camera绕X轴旋转的角度
     */
    private var mCameraRotateX: Float = 0f
    /**
     * camera绕Y轴旋转的角度
     */
    private var mCameraRotateY: Float = 0f
    /**
     * camera最大旋转角度
     */
    private var mMaxCameraTranslate: Float = 0f
    /**
     * camera矩阵
     */
    private val mCameraMatrix = Matrix()
    /**
     * 设置camera
     */
    private val mCamera = Camera()

    var value = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var valCompare: Float = 0f
    /**
     * 偏转角度红线笔
     */
    private val mAnglePaint = Paint()

    /**
     * 方位文字
     */
    private var text = "Utara"

    init {
        mDarkRedPaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.darkRed)
        }

        mDeepGrayPaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.deepGray)
        }

        mLightGrayPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.lightGray)
        }

        mTextPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 80f
            color = ContextCompat.getColor(context, R.color.white)
        }

        mCircumPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.red)
        }

        mOutSideCircumPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.lightGray)
        }

        mNorthPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 40f
            color = ContextCompat.getColor(context, R.color.red)
        }

        mOthersPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 40f
            color = ContextCompat.getColor(context, R.color.white)
        }

        mCenterPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 120f
            color = ContextCompat.getColor(context, R.color.white)
        }

        mSmallDegreePaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            textSize = 30f
            color = ContextCompat.getColor(context, R.color.lightGray)
        }

        mInnerPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        mAnglePaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.red)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        mWidth = min(widthSize, heightSize)
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            mWidth = heightSize
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            mWidth = widthSize
        }
        //为指南针上面的文字预留空间，定为1/3边张
        mTextHeight = mWidth / 3
        //设置圆心点坐标
        mCenterX = mWidth / 2
        mCenterY = mWidth / 2 + mTextHeight
        //外部圆的外径
        mOutSideRadius = mWidth * 3 / 8
        //外接圆的半径
        mCircumRadius = mOutSideRadius * 4 / 5
        //camera最大平移距离
        mMaxCameraTranslate = 0.02f * mOutSideRadius
        setMeasuredDimension(mWidth, mWidth + mWidth / 3)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvas = canvas
        // 设置Camera矩阵 实现3D效果
        set3DMatrix()
        // 画文字
        drawText()
        // 画指南针外圈
        drawCompassOutSide()
        // 画指南针外接圆
        drawCompassCircum()
        // 画内部渐变颜色圆
        drawInnerCircle()
        // 画指南针内部刻度
        drawCompassDegreeScale()
        // 画圆心数字
        drawCenterText()
    }

    /**
     * 设置camera相关
     */
    private fun set3DMatrix() {
        mCameraMatrix.reset()
        mCamera.save()
        mCamera.rotateX(mCameraRotateX)
        mCamera.rotateY(mCameraRotateY)
        mCamera.getMatrix(mCameraMatrix)
        mCamera.restore()
        // camera默认旋转是View左上角为旋转中心
        // 所以动作之前要，设置矩阵位置 -mTextHeight-mOutSideRadius
        mCameraMatrix.preTranslate(-width / 2f, -height / 2f)
        // 动作之后恢复位置
        mCameraMatrix.postTranslate(width / 2f, height / 2f)
        mCanvas.concat(mCameraMatrix)
    }

    private fun drawText() {
        if (value <= 15 || value >= 345) {
            text = "Utara"
        } else if (value > 15 && value <= 75) {
            text = "Timur laut"
        } else if (value > 75 && value <= 105) {
            text = "Timur"
        } else if (value > 105 && value <= 165) {
            text = "Tenggara"
        } else if (value > 165 && value <= 195) {
            text = "Selatan"
        } else if (value > 195 && value <= 255) {
            text = "Barat daya"
        } else if (value > 255 && value <= 285) {
            text = "Barat"
        } else if (value > 285 && value < 345) {
            text = "Barat laut"
        }

        mTextPaint.getTextBounds(text, 0, text.length, mTextRect)
        // 文字宽度
        val mTextWidth = mTextRect.width()
        // 让文字水平居中显示
        mCanvas.drawText(
            text,
            width / 2f - mTextWidth / 2f,
            mTextHeight / 2f,
            mTextPaint
        )
    }

    /**
     * 指南针外部可简单分为两部分
     * 1、用Path实现小三角形
     * 2、两个圆弧
     */
    private fun drawCompassOutSide() {
        mCanvas.save()
        // 小三角形的高度
        val mTriangleHeight = 40
        // 定义Path画小三角形
        mOutsideTriangle.moveTo(width / 2f, (mTextHeight - mTriangleHeight).toFloat())
        // 小三角形的边长
        val mTriangleSide = 46.18f
        // 画出小三角形
        mOutsideTriangle.lineTo(width / 2f - mTriangleSide / 2f, mTextHeight.toFloat())
        mOutsideTriangle.lineTo(width / 2f + mTriangleSide / 2f, mTextHeight.toFloat())
        mOutsideTriangle.close()
        mCanvas.drawPath(mOutsideTriangle, mOutSideCircumPaint)

        // 画圆弧
        mDarkRedPaint.strokeWidth = 5f
        mLightGrayPaint.strokeWidth = 5f
        mDeepGrayPaint.strokeWidth = 3f
        mLightGrayPaint.style = Paint.Style.STROKE
        mCanvas.drawArc(
            width / 2f - mOutSideRadius,
            mTextHeight.toFloat(),
            width / 2f + mOutSideRadius,
            mTextHeight + mOutSideRadius * 2f,
            -80f,
            120f,
            false,
            mLightGrayPaint
        )
        mCanvas.drawArc(
            width / 2f - mOutSideRadius,
            mTextHeight.toFloat(),
            width / 2f + mOutSideRadius,
            mTextHeight + mOutSideRadius * 2f,
            40f,
            20f,
            false,
            mDeepGrayPaint
        )
        mCanvas.drawArc(
            width / 2f - mOutSideRadius,
            mTextHeight.toFloat(),
            width / 2f + mOutSideRadius,
            mTextHeight + mOutSideRadius * 2f,
            -100f,
            -20f,
            false,
            mLightGrayPaint
        )
        mCanvas.drawArc(
            width / 2f - mOutSideRadius,
            mTextHeight.toFloat(),
            width / 2f + mOutSideRadius,
            mTextHeight + mOutSideRadius * 2f,
            -120f,
            -120f,
            false,
            mDarkRedPaint
        )
        mCanvas.restore()
    }

    /**
     * 指南针外接圆，和外部圆换道理差不多
     */
    private fun drawCompassCircum() {
        mCanvas.save()
        // 外接圆小三角形的高度
        val mTriangleHeight = (mOutSideRadius - mCircumRadius) / 2f

        mCanvas.rotate(-value, width / 2f, mOutSideRadius + mTextHeight.toFloat())
        mCircumTriangle.moveTo(width / 2f, mTriangleHeight + mTextHeight)
        //内接三角形的边长,简单数学运算
        val mTriangleSide = (mTriangleHeight / sqrt(3.0) * 2).toFloat()
        mCircumTriangle.lineTo(
            width / 2 - mTriangleSide / 2,
            mTextHeight + mTriangleHeight * 2
        )
        mCircumTriangle.lineTo(
            width / 2 + mTriangleSide / 2,
            mTextHeight + mTriangleHeight * 2
        )
        mCircumTriangle.close()
        mCanvas.drawPath(mCircumTriangle, mCircumPaint)
        mCanvas.drawArc(
            width / 2f - mCircumRadius,
            (mTextHeight + mOutSideRadius - mCircumRadius).toFloat(),
            width / 2f + mCircumRadius,
            (mTextHeight + mOutSideRadius + mCircumRadius).toFloat(),
            -85f,
            350f,
            false,
            mDeepGrayPaint
        )
        mAnglePaint.strokeWidth = 5f
        if (value <= 180) {
            valCompare = value
            mCanvas.drawArc(
                width / 2f - mCircumRadius,
                (mTextHeight + mOutSideRadius - mCircumRadius).toFloat(),
                width / 2f + mCircumRadius,
                (mTextHeight + mOutSideRadius + mCircumRadius).toFloat(),
                -85f,
                valCompare,
                false,
                mAnglePaint
            )
        } else {
            valCompare = 360 - value
            mCanvas.drawArc(
                width / 2f - mCircumRadius,
                (mTextHeight + mOutSideRadius - mCircumRadius).toFloat(),
                width / 2f + mCircumRadius,
                (mTextHeight + mOutSideRadius + mCircumRadius).toFloat(),
                -95f,
                -valCompare,
                false,
                mAnglePaint
            )
        }

        mCanvas.restore()
    }

    private fun drawInnerCircle() {
        mInnerShader = RadialGradient(
            width / 2f,
            (mOutSideRadius + mTextHeight).toFloat(),
            mCircumRadius - 40f,
            Color.parseColor("#323232"),
            Color.parseColor("#000000"),
            Shader.TileMode.CLAMP
        )
        mInnerPaint.shader = mInnerShader
        mCanvas.drawCircle(
            width / 2f,
            (mOutSideRadius + mTextHeight).toFloat(),
            mCircumRadius - 40f,
            mInnerPaint
        )
    }

    private fun drawCompassDegreeScale() {
        mCanvas.save()
        //获取 N 文字的宽度
        mNorthPaint.getTextBounds("N", 0, 1, mPositionRect)
        val mPositionTextWidth = mPositionRect.width()
        val mPositionTextHeight = mPositionRect.height()
        //获取W文字宽度,因为W比较宽 所以要单独获取
        mNorthPaint.getTextBounds("W", 0, 1, mPositionRect)
        val mWPositionTextWidth = mPositionRect.width()
        val mWPositionTextHeight = mPositionRect.height()
        //获取小刻度，两位数的宽度
        mSmallDegreePaint.getTextBounds("30", 0, 1, mSecondRect)
        val mSecondTextWidth = mSecondRect.width()
        val mSecondTextHeight = mSecondRect.height()
        //获取小刻度，3位数的宽度
        mSmallDegreePaint.getTextBounds("30", 0, 1, mThirdRect)
        val mThirdTextWidth = mThirdRect.width()
        val mThirdTextHeight = mThirdRect.height()

        mCanvas.rotate(value, (width / 2).toFloat(), (mOutSideRadius + mTextHeight).toFloat())


        //画刻度线
        for (i in 0..239) {

            if (i == 0 || i == 60 || i == 120 || i == 180) {
                mCanvas.drawLine(
                    (width / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 10).toFloat(),
                    (width / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 30).toFloat(),
                    mDeepGrayPaint
                )
            } else {
                mCanvas.drawLine(
                    (width / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 10).toFloat(),
                    (width / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 30).toFloat(),
                    mLightGrayPaint
                )
            }
            when (i) {
                0 -> mCanvas.drawText(
                    "N",
                    (this.width / 2 - mPositionTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mPositionTextHeight).toFloat(),
                    mNorthPaint
                )
                60 -> mCanvas.drawText(
                    "E",
                    (this.width / 2 - mPositionTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mPositionTextHeight).toFloat(),
                    mOthersPaint
                )
                120 -> mCanvas.drawText(
                    "S",
                    (this.width / 2 - mPositionTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mPositionTextHeight).toFloat(),
                    mOthersPaint
                )
                180 -> mCanvas.drawText(
                    "W",
                    (this.width / 2 - mWPositionTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mWPositionTextHeight).toFloat(),
                    mOthersPaint
                )
                20 -> mCanvas.drawText(
                    "30",
                    (this.width / 2 - mSecondTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mSecondTextHeight).toFloat(),
                    mSmallDegreePaint
                )
                40 -> mCanvas.drawText(
                    "60",
                    (this.width / 2 - mSecondTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mSecondTextHeight).toFloat(),
                    mSmallDegreePaint
                )
                80 -> mCanvas.drawText(
                    "120",
                    (this.width / 2 - mThirdTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mThirdTextHeight).toFloat(),
                    mSmallDegreePaint
                )
                100 -> mCanvas.drawText(
                    "150",
                    (this.width / 2 - mThirdTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mThirdTextHeight).toFloat(),
                    mSmallDegreePaint
                )
                140 -> mCanvas.drawText(
                    "210",
                    (this.width / 2 - mThirdTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mThirdTextHeight).toFloat(),
                    mSmallDegreePaint
                )
                160 -> mCanvas.drawText(
                    "240",
                    (this.width / 2 - mThirdTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mThirdTextHeight).toFloat(),
                    mSmallDegreePaint
                )
                200 -> mCanvas.drawText(
                    "300",
                    (this.width / 2 - mThirdTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mThirdTextHeight).toFloat(),
                    mSmallDegreePaint
                )
                220 -> mCanvas.drawText(
                    "330",
                    (this.width / 2 - mThirdTextWidth / 2).toFloat(),
                    (mTextHeight + mOutSideRadius - mCircumRadius + 40 + mThirdTextHeight).toFloat(),
                    mSmallDegreePaint
                )
            }
            mCanvas.rotate(1.5f, mCenterX.toFloat(), (mOutSideRadius + mTextHeight).toFloat())
        }
        mCanvas.restore()
    }

    private fun drawCenterText() {
        val centerText = value.toInt().toString() + "°"
        mCenterPaint.getTextBounds(centerText, 0, centerText.length, mCenterTextRect)
        val centerTextWidth = mCenterTextRect.width()
        val centerTextHeight = mCenterTextRect.height()
        mCanvas.drawText(
            centerText,
            (width / 2 - centerTextWidth / 2).toFloat(),
            (mTextHeight + mOutSideRadius + centerTextHeight / 5).toFloat(),
            mCenterPaint
        )
    }


}