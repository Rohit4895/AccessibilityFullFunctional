package com.thinkitive.accessibilityfullfunctional.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.thinkitive.accessibilityfullfunctional.R;
import com.thinkitive.accessibilityfullfunctional.Utils.ApkInfoExtractor;
import com.thinkitive.accessibilityfullfunctional.model.PackageNamesOnly;
import com.thinkitive.accessibilityfullfunctional.model.UserInformationData;
import com.thinkitive.accessibilityfullfunctional.view.adapter.PackageInfoAdapter;
import com.thinkitive.accessibilityfullfunctional.viewModel.MainActivityViewModel;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private String packName;
    private RecyclerView recyclerView;
    private int INSERT_ACTIVITY_REQUEST = 1;
    private int ACCESSIBILITY_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);

        int accessibilityEnabled = 0;

        try {
            accessibilityEnabled = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
            //Log.d("demo","Status: "+accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 0) {
            createDialog("To continue with app, you have to on accessibility service for A3utofill.\n Do you want to continue..?");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InsertionTask.class);
                startActivityForResult(intent,INSERT_ACTIVITY_REQUEST);
            }
        });

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));

        final PackageInfoAdapter packageInfoAdapter = new PackageInfoAdapter(this,
                new ArrayList<UserInformationData>());
        recyclerView.setAdapter(packageInfoAdapter);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainActivityViewModel.getAllUserList().observe(MainActivity.this,
                new Observer<List<UserInformationData>>() {
            @Override
            public void onChanged(@Nullable List<UserInformationData> list) {
              //  Log.d("waste","On Create: "+list.size());
                packageInfoAdapter.addItems(list);
            }
        });

        mainActivityViewModel.getAllPackageList().observe(this, new Observer<List<PackageNamesOnly>>() {
            @Override
            public void onChanged(@Nullable List<PackageNamesOnly> packageNamesList) {
                if (packageNamesList.size() == 0){
                    ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(MainActivity.this);
                    List<String> apkPackagelist = apkInfoExtractor.getAllInstalledApkInfo();

                    mainActivityViewModel.insertList(apkPackagelist).observe(MainActivity.this,
                            new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                           // Log.d("waste","Inserted");
                        }
                    });
                }
            }
        });

    }

    private void createDialog(String message){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivityForResult(intent, ACCESSIBILITY_REQUEST);
                    }
                }).setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INSERT_ACTIVITY_REQUEST) {
            mainActivityViewModel.getAllUserList().observe(MainActivity.this,
                    new Observer<List<UserInformationData>>() {
                        @Override
                        public void onChanged(@Nullable List<UserInformationData> list) {
                            // Log.d("waste","On Result: "+list.size());
                        }
                    });
        }else if (requestCode == ACCESSIBILITY_REQUEST) {

        }
    }
}
