package io.requery.android.database.sqlite;

/**
 *
 */
public interface SQLiteUpdateHook {

    /**
     *
     */
    void invoke(int operationType, String databaseName, String tableName, long rowId);
}
