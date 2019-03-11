package com.example.accessibilityfullfunctional.service.Utils;

import com.example.accessibilityfullfunctional.service.model.PackageNamesOnly;
import com.example.accessibilityfullfunctional.service.model.UserInformationData;

import java.util.List;

public class InterfaceDistributionClass {
    public interface CallBackToList{
        void getList(List<UserInformationData> list);
    }

    public interface CallBackToPackageList{
        void getPackageList(List<PackageNamesOnly> list);
    }

    public interface CallBackToService{
        void getUserList(List<UserInformationData> list);
    }

    public interface CallBackForInsertion{
        void insertionCompleted(long id);
    }

    public interface OnClickAppSelect{
        void getAppName(String appPackageName);
    }
}
