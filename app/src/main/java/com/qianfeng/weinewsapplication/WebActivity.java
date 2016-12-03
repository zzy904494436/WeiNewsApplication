package com.qianfeng.weinewsapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.myapplication.MyApplication;
import com.qianfeng.util.LoginDialog;
import com.qianfeng.view.NoScorllWebView;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WebActivity extends AppCompatActivity {

    @ViewInject(R.id.webViewFirst)WebView webView;
    @ViewInject(R.id.webPic)ImageView webPic;
    @ViewInject(R.id.shareToSina)RelativeLayout shareToSina;
    @ViewInject(R.id.webViewErro)TextView webViewErro;
    //,@ViewInject(R.id.scrollViewWeb)ScrollView scrollViewWeb;

    private String url = "";
    private String urlPic = "";
    private String title = "";
    private IWeiboShareAPI mWeiboShareAPI;
    private Bitmap bitmap;
    private MyApplication myApplication;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        x.view().inject(this);
        webViewErro.setVisibility(View.GONE);
        //
        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        //
        Intent intent = getIntent();

        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        webView.getSettings().setJavaScriptEnabled(true);
        //webview如果有错误的话_(:з」∠)_：
        //webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //alertDialog.dismiss();
                return super.shouldOverrideUrlLoading(view, url);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                //alertDialog.dismiss();
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webViewErro.setVisibility(View.VISIBLE);
                webViewErro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        webViewErro.setVisibility(View.GONE);
                        webView.loadUrl(url);
                    }
                });
            }
        });
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(url);
        //动画效果
        TranslateAnimation translate =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,-1f
                );
        translate.setDuration(2500);
        translate.setFillAfter(true);
        webPic.setAnimation(translate);
        //
        //
        /*ObjectAnimator animator = ObjectAnimator.ofFloat(webView, "scaleY", 1f,1.1f);
        animator.setDuration(1000);
        animator.start();*/

        /*TranslateAnimation translate1 =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,-0.55f
                );
        translate1.setDuration(2500);
        webView.setAnimation(translate1);

        translate1.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                webPic.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
        //

        urlPic = intent.getStringExtra("pic");
        Picasso.with(this).load(urlPic).into(webPic);
        //新浪分享
        myApplication = (MyApplication) getApplication();
        isLogined();


    }
    public void isLogined(){
        shareToSina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myApplication.isLogined){
                    mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(WebActivity.this, LoginActivity.Constants.APP_KEY);
                    mWeiboShareAPI.registerApp();	// 将应用注册到微博客户端
                    WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息

                    TextObject t = new TextObject();
                    t.text = "仿微头条 新浪分享:  <"+title+"> Link:"+url;


                    weiboMessage. textObject = t;
                    SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                    request.transaction = String.valueOf(System.currentTimeMillis());
                    request.multiMessage = weiboMessage;
                    mWeiboShareAPI.sendRequest(WebActivity.this,request); //发送请求消息到微博，唤起微博分享界面
                    Log.i("Tag", "send ...");
                }else {
                    LoginDialog loginDialog = new LoginDialog();
                    loginDialog.loginDialog(WebActivity.this);
                    Toast.makeText(WebActivity.this, "清先登陆再分享", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getPicBitMap(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URLConnection con;
                try {
                    URL url = new URL(urlPic);
                    con = url.openConnection();
                    con.connect();
                    con.setConnectTimeout(5000);
                    if (con.getConnectTimeout()<2000){
                        InputStream is = con.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mWeiboShareAPI.handleWeiboResponse(intent, response); //当前应用唤起微博分享后，返回当前应用
    }
    private IWeiboHandler.Response response = new IWeiboHandler.Response() {
        @Override
        public void onResponse(BaseResponse baseResponse) {

            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    break;
            }
        }
    };
}
