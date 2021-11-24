package id.ac.istts.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.ac.istts.data.barang;

@Database(entities = {barang.class},version = 1)
public abstract class AppDatabaseBarang extends RoomDatabase {
    public abstract barangDAO barangDAO();
    private static AppDatabaseBarang INSTANCE;

    public static AppDatabaseBarang getAppDatabaseBarang(Context context) {
        if (INSTANCE==null)
        {
            INSTANCE = Room.databaseBuilder(context, AppDatabaseBarang.class,
                    "barangDB").build();
        }

        return INSTANCE;
    }

}
