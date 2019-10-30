package lqh.hchomework.h4_16

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import kotlin.math.min

/**
 * 功能：正方形头像
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/30
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class SquareImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredHeight, measuredHeight)
        setMeasuredDimension(size, size)
    }
}