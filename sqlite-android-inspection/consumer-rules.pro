# App Inspection / Database Inspector (vendored androidx sqlite-inspection + io.requery hooks)
-keep class androidx.sqlite.inspection.** { *; }
-keep class androidx.inspection.** { *; }
-keepclassmembers class io.requery.android.database.sqlite.SQLiteDatabase {
    public static *** openDatabase(...);
    public *** rawQueryWithFactory(...);
    void endTransaction();
}
-keep class io.requery.android.database.sqlite.SQLiteClosable { *; }
-keep class io.requery.android.database.sqlite.SQLiteCursor { *; }
-keep class io.requery.android.database.sqlite.SQLiteStatement { *; }
