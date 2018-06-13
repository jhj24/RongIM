package com.jhj.httplibrary.utils

import android.content.Context
import android.net.Uri

/**
 * Created by jhj on 2017-12-31 0031.
 */
object FileUtils {

    fun getPath(context: Context, uri: Uri): String {
        return UrlUtils.getPath(context, uri)
    }

    fun getFileType(name: String): String {
        return MimeTypeUtils.getFileType(name)
    }

}