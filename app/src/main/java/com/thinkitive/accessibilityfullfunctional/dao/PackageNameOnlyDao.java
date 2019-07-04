package com.thinkitive.accessibilityfullfunctional.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.thinkitive.accessibilityfullfunctional.model.PackageNamesOnly;

import java.util.List;

@Dao
public interface PackageNameOnlyDao {
    @Insert
    long insertPackageNames(PackageNamesOnly packageNamesOnly);

    @Query("select * from PackageNamesOnly")
    List<PackageNamesOnly> getAllPackages();

    @Query("delete from PackageNamesOnly")
    void deleteAll();
}
