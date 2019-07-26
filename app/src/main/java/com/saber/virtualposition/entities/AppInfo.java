package com.saber.virtualposition.entities;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
public class AppInfo {

    private CharSequence name;
    private String packageName;
    private Intent intent;
    private Drawable icon;
    private String className;


    public AppInfo(){}

    /**
     * 设置启动程序的intent
     * @param componentName
     * @param launchFlags
     */
    public void setActivity(ComponentName componentName ,int launchFlags){

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(componentName);
        intent.setFlags(launchFlags);

    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
