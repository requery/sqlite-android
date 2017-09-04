package io.requery.android.database.sqlite;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.content.Context;

import io.requery.android.database.DatabaseErrorHandler;

/**
 * Implements {@link SupportSQLiteOpenHelper.Factory} using the SQLite implementation shipped in
 * this library.
 */
@SuppressWarnings("unused")
public final class RequerySQLiteOpenHelperFactory implements SupportSQLiteOpenHelper.Factory {

    @Override
    public SupportSQLiteOpenHelper create(Configuration config) {
        return new CallbackSQLiteOpenHelper(config.context, config.name, config.callback);
    }

    private static final class CallbackSQLiteOpenHelper extends SQLiteOpenHelper {

        private final Callback callback;

        CallbackSQLiteOpenHelper(Context context, String name, Callback cb) {
            super(context, name, null, cb.version, new CallbackDatabaseErrorHandler(cb));
            this.callback = cb;
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            callback.onConfigure(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            callback.onCreate(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            callback.onUpgrade(db, oldVersion, newVersion);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            callback.onDowngrade(db, oldVersion, newVersion);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            callback.onOpen(db);
        }
    }

    private static final class CallbackDatabaseErrorHandler implements DatabaseErrorHandler {

        private final Callback callback;

        CallbackDatabaseErrorHandler(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onCorruption(SQLiteDatabase db) {
            callback.onCorruption(db);
        }
    }
}
