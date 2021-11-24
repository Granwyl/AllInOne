package id.ac.istts.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.ac.istts.data.user;

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

