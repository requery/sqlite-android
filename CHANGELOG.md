Change Log
==========

## 3.18.0

- [SQLite 3.18.0](https://sqlite.org/releaselog/3_18_0.html)
- Fix conversion of strings larger than the available stack size (#35)

## 3.17.0

- [SQLite 3.17.0](https://sqlite.org/releaselog/3_17_0.html)

## 3.16.2

- [SQLite 3.16.2](https://sqlite.org/releaselog/3_16_2.html)
- Support additional SQLite open flags
- Add methods from DatabaseUtils into base classes

## 3.16.0

- [SQLite 3.16.0](https://sqlite.org/releaselog/3_16_0.html)

## 3.15.1

- [SQLite 3.15.1](https://sqlite.org/releaselog/3_15_1.html)

## 3.15.0

- [SQLite 3.15.0](https://www.sqlite.org/releaselog/3_15_0.html)

## 3.14.2

- [SQLite 3.14.2](https://www.sqlite.org/releaselog/3_14_2.html)
- Removed code that disabled WAL when executing a ATTACH statement

## 3.14.1

- [SQLite 3.14.1](https://www.sqlite.org/releaselog/3_14_1.html)

## 3.14.0

- [SQLite 3.14.0](https://www.sqlite.org/releaselog/3_14.html)
- Support return value in `SQLiteDatabase.CustomFunction`
- Support `Object[]` array in `SQLiteDatabase` query methods

## 3.13.0-3

- Support x86_64 target

## 3.13.0-2

- More proguard rules fixes

## 3.13.0-1

- Fix proguard rules file

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