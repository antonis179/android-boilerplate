# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizations code/removal/simple, code/removal/advanced, class/unboxing/enum
-optimizationpasses 3
-dontobfuscate
-dontwarn **

# Speed things up!
-libraryjars <java.home>/lib/rt.jar


# Keep
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable

-keepclassmembers class **.R$* {
 public static <fields>;
}

# Realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**


# Butterknife
-keep @interface butterknife.*

-keepclasseswithmembers class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembers class * {
    @butterknife.* <methods>;
}

-keepclasseswithmembers class * {
    @butterknife.On* <methods>;
}

-keep class **$$ViewInjector {
    public static void inject(...);
    public static void reset(...);
}

-keep class **$$ViewBinder {
    public static void bind(...);
    public static void unbind(...);
}