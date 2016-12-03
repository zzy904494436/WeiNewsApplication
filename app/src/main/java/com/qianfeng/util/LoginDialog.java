package com.qianfeng.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianfeng.weinewsapplication.LoginActivity;
import com.qianfeng.weinewsapplication.R;

/**
 * Created by Administrator on 2016/11/17.
 */

public class LoginDialog {
    private Dialog dialog;
    public void loginDialog(final Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.login_dialog_layout,null);

        dialog = new Dialog(context,R.style.Translucent_NoTitle);

        dialog.setContentView(view);
        dialog.show();

        TextView ZaiGuangGuang = (TextView) view.findViewById(R.id.ZaiGuangGuang);
        TextView QuDengLu = (TextView) view.findViewById(R.id.QuDengLu);
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.ZaiGuangGuang){
                    dialog.dismiss();
                }else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                }
            }
        };
        ZaiGuangGuang.setOnClickListener(listener);
        QuDengLu.setOnClickListener(listener);
    }
}
