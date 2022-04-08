package com.example.practice_android;

import androidx.room.Dao;
import static androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = REPLACE)
    void insertProduct(Product product);

    @Query("SELECT * FROM products")
    List<Product> findAll();
}
