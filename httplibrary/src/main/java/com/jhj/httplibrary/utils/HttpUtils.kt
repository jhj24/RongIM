package com.jhj.httplibrary.utils

import android.net.Uri
import com.jhj.httplibrary.model.HttpParams
import okhttp3.*
import java.net.URLEncoder

/**
 * Created by jhj on 2017-12-31 0031.
 */

object HttpUtils {

    /**
     * 传递过来的参数拼接成Url
     */
    fun appendUrl(url: String, params: HashMap<String, String>): String {
        if (params.isEmpty()) {
            return url
        }
        val builder = Uri.parse(url).buildUpon()
        for ((key, value) in params) {
            val urlValue = URLEncoder.encode(value, "UTF-8")
            builder.appendQueryParameter(key, urlValue)
        }
        return builder.build().toString()
    }


    /**
     * 生成类似表单的请求体
     */
    fun generateRequestBody(params: HttpParams): RequestBody {
        if (params.fileParams.isEmpty()) {
            //表单提交，没有文件
            val bodyBuilder = FormBody.Builder()
            for ((key, value) in params.stringParams) {
                bodyBuilder.add(key, value)
            }
            return bodyBuilder.build()
        } else {
            //表单提交，有文件
            val multipartBodybuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            //拼接键值对
            if (!params.stringParams.isEmpty()) {
                for ((key, value) in params.stringParams) {
                    multipartBodybuilder.addFormDataPart(key, value)
                }
            }
            //拼接文件
            for ((key, value) in params.fileParams) {
                if (value.isEmpty()) continue
                val filesBuilder = MultipartBody.Builder()
                value.forEach { file ->
                    val disposition = String.format("form-data; filename=\"%s\"", file.name)
                    val requestBody = RequestBody.create(MediaType.parse(FileUtils.getFileType(file.name)), file)
                    filesBuilder.addPart(Headers.of("Content-Disposition", disposition), requestBody)
                }
                multipartBodybuilder.addFormDataPart(key, null, filesBuilder.build())
            }
            return multipartBodybuilder.build()
        }
    }


}
