package com.thinkitive.accessibilityfullfunctional.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;


import com.thinkitive.accessibilityfullfunctional.Utils.InterfaceDistributionClass;
import com.thinkitive.accessibilityfullfunctional.model.PackageNamesOnly;
import com.thinkitive.accessibilityfullfunctional.model.UserInformationData;
import com.thinkitive.accessibilityfullfunctional.viewModel.local.CrudOperations;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel implements InterfaceDistributionClass.CallBackToList,
        InterfaceDistributionClass.CallBackForInsertion, InterfaceDistributionClass.CallBackToPackageList,
        InterfaceDistributionClass.CallBackForDeletion {

    private MutableLiveData<List<UserInformationData>> userInformationDataList;
    private MutableLiveData<Boolean> insertionStatus;
    private MutableLiveData<Boolean> deletionStatus;
    private MutableLiveData<List<PackageNamesOnly>> packageNamesList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        userInformationDataList = new MutableLiveData<>();
        insertionStatus = new MutableLiveData<>();
        packageNamesList = new MutableLiveData<>();
        deletionStatus = new MutableLiveData<>();
    }

    public LiveData<List<UserInformationData>> getAllUserList(){
        new CrudOperations(getApplication()).getAllUser(this);
        return userInformationDataList;
    }

    public MutableLiveData<Boolean> insertList(List<String> packageNameList){
        new CrudOperations(getApplication()).insertAllPackages(this, packageNameList);
        return insertionStatus;
    }

    public MutableLiveData<Boolean> deleteUser(int userId){
        new CrudOperations(getApplication()).deleteUserFromDB(this, userId);
        return deletionStatus;
    }

    public LiveData<List<PackageNamesOnly>> getAllPackageList(){
        new CrudOperations(getApplication()).getAllPackageList(this);
        return packageNamesList;
    }

    @Override
    public void getList(List<UserInformationData> list) {
       // Log.d("waste","list: "+list.size());
        userInformationDataList.postValue(list);
    }

    @Override
    public void insertionCompleted(long id) {
        if (id == 0)
            return;
        insertionStatus.postValue(true);
    }

    @Override
    public void getPackageList(List<PackageNamesOnly> list) {
        packageNamesList.postValue(list);
    }

    @Override
    public void deletionCompleted(int id) {
        deletionStatus.postValue(true);
    }
}
