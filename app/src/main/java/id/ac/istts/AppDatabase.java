package id.ac.istts;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities={user.class},version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract userDAO userDAO();
    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE==null)
        {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class,
                    "userDB").build();
        }

        return INSTANCE;
    }
}

