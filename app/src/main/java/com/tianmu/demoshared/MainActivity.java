package com.tianmu.demoshared;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.tianmu.demoshared.umeng.AuthActivity;
import com.tianmu.demoshared.umeng.SharedActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.btn_auth).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button://分享
                Intent it = new Intent(this, SharedActivity.class);
                startActivity(it);

                break;
            case R.id.btn_auth:
                Intent it2 = new Intent(this, AuthActivity.class);
                startActivity(it2);
        }

    }


}
