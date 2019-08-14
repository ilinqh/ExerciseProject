package lqh.hchomework.h13

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * 功能：自动换行的标签布局
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-08-14
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class TagLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : ViewGroup(context, attrs, def) {

    // 子控件位置信息
    val childrenBounds by lazy {
        ArrayList<Rect>()
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
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

            if (widthMode != MeasureSpec.UNSPECIFIED &&
                lineWidthUsed + child.measuredWidth > widthSize
            ) {
                lineWidthUsed = 0
                heightUsed += lineHeight
                measureChildWithMargins(child, widthMeasureSpec, lineWidthUsed, heightMeasureSpec, heightUsed)
            }

            var childBounds: Rect
            if (childrenBounds.size > i) {
                childBounds = childrenBounds[i]
            } else {
                childBounds = Rect()
                childrenBounds.add(childBounds)
            }
            childBounds.set(
                lineWidthUsed,
                heightUsed,
                lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )

            lineWidthUsed += child.measuredWidth
            widthUsed = Math.max(widthUsed, lineWidthUsed)
            lineHeight = Math.max(lineHeight, child.measuredHeight)
        }
        heightUsed += lineHeight
        setMeasuredDimension(widthUsed, heightUsed)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childBounds = childrenBounds[i]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        // 用于 measureChildWithMargins 方法中获取 MarginLayoutParams
        return MarginLayoutParams(context, attrs)
    }
}


















