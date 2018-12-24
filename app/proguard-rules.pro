-optimizationpasses 5
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes EnclosingMethod

-dontskipnonpubliclibraryclasses

-keep public class android.webkit.**
-dontwarn android.webkit.*
-keep class android.webkit.** { *; }

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * {
  public <init>(android.content.Context);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-dontwarn android.support.**

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn com.flytech.**

#libs jars
-keep class com.google.gson.** { *; }
-keep class com.qiniu.** { *; }
-keep class com.google.zxing.**{*;}
-keep class com.robotium.solo.**{*;}
-keep class com.baoyz.swipemenulistview.**{*;}

-keep class android_serialport_api.**{*;}
-keep class com.allinpay.aipmis.allinpay.**{*;}
-keep class com.pax.imglib.**{*;}
-keep class com.shishike.lib.**{*;}
-keep class com.baidu.**{*;}
-keep class com.androidpos.sysapi.**{*;}
-dontwarn com.androidpos.sysapi.**
-keep class com.dewo.tzc.**{*;}
-keep class com.landicorp.**{*;}
-keep class com.baidu.**{*;}
-keep class com.android.encryptcaculator.**{*;}
-keep class com.android.serialport.**{*;}
-keep class com.barcodeupdate.jni.**{*;}

-keep class org.apache.shiro.** { *; }
-dontwarn org.apache.shiro.**

-keep class com.watchdata.wdreader.**{*;}
#eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep public class com.squareup.**
-keep class com.squareup.**{*;}
-dontwarn com.squareup.**

-dontwarn org.springframework.**
#############################
-keep class android.support.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v7.**
-keep interface android.support.v7.app.** { *; }
-dontwarn android.support.**

-keep class * extends java.lang.annotation.Annotation {*;}
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers enum * {
  public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}