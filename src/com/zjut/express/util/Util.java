package com.zjut.express.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;

/**
 * {@code Util}工具类<br>
 * <li></li>Rules:使用前先获取实例 {@link #getInstance},部分静态方法可以直接由{@code Util}调用<br>
 * <li></li>对外方法
 * {@link #IsCanUseSdCard},
 * {@link #getMemoryClass},
 * {@link #hasExternalCacheDir}......
 * 
 * @author HuGuojun
 * @date 2013-11-23 下午11:02:10
 * @modify
 * @version 1.0.0
 */
public class Util {
	
	static String CACHESUFFIX = ".CACHEIMG";
	
	static Util utils = new Util();
	
	/**
	 * 获取{@link Util} 实例
	 * @param context {@link Context}
	 * @return Utils
	 */
	public static Util getInstance(){
		return utils;
	}
	
	/**
	 * 判断SDCard是否可用
	 * @return 可用返回true, 不可用返回false
	 * @category 静态公共方法 
	 */
	public static boolean IsCanUseSdCard() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}
	
	/**  
	 * 获取设备分配给app的可用内存
	 * @param context 
	 * @return 可以使用的内存大小
	 * @category 实例公共方法 
	 */
	public int getMemoryClass(Context context) {
        return ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();
	}
	
	/**
	 * 判断是否有内置的获取外部存储方法(Context.getExternalCacheDir())<br>
	 * 就是判断系统版本是否高于2.2
	 * @return 是返回true,否返回false
	 * @category 实例公共方法
	 */
	public boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}
	
	/**
	 * 检查邮箱格式
	 * @param email
	 * @return 格式正确返回true
	 */
	public static boolean checkEmail(String email){
		String emailPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}
	
	/**
	 * 检查号码格式
	 * @param mobile
	 * @return 格式正确返回true
	 */
	public static boolean checkMobile(String mobile){
		String mobilePattern = "^1[3,4,5,8]\\d{9}$";
		return mobile.matches(mobilePattern);
	}
	
	/** 
     * dip转换成px 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * px转换成dip 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale (DisplayMetrics类中属性scaledDensity)
     * @return
     */
    public static int sp2px(float spValue, float fontScale) {
    	return (int) (spValue * fontScale + 0.5f);
    }
    
    /**
     * dip转换成px 保证距离不变 
     * @param dpValue
     * @param scale (DisplayMetrics类中属性density)
     * @return
     */
    public static int dip2px(float dpValue, float scale) {
        return (int) (dpValue * scale + 0.5f);  
    }   
    
    /**
     * 获取 ScreenWidth
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
    	DisplayMetrics dm = new DisplayMetrics();  
		dm = context.getResources().getDisplayMetrics();  
		return dm.widthPixels;       
    }
    
    /**
     * 获取 ScreenHeight
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
    	DisplayMetrics dm = new DisplayMetrics();  
		dm = context.getResources().getDisplayMetrics();  
		return dm.heightPixels;
    }
}
