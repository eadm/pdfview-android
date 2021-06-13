package com.pdfview

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import com.pdfview.subsamplincscaleimageview.ImageSource
import com.pdfview.subsamplincscaleimageview.SubsamplingScaleImageView
import java.io.File

class PDFView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : SubsamplingScaleImageView(context, attrs) {

    private var mSource: Source? = null
    private var mScale: Float = 8f

    init {
        setMinimumTileDpi(120)
        setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_START)
    }

    fun fromAsset(assetFileName: String): PDFView {
        mSource = Source.FromFile(FileUtils.fileFromAsset(context, assetFileName))
        return this
    }

    fun fromFile(file: File): PDFView {
        mSource = Source.FromFile(file)
        return this
    }

    fun fromFile(filePath: String): PDFView {
        mSource = Source.FromFile(File(filePath))
        return this
    }

    fun fromUri(uri: Uri): PDFView {
        mSource = Source.FromUri(uri)
        return this
    }

    fun scale(scale: Float): PDFView {
        mScale = scale
        return this
    }

    fun show() {
        val source = mSource!!
        val imageSource =
            when (source) {
                is Source.FromFile ->
                    ImageSource.uri(source.file.path)

                is Source.FromUri ->
                    ImageSource.uri(source.uri)
            }
        setRegionDecoderFactory { PDFRegionDecoder(view = this, source = source, scale = mScale) }
        setImage(imageSource)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.recycle()
    }
}