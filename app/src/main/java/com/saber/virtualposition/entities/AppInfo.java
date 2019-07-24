package com.saber.virtualposition.entities;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
public class AppInfo implements Parcelable {

    private CharSequence name;
    private String packageName;
    private Intent intent;
    private Drawable icon;

    public AppInfo(){}

    public AppInfo(Parcel in) {
        packageName = in.readString();
        intent = in.readParcelable(Intent.class.getClassLoader());
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(packageName);
        parcel.writeParcelable(intent, i);
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "name=" + name +
                ", packageName='" + packageName + '\'' +
                ", intent=" + intent +
                ", icon=" + icon +
                '}';
    }
}
