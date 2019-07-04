package com.example.accessibilityfullfunctional.Utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.accessibilityfullfunctional.dao.PackageNameOnlyDao;
import com.example.accessibilityfullfunctional.dao.UserInformationDataDao;
import com.example.accessibilityfullfunctional.model.PackageNamesOnly;
import com.example.accessibilityfullfunctional.model.UserInformationData;

@Database(entities = {UserInformationData.class, PackageNamesOnly.class}, version = 1)
public abstract class MainDataBase extends RoomDatabase {
    public abstract UserInformationDataDao userInformationDataDao();
    public abstract PackageNameOnlyDao packageNameOnlyDao();
}
