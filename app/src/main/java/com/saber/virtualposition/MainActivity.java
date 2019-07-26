package com.saber.virtualposition;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saber.virtualposition.adapter.CommonRecyclerAdapter;
import com.saber.virtualposition.adapter.CommonRecyclerViewHolder;
import com.saber.virtualposition.entities.AppInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppInfo> mAppInfoList;
    private CommonRecyclerAdapter<AppInfo> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppInfoList = new ArrayList<>();
        initRecycleView();
        new Task(this).execute();
    }

    private static class Task extends AsyncTask<String, String, String> {

        WeakReference<MainActivity> mWeakReference;

        Task(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... strings) {
            MainActivity activity = mWeakReference.get();
            if (activity.mAppInfoList != null) {
                activity.mAppInfoList.clear();
                activity.mAppInfoList.addAll(activity.loadApplications());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            MainActivity activity = mWeakReference.get();
            System.out.println(activity.mAppInfoList);
            activity.mAdapter.notifyDataSetChanged();
        }
    }

    private List<AppInfo> loadApplications() {

        List<AppInfo> appInfoList = new ArrayList<>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(packageManager));
        for (ResolveInfo app : apps) {
            AppInfo appInfo = new AppInfo();
            appInfo.setName(app.loadLabel(packageManager));
            appInfo.setActivity(new ComponentName(app.activityInfo.applicationInfo.packageName, app.activityInfo.name),
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            appInfo.setIcon(app.activityInfo.loadIcon(packageManager));
            appInfo.setPackageName(app.activityInfo.applicationInfo.packageName);
            appInfo.setClassName(app.activityInfo.name);
            appInfoList.add(appInfo);
        }
        return appInfoList;
    }


    public void startSelectedApp(ResolveInfo resolveInfo){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName componentName = new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,resolveInfo.activityInfo.name);
        intent.setComponent(componentName);
        startActivity(intent);
    }

    public void initRecycleView() {
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new CommonRecyclerAdapter<AppInfo>(this, R.layout.item_app_list, mAppInfoList) {
            @Override
            public void convertView(CommonRecyclerViewHolder holder, AppInfo appInfo) {
                holder.setText(R.id.tv_name, String.valueOf(appInfo.getName()));
                TextView tv_name = holder.getView(R.id.tv_name);
                Drawable drawable = appInfo.getIcon();
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_name.setCompoundDrawables(null, drawable, null, null);
            }
        };

        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                AppInfo appInfo = mAppInfoList.get(position);
                ComponentName componentName = new ComponentName(appInfo.getPackageName(),appInfo.getClassName());
                intent.setComponent(componentName);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rv.setAdapter(mAdapter);

    }

}
