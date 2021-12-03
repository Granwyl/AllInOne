package id.ac.istts.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.istts.data.user;

@Dao
public interface userDAO {
    @Query("SELECT * FROM users")
    List<user> getUserList();

    @Query("Select * from users where username=:id")
    user getUser(String id);

    @Query("Select * from users where email=:em")
    user getUserbyemail(String em);

    @Query("Update users set saldo=:x where username=:m")
    void setSaldo(int x,String m);

    @Query("Update users set email=:x where username=:m")
    void cEmail(String x,String m);

    @Insert
    void insert(user u);

    @Update
    void update(user u);

    @Delete
    void delete(user u);
}
