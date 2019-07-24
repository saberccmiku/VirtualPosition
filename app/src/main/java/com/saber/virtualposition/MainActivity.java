package com.saber.virtualposition;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saber.virtualposition.adapter.CommonRecyclerAdapter;
import com.saber.virtualposition.adapter.CommonRecyclerViewHolder;
import com.saber.virtualposition.entities.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppInfo> mAppInfoList ;
    private  CommonRecyclerAdapter<AppInfo> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppInfoList = new ArrayList<>();
        mAdapter = new CommonRecyclerAdapter<AppInfo>(this,R.layout.view_app_detail,mAppInfoList) {
            @Override
            public void convertView(CommonRecyclerViewHolder holder, AppInfo appInfo) {
                holder.setText(R.id.textView,String.valueOf(appInfo.getName()));
                holder.setText(R.id.textView2,appInfo.getPackageName());
                holder.setBackground(R.id.imageView,appInfo.getIcon());
            }
        };

        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AppInfo appInfo = mAppInfoList.get(position);
                showAppDetail(appInfo);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        RecyclerView rv = findViewById(R.id.rv);
        rv.setAdapter(mAdapter);
        new Task().execute();
    }

    private class Task extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            if (mAppInfoList!=null){
                mAppInfoList.clear();
                mAppInfoList.addAll(loadApplications());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println(mAppInfoList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<AppInfo> loadApplications() {

        List<AppInfo> appInfoList = new ArrayList<>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(intent,0);
        Collections.sort(apps,new ResolveInfo.DisplayNameComparator(packageManager));
        if (apps!=null){
            for (ResolveInfo app : apps) {
                AppInfo appInfo = new AppInfo();
                appInfo.setName(app.loadLabel(packageManager));
                appInfo.setActivity(new ComponentName(app.activityInfo.applicationInfo.packageName,app.activityInfo.name),
                        Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                appInfo.setIcon(app.activityInfo.loadIcon(packageManager));
                appInfo.setPackageName(app.activityInfo.applicationInfo.packageName);
                appInfoList.add(appInfo);
            }
        }
        return appInfoList;
    }

    private void showAppDetail(AppInfo appInfo){
        Intent intent =new Intent(MainActivity.this,AppDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("appInfo",appInfo);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

}
