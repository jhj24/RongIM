package com.jhj.rongim.rong

import android.net.Uri
import com.jhj.httplibrary.callback.base.HttpCallback
import com.jhj.httplibrary.httpcall.HttpCall
import com.jhj.rongim.Config
import com.jhj.rongim.bean.UserInfoBean
import com.jhj.rongim.net.DataCallback
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo

/**
 * Created by jhj on 18-6-16.
 */
object RongUtil {

    fun refreshUserInfo(userId: String) {
        HttpCall.post(Config.USER_INFO)
                .addParam("userGuid", userId)
                .addParam("myGuid", Config.GUID)
                .enqueue(object : DataCallback<UserInfoBean>() {
                    override fun onSuccess(data: UserInfoBean?) {
                        data?.let {
                            RongIM.getInstance().refreshUserInfoCache(UserInfo(it.userGuid, it.upname, Uri.parse(it.photo)))
                        }
                    }

                    override fun onFailure(msg: String?, errorCode: Int) {
                        print(msg)
                    }
                })
    }

    fun a(){

    }

}