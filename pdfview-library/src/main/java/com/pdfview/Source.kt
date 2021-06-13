package com.pdfview

import android.net.Uri
import java.io.File

internal sealed class Source {
    data class FromFile(val file: File) : Source()
    data class FromUri(val uri: Uri) : Source()
}