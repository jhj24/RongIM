package com.jhj.rongim.rong

import android.content.Context
import android.view.View
import com.jhj.rongim.UserInfoActivity

import io.rong.imkit.RongIM
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.imlib.model.UserInfo
import org.jetbrains.anko.startActivity

/**
 * 消息/头像点击事件
 * 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
 * Created by jhj on 18-6-14.
 */

class ConversationListener : RongIM.ConversationClickListener {

    override fun onUserPortraitClick(context: Context, conversationType: Conversation.ConversationType, userInfo: UserInfo, s: String): Boolean {
        val id = userInfo.userId
        context.startActivity<UserInfoActivity>("id" to id)
        return true
    }

    override fun onUserPortraitLongClick(context: Context, conversationType: Conversation.ConversationType, userInfo: UserInfo, s: String): Boolean {
        return false
    }

    override fun onMessageClick(context: Context, view: View, message: Message): Boolean {
        return false
    }

    override fun onMessageLinkClick(context: Context, s: String, message: Message): Boolean {
        return false
    }

    override fun onMessageLongClick(context: Context, view: View, message: Message): Boolean {
        return false
    }
}
