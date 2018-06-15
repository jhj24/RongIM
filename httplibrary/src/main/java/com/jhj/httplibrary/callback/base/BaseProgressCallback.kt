package com.jhj.httplibrary.callback.base

/**
 * 带进度条的Callback基类.
 * 用于自定义带进度条的Callback
 * Created by jhj on 2018-1-11 0011.
 */
abstract class BaseProgressCallback<T> : HttpCallback<T>() {

    abstract fun onProgress(current: Long, total: Long)

}