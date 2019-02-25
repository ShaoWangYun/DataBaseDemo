package com.imswy.databasedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class Utils {
    //向SP中存入数据
    public static void SaveDataToSP(Context context,String Name,String Value){
        SharedPreferences.Editor editor = context.getSharedPreferences("IOTData",Context.MODE_PRIVATE).edit();
        editor.putString(Name,Value);
        editor.commit();
    }

    //从SP中获取数据
    public static String ReadDataFromSP(Context context,String Name){
        SharedPreferences sp = context.getSharedPreferences("IOTData",Context.MODE_PRIVATE);
        return sp.getString(Name,"");
    }

    //Toast防误触
    private static Toast toast;
    //显示Toast
    public static void showToast(Context context,String toastInfo,int duration){
        if (toast == null) {
            if(duration == 0){
                toast = Toast.makeText(context,toastInfo,Toast.LENGTH_SHORT);
            }else{
                toast = Toast.makeText(context,toastInfo,Toast.LENGTH_LONG);
            }
        } else {
            toast.setText(toastInfo);
        }
        toast.show();
    }

    //显示Log
    public static void showLog(String Tag,String tagInfo){
        Log.i(Tag,tagInfo);
    }

}
