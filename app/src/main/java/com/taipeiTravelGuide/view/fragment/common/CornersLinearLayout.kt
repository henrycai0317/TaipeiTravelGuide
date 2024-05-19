package com.taipeiTravelGuide.view.fragment.common


import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.LinearLayout
import com.taipeiTravelGuide.R


/**
 * 切圓角元件
 *
 */
class CornersLinearLayout @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(mContext, attrs, defStyleAttr) {
    private var mCorners: Float
    private var mLeftTopCorner: Float
    private var mRightTopCorner: Float
    private var mLeftBottomCorner: Float
    private var mRightBottomCorner: Float
    private var mWidth = 0
    private var mHeight = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        val path = Path()
        val rectF = RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
        if (mCorners > 0f) {
            path.addRoundRect(rectF, mCorners, mCorners, Path.Direction.CCW)
        } else {
            val radii = floatArrayOf(
                mLeftTopCorner, mLeftTopCorner,
                mRightTopCorner, mRightTopCorner,
                mRightBottomCorner, mRightBottomCorner,
                mLeftBottomCorner, mLeftBottomCorner
            )
            path.addRoundRect(rectF, radii, Path.Direction.CCW)
        }
        canvas.clipPath(path)
        super.draw(canvas)
    }

    fun setCorners(corner:Float) {
        mCorners = corner
    }

    fun setLeftBottomCorner(leftBottomCorner:Float) {
        mLeftBottomCorner = leftBottomCorner
    }

    fun setLeftTopCorner(leftTopCorner:Float) {
        mLeftTopCorner = leftTopCorner
    }

    fun setRightBottomCorner(rightBottomCorner:Float) {
        mRightBottomCorner = rightBottomCorner
    }

    fun setRightTopCorner(rightTopCorner:Float) {
        mRightTopCorner = rightTopCorner
    }

    init {
        val typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CornersLinearLayout)
        mCorners = typedArray.getDimension(R.styleable.CornersLinearLayout_corner, 0f)
        mLeftTopCorner = typedArray.getDimension(R.styleable.CornersLinearLayout_leftTopCorner, 0f)
        mRightTopCorner = typedArray.getDimension(R.styleable.CornersLinearLayout_rightTopCorner, 0f)
        mRightBottomCorner = typedArray.getDimension(R.styleable.CornersLinearLayout_rightBottomCorner, 0f)
        mLeftBottomCorner = typedArray.getDimension(R.styleable.CornersLinearLayout_leftBottomCorner, 0f)
        typedArray.recycle()
    }
}