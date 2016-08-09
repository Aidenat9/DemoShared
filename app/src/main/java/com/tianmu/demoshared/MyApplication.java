package com.tianmu.demoshared;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by TianMu on 2016/8/4 0004.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initShare();
        //初始化UniversialImageLoader
        initImgloader();

    }

    private void initImgloader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .build();
        ImageLoader.getInstance().init(config);

    }

    /**
     * 初始化分享入口
     */
    private void initShare() {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3896161182","2e788dc89182943d131c837e54ef54b3");
        //新浪微博 appkey appsecret
        //在微博开放平台设置的授权回调URL必须与代码中设置一致
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
        PlatformConfig.setQQZone("1105595732", "o2kwktwKWlc51Z2p");
        // QQ和Qzone appid appkey
    }
}
