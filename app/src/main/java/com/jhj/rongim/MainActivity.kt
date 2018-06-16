package com.jhj.rongim

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.rongim.rong.TokenManager
import io.rong.imkit.RongIM
import io.rong.imkit.userInfoCache.RongUserInfoManager
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TokenManager().getToken(this)


        one.setOnClickListener {
            RongIM.getInstance().startConversationList(this)
        }

        two.setOnClickListener {
            RongUserInfoManager.getInstance().setUserInfo(UserInfo(Config.TARGET_GUID, Config.TARGET_NAME, Uri.parse(Config.TARGET_PHONE)))
            RongIM.getInstance().startPrivateChat(this, Config.TARGET_GUID, Config.TARGET_NAME)
        }
    }
}
