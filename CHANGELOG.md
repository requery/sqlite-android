Change Log
==========

## 3.13.0

- [SQLite 3.13.0](https://www.sqlite.org/releaselog/3_13_0.html)
- Updated proguard rules

## 3.12.2-2

- Minimum API level supported is now 9 (Gingerbread) previously was 15 (ICS)
- Fix missing support lib dependency missing from maven POM publish

## 3.12.2-1

- Fix native error code SQLITE_CANTOPEN(14) creating a database for the first time
- Fix SQLiteOpenHelper setWriteAheadLoggingEnabled flag not passed to openDatabase
- Change SQLiteGlobal default config values to match Android defaults

## 3.12.2

- [SQLite 3.12.2](https://www.sqlite.org/releaselog/3_12_2.html)

## 3.12.1-1

- Fix CursorWindow deactivate/close
- Fix Cursor setNotificationUri not working

## 3.12.1

- [SQLite 3.12.1](https://www.sqlite.org/releaselog/3_12_1.html)
- Support runtime extension loading
- Support custom functions
- `beginTransactionDeferred`/`beginTransactionWithListenerDeferred` added
- `CancellationSignal` dependency changed to support-v4 from app-compat
- Sources included in artifacts

## 3.12.0

- Initial release with SQLite 3.12.0