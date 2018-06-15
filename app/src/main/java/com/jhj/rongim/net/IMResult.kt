package com.jhj.rongim.net

import com.jhj.httplibrary.result.Result

/**
 * Created by jhj on 18-6-9.
 */

class IMResult<T> : Result<IMResult<T>>() {

    override val clazz: IMResult<T>
        get() = this


    var code: Int = 0
    var userId: String? = null
    val token: T? = null


}
