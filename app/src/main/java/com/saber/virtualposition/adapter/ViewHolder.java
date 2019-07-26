package com.saber.virtualposition.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 自定义通用的viewHolder
 * Created by Administrator on 2016/6/14
 */
public class ViewHolder  {
    private final SparseArray<View> mViews;
    private View mConvertView;
    private int position;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        //setTag
        mConvertView.setTag(this);
        this.position = position;
    }

    /**
     * 拿到一个viewHolder对象
     * @param context 当前activity
     * @param convertView 当前布局
     * @param parent  布局样式
     * @param layoutId 当前布局id
     * @param position 当前控件位置
     * @return viewHolder对象
     */

    public static ViewHolder getViewHolder(Context context, View convertView,
                                           ViewGroup parent, int layoutId, int position) {

        if (convertView==null){
            return new ViewHolder(context,parent,layoutId,position);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     * @param viewId 控件id
     * @param <T> 泛型
     * @return 控件
     */
    public <T extends View> T getView (int viewId){
        View view = mViews.get(viewId);
        if (view==null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        //消除转换警告
        //noinspection unchecked
        return (T) view;
    }

    /**
     * 获取convertView
     * @return convertView
     */
    public View getConvertView(){
        return mConvertView;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 为TextView设置字符串
     * @param viewId 控件id
     * @param text 要设置的字符串内容
     * @return viewHolder
     */
    public ViewHolder setText(int viewId ,String text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setImage(int viewId,int drawableId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(drawableId);
        return this;
    }

    public ViewHolder setImage(int viewId, Bitmap bitmap){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setBackgroundResource(int viewId,int drawableId){
        ImageView imageView = getView(viewId);
        imageView.setBackgroundResource(drawableId);
        return this;
    }


}