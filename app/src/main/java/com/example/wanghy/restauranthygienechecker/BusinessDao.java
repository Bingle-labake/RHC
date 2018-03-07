package com.example.wanghy.restauranthygienechecker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.wanghy.restauranthygienechecker.entity.Business;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface BusinessDao {
    @Insert
    void insertBusiness(Business business);

    @Query("SELECT * FROM Business WHERE bid=:bid LIMIT 1")
    Business retrieveBusinessByISBN(String bid);

    @Query("SELECT * FROM Business")
    List<Business> getAll();
}
