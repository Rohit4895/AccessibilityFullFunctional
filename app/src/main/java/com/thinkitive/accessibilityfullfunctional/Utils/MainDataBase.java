package com.thinkitive.accessibilityfullfunctional.Utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.thinkitive.accessibilityfullfunctional.dao.PackageNameOnlyDao;
import com.thinkitive.accessibilityfullfunctional.dao.UserInformationDataDao;
import com.thinkitive.accessibilityfullfunctional.model.PackageNamesOnly;
import com.thinkitive.accessibilityfullfunctional.model.UserInformationData;

@Database(entities = {UserInformationData.class, PackageNamesOnly.class}, version = 1)
public abstract class MainDataBase extends RoomDatabase {
    public abstract UserInformationDataDao userInformationDataDao();
    public abstract PackageNameOnlyDao packageNameOnlyDao();
}
