package com.tianmu.demoshared.umeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tianmu.demoshared.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    private UMShareAPI mShareAPI = null;
    private AuthActivity mContext;
    private ImageView headImg;
    private TextView userName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        /** init auth api**/
        mContext = this;

        mShareAPI = UMShareAPI.get(mContext);
        initView();
    }

    private void initView() {
        findViewById(R.id.app_auth_qq).setOnClickListener(this);
        findViewById(R.id.app_auth_sina).setOnClickListener(this);
        findViewById(R.id.app_auth_weixin).setOnClickListener(this);
        headImg = (ImageView) findViewById(R.id.img_headImg);
        userName = (TextView) findViewById(R.id.text_userName);
    }

    /**
     * auth callback interface
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            Log.d("user info", "user info:" + data.toString());

            /**================================================
             * 得到登陆后的信息
             */
            UMShareAPI umShareAPI = UMShareAPI.get(mContext);

            mShareAPI.getPlatformInfo(mContext, platform, new UMAuthListener() {
                @Override
                public void onComplete(SHARE_MEDIA plat, int i, Map<String, String> map) {

                    if (map != null) {
                        //判断一下什么平台，不同平台返回的数据格式不同
                        if (plat == SHARE_MEDIA.SINA) {

                            String result = map.get("result");
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String screen_name = jsonObject.getString("screen_name");
                                String profile_image_url = jsonObject.getString("profile_image_url");
                                userName.setText(screen_name);
                                ImageLoader.getInstance().displayImage(profile_image_url, headImg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        if (plat == SHARE_MEDIA.QQ || plat == SHARE_MEDIA.QZONE) {

                            String screenName = map.get("screen_name").toString();
                            String imageUrl = map.get("profile_image_url").toString();
                            ImageLoader.getInstance().displayImage(imageUrl, headImg);
                            userName.setText(screenName);
                            Log.d("auth callbacl", "getting data");

//                            Toast.makeText(getApplicationContext(), map.toString(), Toast.LENGTH_SHORT).show();

                        }


                    }
                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    Toast.makeText(getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        {
            SHARE_MEDIA platform = null;
            if (view.getId() == R.id.app_auth_sina) {
                platform = SHARE_MEDIA.SINA;
            } else if (view.getId() == R.id.app_auth_qq) {
                platform = SHARE_MEDIA.QQ;
            } else if (view.getId() == R.id.app_auth_weixin) {
                platform = SHARE_MEDIA.WEIXIN;
            }
            /**begin invoke umeng api**/

            mShareAPI.doOauthVerify(mContext, platform, umAuthListener);
        }
    }
}
