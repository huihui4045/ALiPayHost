package com.alizhezi.pay.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

public class PluginManager {



    private static final PluginManager ourInstance = new PluginManager();

    private DexClassLoader mDexClassLoader;

    private Resources mResources;

    private String TAG=this.getClass().getSimpleName();
    private PackageInfo packageInfo;
    private Resources.Theme mTheme;

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {


    }

    public void loadPath(Context context,String pluginApkName) {

        File extractFile = context.getFileStreamPath(pluginApkName);

        String dexPath=extractFile.getPath();

        File fileRelease = context.getDir("plugin", Context.MODE_PRIVATE);

        Log.e(TAG,"extractFile  dexPath:"+extractFile.getPath());
        Log.e(TAG,"fileRelease:"+fileRelease.getAbsolutePath());

        mDexClassLoader=new DexClassLoader(dexPath,fileRelease.getAbsolutePath(),null,context.getClassLoader());


        PackageManager packageManager=context.getPackageManager();
        packageInfo=packageManager.getPackageArchiveInfo(dexPath,PackageManager.GET_ACTIVITIES);

        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);

            addAssetPath.invoke(assetManager,dexPath);



        }  catch (Exception e) {
            e.printStackTrace();

            Log.e(TAG,"loadResources:"+e.getMessage());
            Toast.makeText(context,"loadResources:"+e.getMessage(),Toast.LENGTH_LONG).show();

        }

        mResources=new Resources(assetManager,context.getResources().getDisplayMetrics(),
                context.getResources().getConfiguration());

        mTheme=mResources.newTheme();

        mTheme.setTo(context.getTheme());

        parseReceivers(context, dexPath);


    }

    private void parseReceivers(Context context, String path) {


        try {

            Class   packageParserClass = Class.forName("android.content.pm.PackageParser");
            Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);
            Object packageParser = packageParserClass.newInstance();
            Object packageObj=  parsePackageMethod.invoke(packageParser, new File(path), PackageManager.GET_ACTIVITIES);

            Field receiverField=packageObj.getClass().getDeclaredField("receivers");
//拿到receivers  广播集合    app存在多个广播   集合  List<Activity>  name  ————》 ActivityInfo   className
            List receivers = (List) receiverField.get(packageObj);

            Class<?> componentClass = Class.forName("android.content.pm.PackageParser$Component");
            Field intentsField = componentClass.getDeclaredField("intents");


            // 调用generateActivityInfo 方法, 把PackageParser.Activity 转换成
            Class<?> packageParser$ActivityClass = Class.forName("android.content.pm.PackageParser$Activity");
//            generateActivityInfo方法
            Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
            Object defaltUserState= packageUserStateClass.newInstance();
            Method generateReceiverInfo = packageParserClass.getDeclaredMethod("generateActivityInfo",
                    packageParser$ActivityClass, int.class, packageUserStateClass, int.class);
            Class<?> userHandler = Class.forName("android.os.UserHandle");
            Method getCallingUserIdMethod = userHandler.getDeclaredMethod("getCallingUserId");
            int userId = (int) getCallingUserIdMethod.invoke(null);

            for (Object activity : receivers) {
                ActivityInfo info= (ActivityInfo) generateReceiverInfo.invoke(packageParser,  activity,0, defaltUserState, userId);
                BroadcastReceiver broadcastReceiver= (BroadcastReceiver) mDexClassLoader.loadClass(info.name).newInstance();
                List<? extends IntentFilter> intents= (List<? extends IntentFilter>) intentsField.get(activity);
                for (IntentFilter intentFilter : intents) {
                    context.registerReceiver(broadcastReceiver, intentFilter);
                }
            }
            //generateActivityInfo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 把Assets里面得文件复制到 /data/data/files 目录下
     */
    public  void extractAssets(Context context,String pluginApkName) {
        Utils.extractAssets(context,pluginApkName);
    }

    public DexClassLoader getmDexClassLoader() {
        return mDexClassLoader;
    }

    public Resources getmResources() {
        return mResources;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public Resources.Theme getmTheme() {
        return mTheme;
    }
}
