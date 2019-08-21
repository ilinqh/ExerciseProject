package lqh.hchomework.h12

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import lqh.hchomework.R
import lqh.hchomework.Utils

/**
 * 功能：MaterialEditText
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-08-13
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class MaterialEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    companion object {
        private val TEXT_SIZE = Utils.dp2px(12f)
        private val TEXT_MARGIN = Utils.dp2px(8f)

        private val VERTICAL_OFFSET = Utils.dp2px(38f)
        private val VERTICAL_OFFSET_EXTRA = Utils.dp2px(16f)
    }

    private var floatingLabelShown: Boolean = false

    private var backgroundPadding = Rect()

    var useFloatingLabel: Boolean = true
        set(value) {
            field = value
            resetPadding(value)
            invalidate()
        }

    private var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this@MaterialEditText, "floatingLabelFraction", 0f, 1f)
    }

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useFloatingLabel = typeArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true)
        typeArray.recycle()

        paint.textSize = TEXT_SIZE

        resetPadding(useFloatingLabel)

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (floatingLabelShown && TextUtils.isEmpty(s)) {
                    floatingLabelShown = !floatingLabelShown
                    animator.reverse()
                } else if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                    floatingLabelShown = !floatingLabelShown
                    animator.start()
                }
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (useFloatingLabel) {
            paint.alpha = (floatingLabelFraction * 0xff).toInt()
            canvas.drawText(
                hint.toString(), paddingStart.toFloat(),
                VERTICAL_OFFSET - VERTICAL_OFFSET_EXTRA * floatingLabelFraction, paint
            )
        }
    }

    private fun resetPadding(useFloatingLabel: Boolean) {
        background.getPadding(backgroundPadding)
        if (useFloatingLabel) {
            setPadding(
                backgroundPadding.left, (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN).toInt(),
                backgroundPadding.right, backgroundPadding.bottom
            )
        } else {
            setPadding(
                backgroundPadding.left, backgroundPadding.top,
                backgroundPadding.right, backgroundPadding.bottom
            )
        }
    }
}