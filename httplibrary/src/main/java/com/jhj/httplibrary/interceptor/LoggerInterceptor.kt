package com.jhj.httplibrary.interceptor

import android.util.Log

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.lang.Exception

/**
 * 拦截器
 * Created by jhj on 2017-12-30 0030.
 */
class LoggerInterceptor : Interceptor {

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
        private val singleLine = "----------------------------------------"
        private val doubleLine = "=============================================="
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        //请求日志拦截
        logForRequest(request, chain.connection())

        //执行请求，计算请求时间
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            log("<-- HTTP FAILED: " + e)
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        //响应日志拦截
        return logForResponse(response, tookMs)
    }

    @Throws(IOException::class)
    private fun logForRequest(request: Request, connection: Connection?) {
        val requestBody = request.body()
        val protocol = connection?.protocol() ?: Protocol.HTTP_1_1
        try {
            log(doubleLine + "request=" + doubleLine)
            log("\tmethod: " + request.method())
            log("\turl: " + request.url())
            log("\tprotocol: " + protocol)

            log(singleLine + "headers" + singleLine)
            requestBody?.let { body ->
                if (body.contentType() != null) {
                    log("\t" + "Content-Type: " + body.contentType())
                }
                if (body.contentLength().toInt() != -1) {
                    log("\t" + "Content-Length: " + body.contentLength())
                }
            }
            val headers = request.headers()
            var i = 0
            while (i < headers.size()) {
                val name = headers.name(i)
                if ("Content-Type" != name && "Content-Length" != name) {
                    log("\t" + name + ": " + headers.value(i))
                }
                i++
            }

            log(singleLine + "params" + singleLine)
            requestBody?.let { body ->
                if (isPlaintext(body.contentType())) {
                    bodyToString(request)
                } else {
                    log("\trequestBody's content : maybe [file part] , too large too print , ignored!")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            log(doubleLine + "========" + doubleLine)
        }
    }

    private fun logForResponse(response: Response, tookMs: Long): Response {
        val clone = response.newBuilder().build()

        try {
            log(doubleLine + "response" + doubleLine)
            log("\turl: " + clone.request().url())
            log("\tcode: " + clone.code())
            log("\tprotocol: " + clone.protocol())
            log("\tresponseTime: " + tookMs + "ms")

            log(singleLine + "headers" + singleLine)
            val headers = clone.headers()
            var i = 0
            while (i < headers.size()) {
                log("\t" + headers.name(i) + ": " + headers.value(i))
                i++
            }

            log(singleLine + "body---" + singleLine)
            if (HttpHeaders.hasBody(clone)) {
                clone.body()?.let {
                    if (isPlaintext(it.contentType())) {
                        val bytes = toByteArray(it.byteStream())
                        val charset = it.contentType()?.charset(UTF8) ?: UTF8
                        val body = String(bytes, charset)
                        val strings = body.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        for (s in strings) {
                            log("\t" + s)
                        }
                        val responseBody = ResponseBody.create(it.contentType(), bytes)
                        return response.newBuilder().body(responseBody).build()
                    } else {
                        log("\tresponseBody's content : maybe [file part] , too large too print , ignored!")
                    }
                }
                if (clone.body() == null) return response
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            log(doubleLine + "========" + doubleLine)
        }
        return response
    }

    private fun bodyToString(request: Request) {
        try {
            val copy = request.newBuilder().build()
            val body = copy.body() ?: return
            val buffer = Buffer()
            body.writeTo(buffer)
            val charset = body.contentType()?.charset(UTF8) ?: UTF8
            val str = buffer.readString(charset).split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (aStr in str) {
                log("\t" + aStr)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 判断Body的MediaType类型，如果是"text"，"json"等类型，返回true。这些类型的Body都可当String类显示
     *
     * @param mediaType MediaType
     * @return boolean
     */
    private fun isPlaintext(mediaType: MediaType?): Boolean {
        mediaType?.let {
            if (it.type() != null && "text" == it.type()) {
                return true
            }
            val subtype = it.subtype().toLowerCase()
            if (subtype.contains("x-www-form-urlencoded") ||
                    subtype.contains("json") ||
                    subtype.contains("xml") ||
                    subtype.contains("html")) {
                return true
            }
        }
        return false
    }

    @Throws(IOException::class)
    private fun toByteArray(input: InputStream): ByteArray {
        val output = ByteArrayOutputStream()
        var len = 0
        val buffer = ByteArray(4096)
        while (input.read(buffer).apply { len = this } != -1) {
            output.write(buffer, 0, len)
        }
        output.close()
        return output.toByteArray()
    }

    private fun log(message: String) {
        Log.d("http", "||" + message)
    }
}
