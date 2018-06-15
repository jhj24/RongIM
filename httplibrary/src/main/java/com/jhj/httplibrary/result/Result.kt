package com.jhj.httplibrary.result

import com.google.gson.Gson
import java.io.Serializable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 网络请求返回的基础数据
 * Created by jhj on 2018-1-11 0011.
 */
abstract class Result<T : Result<T>> : Serializable {

    abstract val clazz: T


    fun <T> parseJson(str: String?, type: Type): T {
        val superType = type(clazz::class.java, type)
        return Gson().fromJson(str, superType)
    }


    fun type(raw: Class<*>, vararg args: Type): ParameterizedType {
        return object : ParameterizedType {
            override fun getRawType(): Type {
                return raw
            }

            override fun getActualTypeArguments(): Array<out Type>? {
                return args
            }

            override fun getOwnerType(): Type? {
                return null
            }
        }
    }
}