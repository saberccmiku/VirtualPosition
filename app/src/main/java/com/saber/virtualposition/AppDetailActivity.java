package com.saber.virtualposition;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.saber.virtualposition.entities.AppInfo;

public class AppDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        if (getIntent().getExtras()!=null) {
            AppInfo appInfo = getIntent().getExtras().getParcelable("appInfo");
            if (appInfo!=null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    findViewById(R.id.imageView).setBackground(appInfo.getIcon());
                } else {
                    findViewById(R.id.imageView).setBackgroundDrawable(appInfo.getIcon());
                }
                TextView textView = findViewById(R.id.textView);
                textView.setText(appInfo.getName());
                TextView textView2 = findViewById(R.id.textView2);
                try {
                    String[] permissions = getAppPackInfo(appInfo.getPackageName()).requestedPermissions;
                    StringBuilder sb = new StringBuilder();
                    for (String permission : permissions) {
                        sb.append(permission).append("\n");
                    }
                    textView2.setText(sb.toString());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public PackageInfo getAppPackInfo (String packageName) throws PackageManager.NameNotFoundException {

        return getPackageManager().getPackageInfo(packageName,PackageManager.GET_ACTIVITIES|PackageManager.GET_PERMISSIONS);

    }
}
