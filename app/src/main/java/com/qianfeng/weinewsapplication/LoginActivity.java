package com.qianfeng.weinewsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.myapplication.MyApplication;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity {

    @ViewInject(R.id.textTitle)TextView textTitle;


    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private UsersAPI usersAPI;

    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            if (!TextUtils.isEmpty(s)){
                User user = User.parse(s);
                String userName = user.name;
                String userIcon = user.avatar_hd;
                //回传_(:з」∠)_
                Intent intent = new Intent();
                intent.putExtra("userName",userName);
                intent.putExtra("userIcon",userIcon);
                MyApplication myApplication = (MyApplication) getApplication();

                setResult(2,intent);
                myApplication.userIcon = userIcon;
                myApplication.userName = userName;
                myApplication.isLogined=true;
                LoginActivity.this.finish();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        x.view().inject(this);
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        textTitle.setText("登录");
    }
    @Event(R.id.imageViewLogin)
    private void imageViewLogin_Click(View view){
        //
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        mSsoHandler. authorize(new AuthListener());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public interface Constants {
        public static final String APP_KEY      = "1320532542";		   // 应用的APP_KEY
        public static final String REDIRECT_URL = "http://www.baidu.com";// 应用的回调页
        public static final String SCOPE = 							   // 应用申请的高级权限
                "email,direct_messages_read,direct_messages_write,"
                        + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                        + "follow_app_official_microblog," + "invitation_write";
    }
    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values); // 从 Bundle 中解析 Token
            if (mAccessToken.isSessionValid()) {
               /* AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken);*/ //保存Token
                //Toast.makeText(LoginActivity.this, mAccessToken.getUid(), Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "微信登录功能", Toast.LENGTH_SHORT).show();
                usersAPI = new UsersAPI(LoginActivity.this, Constants.APP_KEY, mAccessToken);
                long uid = Long.parseLong(mAccessToken.getUid());
                usersAPI.show(uid,mListener);

            } else {
                // 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
                String code = values.getString("code", "");
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }

        @Override
        public void onCancel() {

        }
    }
}
