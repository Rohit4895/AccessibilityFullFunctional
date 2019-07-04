package com.thinkitive.accessibilityfullfunctional.viewModel.local;

import android.content.Context;

import com.thinkitive.accessibilityfullfunctional.Utils.AppExecutor;
import com.thinkitive.accessibilityfullfunctional.Utils.DataBaseClient;
import com.thinkitive.accessibilityfullfunctional.Utils.InterfaceDistributionClass;
import com.thinkitive.accessibilityfullfunctional.dao.PackageNameOnlyDao;
import com.thinkitive.accessibilityfullfunctional.model.PackageNamesOnly;
import com.thinkitive.accessibilityfullfunctional.model.UserInformationData;

import java.util.List;

public class CrudOperations {
    private Context context;

    public CrudOperations(Context context) {
        this.context = context;
    }

    public void insertAllPackages(final InterfaceDistributionClass.CallBackForInsertion callBackForInsertion,
                                  final List<String> packsgeNameList){
        AppExecutor.getInstance().getDiskIo().execute(new Runnable() {
            @Override
            public void run() {
                PackageNamesOnly packageNamesOnly = new PackageNamesOnly();
                PackageNameOnlyDao packageNameOnlyDao = DataBaseClient.getInstance(context)
                        .getMainDataBase().packageNameOnlyDao();

                for (int i=0; i<packsgeNameList.size(); i++){
                    packageNamesOnly.setPackageName(packsgeNameList.get(i));
                   // Log.d("waste",packsgeNameList.get(i));
                    packageNameOnlyDao.insertPackageNames(packageNamesOnly);
                }

                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBackForInsertion.insertionCompleted(1);
                    }
                });
            }
        });
    }

    public void getAllPackageList(final InterfaceDistributionClass.CallBackToPackageList callBackToPackageList){
        AppExecutor.getInstance().getDiskIo().execute(new Runnable() {
            @Override
            public void run() {
                final List<PackageNamesOnly> packageNamesList = DataBaseClient.getInstance(context)
                        .getMainDataBase().packageNameOnlyDao().getAllPackages();
                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBackToPackageList.getPackageList(packageNamesList);
                    }
                });
            }
        });
    }

    public void insertAllInformation(final InterfaceDistributionClass.CallBackForInsertion callBackForInsertion,
                                     final String user, final String password, final String packName ){
        AppExecutor.getInstance().getDiskIo().execute(new Runnable() {
            @Override
            public void run() {
                UserInformationData userInformationData = new UserInformationData();
                userInformationData.setUser(user);
                userInformationData.setPassword(password);
                userInformationData.setPackageName(packName);

                final long id = DataBaseClient.getInstance(context).getMainDataBase()
                        .userInformationDataDao().insertData(userInformationData);

                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBackForInsertion.insertionCompleted(id);
                    }
                });
            }
        });
    }

    public void getAllUser(final InterfaceDistributionClass.CallBackToList callBackToList){
        AppExecutor.getInstance().getDiskIo().execute(new Runnable() {
            @Override
            public void run() {
                final List<UserInformationData> list = DataBaseClient.getInstance(context).
                        getMainDataBase().userInformationDataDao().getAll();
                AppExecutor.getInstance().getDiskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBackToList.getList(list);
                    }
                });
            }
        });
    }
}

