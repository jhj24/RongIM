package com.jhj.rongim

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TokenManager().getToken(this)
        one.setOnClickListener {
           // RongIM.getInstance().setCurrentUserInfo(UserInfo(Config.GUID, Config.NAME, Uri.parse(Config.PHOTO)))
           // RongIM.getInstance().refreshUserInfoCache(UserInfo(Config.GUID, Config.NAME, Uri.parse(Config.PHOTO)))
            RongIM.getInstance().startPrivateChat(this, Config.targetId, "标题")
        }
    }
}
