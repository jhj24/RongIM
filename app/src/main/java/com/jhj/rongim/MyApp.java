package com.jhj.rongim;

import android.app.Application;
import android.net.Uri;

import com.jhj.httplibrary.httpcall.HttpCall;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * Created by jhj on 18-6-4.
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        RongIMClient.init(this);
        //根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                return new UserInfo(Config.GUID, Config.NAME, Uri.parse(Config.PHOTO));
            }
        }, true);
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                return null;
            }
        }, true);
        //设置消息体内是否携带用户信息。
       /* RongIM.getInstance().setCurrentUserInfo(new UserInfo(Config.GUID, Config.NAME, Uri.parse(Config.PHOTO)));
        RongIM.getInstance().setMessageAttachedUserInfo(true);*/
        HttpCall.INSTANCE.init(this).build();
    }

}
