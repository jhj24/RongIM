package com.jhj.rongim.rong

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.jhj.rongim.R
import io.rong.imkit.RongContext
import io.rong.imkit.fragment.ConversationListFragment
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.activity_conversation.*

/**
 *
 * 会话列表
 * Created by jhj on 18-6-5.
 */
class ConversationListActivity : FragmentActivity() {


    private var mConversationsTypes: Array<Conversation.ConversationType>? = null
    private var mConversationListFragment: ConversationListFragment? = null
    private var isDebug: Boolean = false
    var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation_list)
        initConversationList()
        (conversation_list as ConversationListFragment).uri = uri

    }


    private fun initConversationList(): Fragment {
        if (mConversationListFragment == null) {
            val listFragment = ConversationListFragment()
            listFragment.setAdapter(ConversationListAdapterEx(RongContext.getInstance()))

            if (isDebug) {
                uri = Uri.parse("rong://" + applicationInfo.packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                        .build()
                mConversationsTypes = arrayOf(Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP, Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE, Conversation.ConversationType.SYSTEM, Conversation.ConversationType.DISCUSSION)

            } else {
                uri = Uri.parse("rong://" + applicationInfo.packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .build()
                mConversationsTypes = arrayOf(Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP, Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE, Conversation.ConversationType.SYSTEM)
            }
            listFragment.uri = uri
            mConversationListFragment = listFragment
            return listFragment
        } else {
            return mConversationListFragment as ConversationListFragment
        }
    }


}