package com.jhj.httplibrary.request

import android.content.Context
import com.jhj.httplibrary.httpcall.Attribute
import com.jhj.httplibrary.model.HttpMethod
import com.jhj.httplibrary.utils.HttpUtils
import okhttp3.RequestBody

/**
 * Created by jhj on 2018-2-23 0023;
 */
class GetRequest(context: Context?, url: String, attribute: Attribute?) : BaseRequest<GetRequest>(context, url, attribute) {

    override fun getRequestUrl(): String {
        return HttpUtils.appendUrl(url, params.stringParams)
    }

    override fun getMethod(): HttpMethod {
        return HttpMethod.GET
    }

    override fun generateRequest(): RequestBody? {
        return null
    }
}