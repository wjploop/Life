package com.wjploop.life.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class FixRatioImage @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleId: Int = 0) :
    AppCompatImageView(context, attributeSet, defStyleId) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }
}