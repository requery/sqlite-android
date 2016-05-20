-keepclasseswithmembers class io.requery.android.database.** {
  native <methods>;
  public <init>(...);
}
-keepnames class io.requery.android.database.** { *; }
-keep public class io.requery.android.database.sqlite.SQLiteCustomFunction { *; }
-keep public class io.requery.android.database.sqlite.SQLiteDebug** { *; }
