# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\develop\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
#-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.graphics.drawable.**
-keep public class * extends android.support.graphics.drawable.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

#############################################
#
# 项目中特殊处理部分
#
#############################################

#-----------处理反射类---------------



#-----------处理js交互---------------



#-----------处理实体类---------------
# 在开发的时候我们可以将所有的实体类放在一个包内，这样我们写一次混淆就行了。
#-keep public class com.ljd.example.entity.** {
#    public void set*(***);
#    public *** get*();
#    public *** is*();
#}


#-----------处理第三方依赖库---------
#   compile 'com.android.support:appcompat-v7:24.2.1'
#    compile 'com.android.support:design:24.2.1'
#    testCompile 'junit:junit:4.12'
#    compile 'com.orhanobut:logger:1.15'
#    compile project(':allrecycleview')
#    compile 'com.github.bumptech.glide:glide:3.7.0'
#    compile 'io.reactivex:rxjava:1.2.5'
#    compile 'io.reactivex:rxandroid:1.2.1'
#    compile project(':xdialog')
#    compile 'com.squareup.retrofit2:retrofit:2.1.0'
#    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
#    compile 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
#    compile 'com.squareup.okhttp3:logging-interceptor:3.5.0'
#    compile 'com.squareup.retrofit2:converter-scalars:2.1.0'
#    compile 'com.android.support:cardview-v7:24.2.1'
#    compile 'com.google.code.gson:gson:2.8.0'
#    compile project(':PushSDK')
#    compile 'com.umeng.analytics:analytics:latest.integration'
#
-dontwarn com.squareup.**
-dontwarn com.google.**
-keep class com.squareup.okio.**{*;}
-keep class com.squareup.okhttp3.**{*;}
-keep class com.google.code.gson.**{*;}

# adapter-rxjava
-dontwarn retrofit2.**
-keep class retrofit2.adapter.rxjava.**{*;}

#analytic 友盟
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}


#converter-gson
-keep class retrofit2.converter.gson.**{*;}
-keep class retrofit2.converter.scalars.**{*;}

-keep class android.support.design.**{*;}

-keep class android.support.test.espresso.**{*;}
-keep class com.google.**{*;}

-dontwarn dagger.**
-keep class dagger.internal.**{*;}
-keep class android.support.test.espresso.**{*;}
-keep class android.support.test.internal.runner.hidden.**{*;}
-keep class android.app.**{*;}

#glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{*;}
#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}

#
-dontwarn org.hamcrest.**
-dontwarn org.orhanobut.**
-dontwarn okhttp3.**
-dontwarn rx.**
-dontwarn retrofit2.**
-keep class org.hamcrest.**{*;}
-keep class com.orhanobut.logger.**{*;}
-keep class okhttp3.logging.**{*;}
-keep class okhttp3.**{*;}
-keep class okio.**{*;}
-keep class retrofit2.**{*;}
-keep class rx.android.**{*;}
-keep class rx.**{*;}

-dontwarn okio.**
-keepattributes EnclosingMethod

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

##okhttp
#-dontwarn okhttp3.**
#-keep class okhttp3.**{*;}
#-keep interface okhttp3.**{*;}
#
#
##okio
#-dontwarn okio.**
#-keep class okio.**{*;}
#-keep interface okio.**{*;}
#
#
#
#-keep class com.google.gson. {*;}
#-keep class com.google.gson.JsonObject { *; }
#
#
#-dontwarn com.orhanobut.**
#-keep class com.orhanobut.**{*;}
#-keep interface com.orhanobut.**{*;}
#
#-dontwarn  com.github.bumptech.glide.**
#-keep class com.github.bumptech.glide.**{*;}
#-keep interface com.github.bumptech.glide.**{*;}
#
#-dontwarn  io.reactivex.glide.**
#-keep class io.reactivex.glide.**{*;}
#-keep interface io.reactivex.glide.**{*;}
#
#
#
#
#-dontwarn  com.squareup.retrofit2.**
#-keep class com.squareup.retrofit2.**{*;}
#-keep interface com.squareup.retrofit2.**{*;}
#
#-dontwarn  com.google.code.gson.**
#-keep class com.google.code.gson.**{*;}
#-keep interface com.google.code.gson.**{*;}
#
#-dontwarn  com.umeng.analytics.**
#-keep class com.umeng.analytics.**{*;}
#-keep interface com.umeng.analytics.**{*;}
#
-dontwarn  cem.sang.com.allrecycleview.**
-keep class cem.sang.com.allrecycleview.**{*;}
-keep interface cem.sang.com.allrecycleview.**{*;}

-dontwarn  sang.com.xdialog.**
-keep class sang.com.xdialog.**{*;}
-keep interface sang.com.xdialog.**{*;}

-dontwarn  com.sang.viewfractory.**
-keep class com.sang.viewfractory.**{*;}
-keep interface com.sang.viewfractory.**{*;}


#-dontwarn  com.umeng.message.lib.**
#-keep class com.umeng.message.lib.**{*;}
#-keep interface com.umeng.message.lib.**{*;}
#
-keep class finance.com.fp.mode.bean.**{*;}
-keep interface finance.com.fp.mode.bean.**{*;}

