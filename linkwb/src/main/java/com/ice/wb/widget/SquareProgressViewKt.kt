package com.ice.wb.widget

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.ice.wb.widget.utils.PercentStyle
import java.text.DecimalFormat

/**
 * Desc: 外围矩形进度View
 * Created by lvzhengbin
 * Time: 2020/4/10
 */
class SquareProgressViewKt : View {

    private var progress = 0.0
    private var progressBarPaint = Paint()
    private var outlinePaint = Paint()
    private var textPaint = Paint()

    private var widthInDp = 5f
    private var strokewidth = 0f
    private var mCanvas: Canvas? = null

    private var outline = false
    private var startline = false
    private var showProgress = false
    private var centerline = false

    private var roundedCorners = false
    private var roundedCornersRadius = 10f

    private var percentSettings = PercentStyle(Align.CENTER, 150f, true)
    private var clearOnHundred = false

    constructor(context: Context) : super(context){
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs){
    }

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr){
    }

    init {
        initializePaints(this.context)
    }

    private fun initializePaints(context: Context) {
        progressBarPaint.apply {
            color = context.resources.getColor(R.color.holo_green_dark)
            strokeWidth = convertDpToPx(widthInDp, getContext()).toFloat()
            isAntiAlias = true
            style = Paint.Style.STROKE
        }

        outlinePaint.apply {
            color = context.resources.getColor(R.color.black)
            strokeWidth = 1f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }

        textPaint.apply {
            color = context.resources.getColor(R.color.black)
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.mCanvas = canvas
        strokewidth = convertDpToPx(widthInDp, context).toFloat()
        val cW = width
        val cH = height
        val scope = 2 * cW + 2 * cH - 4 * strokewidth
        val hSw = strokewidth / 2
        if (isOutline()) {
            drawOutline()
        }
        if (isStartline()) {
            drawStartline()
        }
        if (isShowProgress()) {
            drawPercent(percentSettings)
        }
        if (isCenterline()) {
            drawCenterline(strokewidth)
        }
        if (isClearOnHundred() && progress == 100.0 || progress <= 0.0) {
            return
        }
        val path = Path()
        val drawEnd: DrawStop = getDrawEnd(scope / 100 * java.lang.Float.valueOf(progress.toString()), canvas)
        if (drawEnd.place == Place.TOP){
            path.moveTo(strokewidth, hSw)
            path.lineTo(drawEnd.location, hSw)
            canvas.drawPath(path, progressBarPaint)
        }
        if (drawEnd.place == Place.RIGHT) {
            path.moveTo(strokewidth, hSw)
            path.lineTo(cW - hSw, hSw)
            path.lineTo(cW - hSw, 0
                    + drawEnd.location)
            canvas.drawPath(path, progressBarPaint)
        }
        if (drawEnd.place == Place.BOTTOM) {
            path.moveTo(strokewidth, hSw)
            path.lineTo(cW - hSw, hSw)
            path.lineTo(cW - hSw, cH - hSw)
            path.lineTo(cW - strokewidth, cH - hSw)
            path.lineTo(drawEnd.location, cH - hSw)
            canvas.drawPath(path, progressBarPaint)
        }
        if (drawEnd.place == Place.LEFT) {
            path.moveTo(strokewidth, hSw)
            path.lineTo(cW - hSw, hSw)
            path.lineTo(cW - hSw, cH - hSw)
            path.lineTo(hSw, cH - hSw)
            path.lineTo(hSw, cH - strokewidth)
            path.lineTo(hSw, drawEnd.location)
            canvas.drawPath(path, progressBarPaint)
        }
    }

    fun drawStartline(){
        val outlinePath = Path()
        outlinePath.moveTo(mCanvas!!.width / 2.toFloat(), 0f)
        outlinePath.lineTo(mCanvas!!.width / 2.toFloat(), strokewidth)
        mCanvas!!.drawPath(outlinePath, outlinePaint)
    }

    fun drawOutline(){
        val outlinePath = Path()
        outlinePath.moveTo(0f, 0f)
        outlinePath.lineTo(mCanvas!!.width.toFloat(), 0f)
        outlinePath.lineTo(mCanvas!!.width.toFloat(), mCanvas!!.height.toFloat())
        outlinePath.lineTo(0f, mCanvas!!.height.toFloat())
        outlinePath.lineTo(0f, 0f)
        mCanvas!!.drawPath(outlinePath, outlinePaint)
    }

    fun getProgress(): Double {
        return progress
    }

    fun setProgress(progress: Double) {
        this.progress = progress
        this.invalidate()
    }

    fun setColor(color: Int){
        progressBarPaint.color = color
        this.invalidate()
    }

    fun setWidthInDp(width: Int){
        this.widthInDp = width.toFloat()
        progressBarPaint.strokeWidth = convertDpToPx(widthInDp, context).toFloat()
        this.invalidate()
    }

    fun isOutline(): Boolean {
        return outline
    }

    fun setOutline(outline: Boolean){
        this.outline = outline
        this.invalidate()
    }

    fun isStartline(): Boolean{
        return startline
    }

    fun setStartline(startline: Boolean){
        this.startline = startline
        this.invalidate()
    }

    private fun drawPercent(setting: PercentStyle) {
        textPaint.textAlign = setting.align
        if (setting.textSize == 0f) {
            textPaint.textSize = mCanvas!!.height / 10 * 4.toFloat()
        } else {
            textPaint.textSize = setting.textSize
        }
        var percentString = DecimalFormat("###").format(getProgress())
        if (setting.isPercentSign) {
            percentString += percentSettings.customText
        }
        textPaint.color = percentSettings.textColor
        mCanvas!!.drawText(
                percentString,
                mCanvas!!.width / 2.toFloat(),
                ((mCanvas!!.height / 2 - (textPaint.descent() + textPaint.ascent()) / 2) as Int).toFloat(),
                textPaint)
    }
    
    fun isShowProgress() : Boolean{
        return showProgress
    }
    
    fun setShowProgress(showProgress : Boolean){
        this.showProgress = showProgress
        this.invalidate()
    }

    fun setPercentStyle(percentSettings: PercentStyle) {
        this.percentSettings = percentSettings
        this.invalidate()
    }

    fun getPercentStyle(): PercentStyle {
        return percentSettings
    }

    fun setClearOnHundred(clearOnHundred: Boolean) {
        this.clearOnHundred = clearOnHundred
        this.invalidate()
    }

    fun isClearOnHundred(): Boolean {
        return clearOnHundred
    }

    private fun drawCenterline(strokewidth: Float) {
        val centerOfStrokeWidth = strokewidth / 2
        val centerlinePath = Path()
        centerlinePath.moveTo(centerOfStrokeWidth, centerOfStrokeWidth)
        centerlinePath.lineTo(mCanvas!!.width - centerOfStrokeWidth, centerOfStrokeWidth)
        centerlinePath.lineTo(mCanvas!!.width - centerOfStrokeWidth, mCanvas!!.height - centerOfStrokeWidth)
        centerlinePath.lineTo(centerOfStrokeWidth, mCanvas!!.height - centerOfStrokeWidth)
        centerlinePath.lineTo(centerOfStrokeWidth, centerOfStrokeWidth)
        mCanvas!!.drawPath(centerlinePath, outlinePaint)
    }

    fun isCenterline(): Boolean {
        return centerline
    }

    fun setCenterline(centerline: Boolean) {
        this.centerline = centerline
        this.invalidate()
    }
    
    private fun getDrawEnd(percent: Float, canvas: Canvas): DrawStop{
        val drawStop = DrawStop()
        strokewidth = convertDpToPx(widthInDp, context).toFloat()
        if (percent > 0 && percent <= canvas.width) { // top
            drawStop.place = Place.TOP
            drawStop.location = percent
        } else {
            if (percent > canvas.width) {
                val second = percent - canvas.width
                if (second > canvas.height - strokewidth) {
                    val third = second - (canvas.height - strokewidth)
                    if (third > canvas.width - strokewidth) {
                        val forth = third - (canvas.width - strokewidth)
                        drawStop.place = Place.LEFT
                        drawStop.location = canvas.height - strokewidth - forth - strokewidth
                    } else { //bottom
                        drawStop.place = Place.BOTTOM
                        drawStop.location = canvas.width - strokewidth - third
                    }
                } else { // right
                    drawStop.place = Place.RIGHT
                    drawStop.location = strokewidth + second
                }
            }
        }
        return drawStop
    }

    fun setRoundedCorners(roundedCorners: Boolean, radius: Float){
        this.roundedCorners = roundedCorners
        roundedCornersRadius = radius
        if (roundedCorners){
            progressBarPaint.pathEffect = CornerPathEffect(roundedCornersRadius)
        }else{
            progressBarPaint.pathEffect = null
        }
        invalidate()
    }

    fun isRoundedCorners(): Boolean{
        return roundedCorners
    }

    private fun convertDpToPx(dp: Float, context: Context): Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    private class DrawStop() {
        var place: Place? = null
        var location = 0f
    }

    enum class Place {
        TOP, RIGHT, BOTTOM, LEFT
    }

}