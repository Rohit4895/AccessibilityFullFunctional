package com.thinkitive.accessibilityfullfunctional.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.thinkitive.accessibilityfullfunctional.Utils.InterfaceDistributionClass;
import com.thinkitive.accessibilityfullfunctional.model.PackageNamesOnly;
import com.thinkitive.accessibilityfullfunctional.viewModel.local.CrudOperations;

import java.util.List;

public class InsertionTaskViewModel extends AndroidViewModel implements InterfaceDistributionClass.CallBackForInsertion,
        InterfaceDistributionClass.CallBackToPackageList {

    private MutableLiveData<Boolean> insertionStatus;
    private MutableLiveData<List<PackageNamesOnly>> packageList;

    public InsertionTaskViewModel(@NonNull Application application) {
        super(application);
        insertionStatus = new MutableLiveData<>();
        packageList = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> insertUser(String user, String password, String packName){
        new CrudOperations(getApplication()).insertAllInformation(this,user,password,packName);
        return insertionStatus;
    }

    public LiveData<List<PackageNamesOnly>> getPackageNamesList(){
        new CrudOperations(getApplication()).getAllPackageList(this);
        return packageList;
    }

    @Override
    public void insertionCompleted(long id) {
        if (id == 0)
            return;
        insertionStatus.postValue(true);
    }

    @Override
    public void getPackageList(List<PackageNamesOnly> list) {
        packageList.postValue(list);
    }
}
