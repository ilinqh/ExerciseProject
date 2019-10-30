package lqh.hchomework.h4_16

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ViewGroup
import kotlin.math.max

/**
 * 功能：标签布局
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/30
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class TagLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private val sparseArray: SparseArray<Rect> by lazy {
        SparseArray<Rect>()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 已用总宽度
        var widthUsed = 0
        // 已用总高度
        var heightUsed = 0

        // 当前行已用总宽度
        var lineWidthUsed = 0
        // 当前行最大高度
        var lineHeight = 0

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)

            val lp = child.layoutParams as MarginLayoutParams
            val childMarginStart = lp.marginStart
            val childMarginEnd = lp.marginEnd
            if (widthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + childMarginStart + childMarginEnd + child.measuredWidth > widthSize) {
                lineWidthUsed = 0
                heightUsed += lineHeight
                lineHeight = 0
            }

            val childBounds = if (sparseArray[i] == null) {
                Rect().apply {
                    sparseArray.put(i, this)
                }
            } else {
                sparseArray.get(i)
            }

            childBounds.set(
                lineWidthUsed + childMarginStart,
                heightUsed,
                lineWidthUsed + childMarginStart + child.measuredWidth + childMarginEnd,
                heightUsed + child.measuredHeight
            )

            lineWidthUsed += child.measuredWidth + childMarginStart + childMarginEnd
            widthUsed = max(widthUsed, lineWidthUsed)
            lineHeight = max(lineHeight, child.measuredHeight)
        }
        heightUsed += lineHeight
        setMeasuredDimension(widthUsed, heightUsed)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childBounds = sparseArray.get(i)
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}