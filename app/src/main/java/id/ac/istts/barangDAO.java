package id.ac.istts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface barangDAO {
    @Query("SELECT * FROM barang")
    List<barang> getBarangList();

    @Query("Select * from barang where nama=:id")
    barang getbarang(String id);

    @Insert
    void insert(barang b);

    @Update
    void update(barang b);

    @Delete
    void delete(barang b);
}
