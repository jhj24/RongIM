package com.jhj.rongim

import android.app.Activity
import android.os.Bundle
import com.jhj.httplibrary.httpcall.HttpCall
import com.jhj.rongim.bean.UserInfoBean
import com.jhj.rongim.net.DataDialogCallback
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast

/**
 * Created by jhj on 18-6-14.
 */
class UserInfoActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)


        val id = intent.getStringExtra("id")


        HttpCall.post(Config.USER_INFO)
                .addParam("userGuid", id)
                .addParam("myGuid", Config.GUID)
                .enqueue(object : DataDialogCallback<UserInfoBean>(this@UserInfoActivity, "正在加载...") {
                    override fun onSuccess(data: UserInfoBean?) {
                        super.onSuccess(data)
                        data?.let {
                            tv_name.text = it.username
                            tv_phone.text = it.uname
                            tv_address.text = it.location
                        }

                    }

                    override fun onFailure(msg: String?, errorCode: Int) {
                        super.onFailure(msg, errorCode)
                        toast(msg ?: "")
                    }

                })
    }

}