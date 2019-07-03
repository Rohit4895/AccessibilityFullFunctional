package com.example.accessibilityfullfunctional.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.accessibilityfullfunctional.service.model.UserInformationData;

import java.util.List;

@Dao
public interface UserInformationDataDao {

    @Insert
    long insertData(UserInformationData userInformationData);

    @Update
    int updateData(UserInformationData userInformationData);

    @Query("select * from UserInformationData")
    List<UserInformationData> getAll();

    @Query("select * from UserInformationData where packageName=:packageName")
    UserInformationData getAllUserAndPassword(String packageName);


}
