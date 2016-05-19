#Android SQLite support library

[![Build Status](https://travis-ci.org/requery/sqlite-android.svg?branch=master)](https://travis-ci.org/requery/sqlite-android)
[ ![Download](https://api.bintray.com/packages/requery/requery/sqlite-android/images/download.svg) ](https://bintray.com/requery/requery/sqlite-android/_latestVersion)

This is an Android specific distribution of the latest versions of SQLite. It contains the latest
SQLite version and the Android specific database APIs derived from AOSP packaged as an AAR
library distributed on jcenter.

Why?
----

- **Consistent**
- **Faster**
- **Up-to-date**

Even the latest version of Android is several versions behind the latest version of SQLite.
Theses versions do not have the bug fixes, performance improvements, or new features present in
current versions of SQLite. This problem is worse the older the version of the OS the device has.
Using this library you can keep up to date with the latest versions of SQLite and provide a
consistent version across OS versions and devices.

Use new SQLite features:

- **[JSON1 extension](https://www.sqlite.org/json1.html)**
- **[Common Table expressions](https://www.sqlite.org/lang_with.html)**
- **[Indexes on expressions](https://www.sqlite.org/expridx.html)**

Performance
-----------

![chart](http://requery.github.io/sqlite-android/performance.png)

This library contains an optimized version of the Android database wrapper API native code that
along with newer optimized sqlite versions provide better performance than the current Android
database API in generally all cases.

On mid range devices running Android KitKat this library can be up to 40% faster on read operations.
Performance varies greatly between different devices and OS versions. On newer OS versions &
devices the performance improvement is generally smaller as those versions contain newer SQLite
versions.

Usage
-----

```gradle
dependencies {
    compile 'io.requery:sqlite-android:3.13.0'
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
for performance reasons the cursors are not cross process by default.

CPU Architectures
-----------------

The native library is built for the following CPU architectures:

- `armeabi-v7a`
- `arm64-v8a`
- `x86`

However you may not want to include all binaries in your apk. You can exclude certain variants by
using `packagingOptions`:

```gradle
android {
    packagingOptions {
        exclude 'lib/arm64-v8a/libsqlite3x.so'
        exclude 'lib/x86/libsqlite3x.so'
    }
}
```

The size of the artifacts with only the armeabi-v7a binary is **~500kb**. In general you can use
armeabi-v7a on the majority of Android devices including Intel Atom which provides a native
translation layer, however performance under the translation layer is worse than using the x86
binary.

Requirements
------------

The min SDK level is API level 9 (Gingerbread).

Versioning
----------

The library is versioned after the version of SQLite it contains. For changes specific to just the
wrapper API a revision number is added e.g. 3.12.0-X, where X is the revision number.

Acknowledgements
----------------
This project is based on the AOSP code and the [Android SQLite bindings](https://www.sqlite.org/android/doc/trunk/www/index.wiki)
No official distributions are made from the Android SQLite bindings it and it has not been updated
in a while, this project starts there and makes significant changes:

Changes
-------

- **Fast read performance:** The original SQLite bindings filled the CursorWindow using it's
  Java methods from native C++. This was because there is no access to the native CursorWindow
  native API from the NDK. Unfortunately this slowed read performance significantly (roughly 2x
  worse vs the android database API) because of extra JNI roundtrips. This has been rewritten
  without the JNI to Java calls (so more like the original AOSP code) and also using a local memory
  CursorWindow.
- Reuse of android.database.sqlite.*, the original SQLite bindings replicated the entire
  android.database.sqlite API structure including exceptions & interfaces. This project does not
  do that, instead it reuses the original classes/interfaces when possible in order to simplify
  migration and/or use with existing code.
- Unit tests added
- Compile with [clang](http://clang.llvm.org/) toolchain
- Compile with FTS3, FTS4, & JSON1 extension
- Migrate to gradle build
- buildscript dynamically fetches and builds the latest sqlite source from sqlite.org
- Added consumer proguard rules
- Use support-v4 version of `CancellationSignal`
- Fix bug in `SQLiteOpenHelper.getDatabaseLocked()` wrong path to `openOrCreateDatabase`
- Fix removed members in AbstractWindowCursor
- Made the AOSP code (mostly) warning free but still mergable from source
- Deprecated classes/methods removed
- Loadable extension support

License
-------

    Copyright (C) 2016 requery.io
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
