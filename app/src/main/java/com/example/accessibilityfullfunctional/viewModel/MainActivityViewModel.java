package com.example.accessibilityfullfunctional.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.accessibilityfullfunctional.service.Utils.InterfaceDistributionClass;
import com.example.accessibilityfullfunctional.service.model.PackageNamesOnly;
import com.example.accessibilityfullfunctional.service.model.UserInformationData;
import com.example.accessibilityfullfunctional.viewModel.local.CrudOperations;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel implements InterfaceDistributionClass.CallBackToList,
        InterfaceDistributionClass.CallBackForInsertion, InterfaceDistributionClass.CallBackToPackageList {
    private MutableLiveData<List<UserInformationData>> userInformationDataList;
    private MutableLiveData<Boolean> insertionStatus;
    private MutableLiveData<List<PackageNamesOnly>> packageNamesList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        userInformationDataList = new MutableLiveData<>();
        insertionStatus = new MutableLiveData<>();
        packageNamesList = new MutableLiveData<>();
    }

    public LiveData<List<UserInformationData>> getAllUserList(){
        new CrudOperations(getApplication()).getAllUser(this);
        return userInformationDataList;
    }

    public MutableLiveData<Boolean> insertList(List<String> packageNameList){
        new CrudOperations(getApplication()).insertAllPackages(this, packageNameList);
        return insertionStatus;
    }

    public LiveData<List<PackageNamesOnly>> getAllPackageList(){
        new CrudOperations(getApplication()).getAllPackageList(this);
        return packageNamesList;
    }

    @Override
    public void getList(List<UserInformationData> list) {
        Log.d("waste","list: "+list.size());
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
}
