package com.qianfeng.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.myapplication.MyApplication;
import com.qianfeng.util.CircleImageView;
import com.qianfeng.weinewsapplication.LoginActivity;
import com.qianfeng.weinewsapplication.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyselfFragment extends Fragment {
    private RelativeLayout myselfLogin;
    private CircleImageView userIcon;
    private TextView tvCilckToClient;
    private ImageView ivBackIcon;
    private MyApplication myApplication;

    public MyselfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myself,container,false);
        myApplication = (MyApplication) getActivity().getApplication();
        tvCilckToClient = (TextView) view.findViewById(R.id.tvCilckToClient);
        userIcon = (CircleImageView) view.findViewById(R.id.userIcon);
        myselfLogin = (RelativeLayout) view.findViewById(R.id.myselfLogin);
        ivBackIcon = (ImageView) view.findViewById(R.id.ivBackIcon);
        if (myApplication.isLogined&&!myApplication.userIcon.isEmpty()){
            Picasso.with(getContext()).load(myApplication.userIcon).into(userIcon);
            //tvCilckToClient.setText(myApplication.userName);
            myselfLogin.setEnabled(false);
            //ivBackIcon.setVisibility(View.INVISIBLE);
            //
            Picasso.with(getContext()).load(myApplication.userIcon).into(userIcon);
            tvCilckToClient.setText(myApplication.userName);
            ivBackIcon.setVisibility(View.INVISIBLE);
        }
        //点击进入新浪登陆功能
        myselfLogin_Click();
        //
        return view;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if (myApplication.isLogined&&!myApplication.userIcon.isEmpty()){
            Picasso.with(getContext()).load(myApplication.userIcon).into(userIcon);
            //tvCilckToClient.setText(myApplication.userName);
            myselfLogin.setEnabled(false);
            //ivBackIcon.setVisibility(View.INVISIBLE);
            //
            Picasso.with(getContext()).load(myApplication.userIcon).into(userIcon);
            tvCilckToClient.setText(myApplication.userName);
            ivBackIcon.setVisibility(View.INVISIBLE);
        }
    }*/

    public void myselfLogin_Click(){
        myselfLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2){
            Picasso.with(getContext()).load(data.getStringExtra("userIcon")).into(userIcon);
            tvCilckToClient.setText(data.getStringExtra("userName"));
            ivBackIcon.setVisibility(View.INVISIBLE);
            myApplication.userName = data.getStringExtra("userName");
            myApplication.userIcon = data.getStringExtra("userIcon");
            myApplication.isLogined=true;
        }
    }
}
