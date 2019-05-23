package com.zhhl.android.facesdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhhl.android.libfacesdk.AppKeyAuthErrorCallback;
import com.zhhl.android.libfacesdk.FaceSdk;
import com.zhhl.android.libfacesdk.IdentityCallback;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private Button select_btn;
    private EditText mIdcardEdit;

    public static final String AppKey = "7d55df3e5a9a4041b91d35f998f3959c";

    private String TAG = getClass().getSimpleName();

    FaceSdk faceSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.info_tv);
        mIdcardEdit = findViewById(R.id.idcard_edit);
        select_btn = findViewById(R.id.select_btn);
        select_btn.setOnClickListener(this);

        /**
         * 初始化 face sdk  ,appkey 不正确会回调此函数
         * {@link AppKeyAuthErrorCallback#callback(String)}
         */
        faceSdk = FaceSdk.init(AppKey, new AppKeyAuthErrorCallback() {
            @Override
            public void callback(String s) {
                Log.e(TAG, "callback: " + s);
            }
        });
        faceSdk.registerCallback(new WeakReference<>(this), new IdentityCallback() {
            @Override
            public void result(boolean success, String token) {
                Log.e("result: ===========", success + "====" + token);
                Toast.makeText(MainActivity.this, success ? "验证成功" : "验证失败"+token, Toast.LENGTH_SHORT).show();

            }

        });
    }



    @Override
    public void onClick(View v) {
        String idcard = mIdcardEdit.getText().toString();
        String i_name = name.getText().toString();

        if (TextUtils.isEmpty(idcard) || TextUtils.isEmpty(i_name)) {
            Toast.makeText(this, "身份证/姓名 为空", Toast.LENGTH_SHORT).show();
            return;
        }

        faceSdk.authUser(i_name, idcard);
    }
}


class InterfaceDescription {


    public static class FaceSdk {

        /**
         * I 初始化face sdk
         *
         * @param AppKey   appkey
         * @param callback AppKey验证失败会回调{@link AppKeyAuthErrorCallback#callback(String)}函数
         * @return face sdk
         */
        public static FaceSdk init(String AppKey, AppKeyAuthErrorCallback callback) {
            throw new RuntimeException("stub");
        }

        /**
         * II 注册回调参数
         *
         * @param activity 流程执行完 退回到 {@link WeakReference#get()}{@code instance Activity}
         * @param callback 流程执行完数据回传到此接口
         */
        public void registerCallback(WeakReference<Activity> activity, IdentityCallback callback) {
            throw new RuntimeException("stub");
        }

        /**
         * III 校验用户
         *
         * @param name   用户姓名
         * @param idCode 用户身份证号
         */
        public void authUser(String name, String idCode) {
            throw new RuntimeException("stub");
        }

    }

}
