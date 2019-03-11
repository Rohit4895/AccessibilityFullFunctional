package com.example.accessibilityfullfunctional.service.Utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.accessibilityfullfunctional.service.dao.PackageNameOnlyDao;
import com.example.accessibilityfullfunctional.service.dao.UserInformationDataDao;
import com.example.accessibilityfullfunctional.service.model.PackageNamesOnly;
import com.example.accessibilityfullfunctional.service.model.UserInformationData;

@Database(entities = {UserInformationData.class, PackageNamesOnly.class}, version = 1)
public abstract class MainDataBase extends RoomDatabase {
    public abstract UserInformationDataDao userInformationDataDao();
    public abstract PackageNameOnlyDao packageNameOnlyDao();
}
