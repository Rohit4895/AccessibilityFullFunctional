package com.example.accessibilityfullfunctional.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.accessibilityfullfunctional.R;
import com.example.accessibilityfullfunctional.service.Utils.ApkInfoExtractor;
import com.example.accessibilityfullfunctional.service.model.PackageNamesOnly;
import com.example.accessibilityfullfunctional.service.model.UserInformationData;
import com.example.accessibilityfullfunctional.view.adapter.PackageInfoAdapter;
import com.example.accessibilityfullfunctional.viewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private String packName;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,InsertionTask.class);
                startActivityForResult(intent,1);
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
                Log.d("waste","On Create: "+list.size());
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
                            Log.d("waste","Inserted");
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mainActivityViewModel.getAllUserList().observe(MainActivity.this,
                new Observer<List<UserInformationData>>() {
            @Override
            public void onChanged(@Nullable List<UserInformationData> list) {
                Log.d("waste","On Result: "+list.size());
            }
        });
    }
}
