package com.jhj.rongim.net

import com.jhj.httplibrary.result.Result

/**
 * Created by jhj on 18-6-15.
 */
class DataResult<T> : Result<DataResult<T>>() {

    override val clazz: DataResult<T>
        get() = this

    val status: Int = 0
    val msg: String? = null
    val items: T? = null


}