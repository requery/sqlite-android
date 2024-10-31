# Android SQLite support library

![Build Status](https://github.com/requery/sqlite-android/actions/workflows/ci.yml/badge.svg)
[![Download](https://jitpack.io/v/requery/sqlite-android.svg)](https://jitpack.io/#requery/sqlite-android)

This is an Android specific distribution of the latest versions of SQLite.
It contains the latest SQLite version and the Android specific database APIs
derived from AOSP packaged as an AAR library distributed on jitpack.

Why?
----

- **Consistent**
- **Faster**
- **Up-to-date**

Even the latest version of Android is several versions behind the latest version of SQLite.
These versions do not have bug fixes, performance improvements, or new features present in
current versions of SQLite. This problem is worse the older the version of the OS the device has.
Using this library, you can keep up to date with the latest versions of SQLite and provide a
consistent version across OS versions and devices.

Use new SQLite features:

- **[JSON1 extension](https://www.sqlite.org/json1.html)**
- **[Common Table expressions](https://www.sqlite.org/lang_with.html)**
- **[Indexes on expressions](https://www.sqlite.org/expridx.html)**
- **[Full-Text Search 5](https://www.sqlite.org/fts5.html)**
- **[Generated Columns](https://www.sqlite.org/gencol.html)**
- **[DROP COLUMN support](https://www.sqlite.org/lang_altertable.html#altertabdropcol)**

Usage
-----

Follow the guidelines from [jitpack.io](https://jitpack.io) to add the JitPack repository 
to your build file if you have not.

Typically, this means an edit to your `build.gradle` file to add a new `repository` definition 
in the `allprojects` block, like this:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Then add the sqlite-android artifact from this repository as a dependency:

```gradle
dependencies {
    implementation 'com.github.requery:sqlite-android:3.47.0'
}
```
Then change usages of `android.database.sqlite.SQLiteDatabase` to
`io.requery.android.database.sqlite.SQLiteDatabase`, similarly extend
`io.requery.android.database.sqlite.SQLiteOpenHelper` instead of
`android.database.sqlite.SQLiteOpenHelper`. Note similar changes maybe required for classes that
depended on `android.database.sqlite.SQLiteDatabase` equivalent APIs are provided in the
`io.requery.android.database.sqlite` package.

If you expose `Cursor` instances across processes you should wrap the returned cursors in a
[CrossProcessCursorWrapper](http://developer.android.com/reference/android/database/CrossProcessCursorWrapper.html)
for performance reasons the cursors are not a cross process by default.

### Support library compatibility

The library implements the SupportSQLite interfaces provided by the support library. Use
`RequerySQLiteOpenHelperFactory` to obtain an implementation of `(Support)SQLiteOpenHelper` based
on a `SupportSQLiteOpenHelper.Configuration` and `SupportSQLiteOpenHelper.Callback`.

This also allows you to use sqlite-android with libraries like Room by passing an instance
of `RequerySQLiteOpenHelperFactory` to them.


CPU Architectures
-----------------

The native library is built for the following CPU architectures:

- `armeabi-v7a` ~1.2 MB
- `arm64-v8a` ~1.7 MB
- `x86` ~1.7 MB
- `x86_64` ~1.8 MB

However, you may not want to include all binaries in your apk.
You can exclude certain variants by using `packagingOptions`:

```gradle
android {
    packagingOptions {
        exclude 'lib/armeabi-v7a/libsqlite3x.so'
        exclude 'lib/arm64-v8a/libsqlite3x.so'
        exclude 'lib/x86/libsqlite3x.so'
        exclude 'lib/x86_64/libsqlite3x.so'
    }
}
```

The size of the artifacts with only the armeabi-v7a binary is **~1.2 MB**.
In general, you can use armeabi-v7a on the majority of Android devices including Intel Atom
which provides a native translation layer, however, performance under the translation layer
is worse than using the x86 binary.

Note that starting August 1, 2019, your apps published on Google Play will [need to support 64-bit architectures](https://developer.android.com/distribute/best-practices/develop/64-bit).

Requirements
------------

The min SDK level is API level 21 (Lollipop).

Versioning
----------

The library is versioned after the version of SQLite it contains. For changes specific to just the
wrapper API, a revision number is added e.g., 3.47.0-X, where X is the revision number.

Acknowledgements
----------------
This project is based on the AOSP code and the [Android SQLite bindings](https://www.sqlite.org/android/doc/trunk/www/index.wiki)
No official distributions are made from the Android SQLite bindings it, and it has not been updated
in a while, this project starts there and makes significant changes:

Changes
-------

- **Fast read performance:** The original SQLite bindings filled the CursorWindow using its
  Java methods from native C++. This was because there is no access to the native CursorWindow
  native API from the NDK. Unfortunately, this slowed read performance significantly (roughly 2x
  worse vs the android database API) because of extra JNI roundtrips. This has been rewritten
  without the JNI to Java calls (so more like the original AOSP code) and also using a local memory
  CursorWindow.
- Reuse of android.database.sqlite.*, the original SQLite bindings replicated the entire
  android.database.sqlite API structure including exceptions & interfaces. This project does not
  do that, instead it reuses the original classes/interfaces when possible to simplify
  migration and/or use with existing code.
- Unit tests added
- Compile with [clang](http://clang.llvm.org/) toolchain
- Compile with FTS3, FTS4, & JSON1 extension
- Migrate to Gradle build
- buildscript dynamically fetches and builds the latest sqlite source from sqlite.org
- Added consumer proguard rules
- Use androidx-core version of `CancellationSignal`
- Fix bug in `SQLiteOpenHelper.getDatabaseLocked()` wrong path to `openOrCreateDatabase`
- Fix removed members in AbstractWindowCursor
- Made the AOSP code (mostly) warning free but still mergable from source
- Deprecated classes/methods removed
- Loadable extension support
- STL dependency removed

License
-------

    Copyright (C) 2017-2024 requery.io
    Copyright (C) 2005-2012 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
