package id.ac.istts.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import id.ac.istts.data.cartItem;
import id.ac.istts.data.user;

@Dao
public interface cartDao {
    @Insert
    void insert(cartItem u);

    @Update
    void update(cartItem u);

    @Delete
    void delete(cartItem u);
}
