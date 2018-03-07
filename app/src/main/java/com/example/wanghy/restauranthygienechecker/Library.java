package com.example.wanghy.restauranthygienechecker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.wanghy.restauranthygienechecker.entity.Business;

@Database(entities = {Business.class}, version = 1, exportSchema = false)
public abstract class Library extends RoomDatabase {
        public abstract BusinessDao BusinessDao();
}
