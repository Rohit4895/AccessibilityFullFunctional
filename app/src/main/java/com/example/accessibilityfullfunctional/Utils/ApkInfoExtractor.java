package com.example.accessibilityfullfunctional.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.accessibilityfullfunctional.R;

import java.util.ArrayList;
import java.util.List;

public class ApkInfoExtractor {
    private Context context;

    public ApkInfoExtractor(Context context){
        this.context = context;
    }

    public List<String> getAllInstalledApkInfo(){
        List<String> ApkPackageName = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent,0);

        for (ResolveInfo resolveInfo: resolveInfoList){
            ActivityInfo activityInfo = resolveInfo.activityInfo;

            if (!isSystemPackage(resolveInfo)){
                ApkPackageName.add(activityInfo.applicationInfo.packageName);
            }
        }
        return ApkPackageName;
    }

    public boolean isSystemPackage(ResolveInfo resolveInfo){
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public Drawable getAppIconByPackageName(String ApkPackageName){
        Drawable drawable;
        try {
            drawable = context.getPackageManager().getApplicationIcon(ApkPackageName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            drawable = ContextCompat.getDrawable(context, R.mipmap.ic_launcher_accessibility);
        }

        return drawable;
    }

    public String getAppName(String ApkPackageName){
        String name = "";
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(ApkPackageName,0);
            if (applicationInfo != null){
                name = (String) packageManager.getApplicationLabel(applicationInfo);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}
