package com.jhj.rongim

import android.util.Log
import com.jhj.httplibrary.httpcall.HttpCall
import com.jhj.rongim.net.THttpCallback
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient


/**
 * Created by jhj on 18-6-5.
 */
class TokenManager {


    fun getToken() {
        HttpCall.post(Config.GET_TOKEN)
                .addParam("userid", Config.GUID)
                .addParam("name", Config.NAME)
                .addParam("portraitUri", Config.PHOTO)
                .enqueue(object : THttpCallback<String>() {

                    override fun onSuccess(data: String?) {
                        connect(data)
                    }

                    override fun onFailure(msg: String?, errorCode: Int) {
                        print(errorCode)
                    }
                })
    }


    /**
     *
     * 连接服务器，在整个应用程序全局，只需要调用一次，需在 [.init] 之后调用。
     *
     * 如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @param callback 连接回调。
     * @return RongIM  客户端核心类的实例。
     */
    fun connect(token: String?) {


        RongIM.connect(token, object : RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            override fun onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            override fun onSuccess(userid: String) {
                Log.d("LoginActivity", "--onSuccess$userid")

            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            override fun onError(errorCode: RongIMClient.ErrorCode) {
                Log.d("LoginActivity", "--onError$errorCode")
            }
        })
    }

}