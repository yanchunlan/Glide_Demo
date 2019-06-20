package com.example.glide_demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.library.glide.Glide;
import com.example.library.glide.RequestListener;
import com.example.library.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    private LinearLayout mLinear;


    private String url = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3950876957,1467991853&fm=26&gp=0.jpg";
    private String[] urls = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559067611298&di=269629fabab510d868bf17c89ffb166e&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F180201%2F9-1P201145015516.jpg"
            , "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=934222697,3928396438&fm=11&gp=0.jpg"
            , "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3967114459,4266717778&fm=26&gp=0.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559067733308&di=be001c108955c8f2bb862950b7e02d81&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201601%2F15%2F20160115151155_FeuKM.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559067855055&di=071a5963bea36f3bb84460d981c5ff28&imgtype=0&src=http%3A%2F%2Fi3.sinaimg.cn%2Fent%2F2014%2F0521%2FU2520P28DT20140521152117.jpg"
            , "https://ss0.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1449634080,114330435&fm=27&gp=0.jpg"
            , "https://ss0.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2742398684,1626866478&fm=26&gp=0.jpg"
            , "https://ss3.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3251808624,792931401&fm=26&gp=0.jpg"
            , "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1432937052,3680173361&fm=26&gp=0.jpg"
            , "https://ss0.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3436777124,1783395554&fm=26&gp=0.jpg"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtils.requestPermissions(this,
                PERMISSIONS_STORAGE,
                REQUEST_CODE_PERMISSIONS,
                okRunnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode == REQUEST_CODE_PERMISSIONS,
                grantResults,
                okRunnable,
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "没有获得必要的权限", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private Runnable okRunnable = new Runnable() {
        @Override
        public void run() {
            setContentView(R.layout.activity_main);
            initView();
        }
    };

    private void initView() {
        mLinear = (LinearLayout) findViewById(R.id.linear);
    }

    public void clickLoadSingle(View view) {
        ImageView iv = addChildImageView();

        // set glide imageView
        Glide.with(this)
                .load(url)
                .listener(new RequestListener() {
                    @Override
                    public boolean onSuccess(Bitmap bitmap) {
                        Toast.makeText(MainActivity.this, "single", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onFailure() {
                        return false;
                    }
                })
                .loading(R.mipmap.ic_launcher)
                .into(iv);

    }

    public void clickLoadMore(View view) {
        for (int i = 0; i < 10; i++) {
            ImageView iv = addChildImageView();
            Glide.with(this)
                    .load(urls[i]+"")
                    .loading(R.mipmap.ic_launcher)
                    .into(iv);
        }
    }

    public void clickRemoveAll(View view) {
        if (mLinear.getChildCount() > 0) {
            mLinear.removeAllViews();
        }
    }

    private ImageView addChildImageView() {
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mLinear.addView(iv);
        return iv;
    }
}
