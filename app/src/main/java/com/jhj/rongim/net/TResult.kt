package com.jhj.rongim.net

import com.jhj.httplibrary.result.Result

/**
 * Created by jhj on 18-6-9.
 */

class TResult<T> : Result<T>() {

    override val clazz: Result<T>
        get() = this


    var code: Int = 0
    var userId: String? = null
    val token: T? = null



}
