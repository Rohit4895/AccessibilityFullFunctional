package com.thinkitive.accessibilityfullfunctional.view.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkitive.accessibilityfullfunctional.R;
import com.thinkitive.accessibilityfullfunctional.Utils.ApkInfoExtractor;
import com.thinkitive.accessibilityfullfunctional.Utils.InterfaceDistributionClass;
import com.thinkitive.accessibilityfullfunctional.model.PackageNamesOnly;
import com.thinkitive.accessibilityfullfunctional.view.adapter.InsertionAdapter;
import com.thinkitive.accessibilityfullfunctional.viewModel.InsertionTaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class InsertionTask extends AppCompatActivity implements InterfaceDistributionClass.OnClickAppSelect {
    private EditText user, password;
    private TextView packageName;
    private Button insertInfo;
    private InsertionTaskViewModel insertionTaskViewModel;
    private RecyclerView recyclerView;
    private ApkInfoExtractor apkInfoExtractor;
    private String pack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertion_task);

        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        packageName = findViewById(R.id.selectedApp);
        insertInfo = findViewById(R.id.insertInfo);
        recyclerView = findViewById(R.id.recyclerForInsertion);

        apkInfoExtractor = new ApkInfoExtractor(this);
        pack="";

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final InsertionAdapter insertionAdapter = new InsertionAdapter(this, new ArrayList<String>());
        recyclerView.setAdapter(insertionAdapter);

        insertionTaskViewModel = ViewModelProviders.of(this).get(InsertionTaskViewModel.class);

        insertionTaskViewModel.getPackageNamesList().observe(InsertionTask.this,
                new Observer<List<PackageNamesOnly>>() {
            @Override
            public void onChanged(@Nullable List<PackageNamesOnly> packageNamesList) {
                List<String> adapterList= new ArrayList<>();

                for (int i=0; i<packageNamesList.size(); i++){
                    adapterList.add(String.valueOf(packageNamesList.get(i).getPackageName()));
                }

                insertionAdapter.addList(adapterList);


              /* For Spinner.....
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(InsertionTask.this,
                        android.R.layout.simple_spinner_dropdown_item, adapterList);
                packageName.setAdapter(adapter);*/
            }
        });



        insertInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = user.getText().toString();
                String pass = password.getText().toString();

                if (userId == null || pass == null || pack == null){
                   // Toast.makeText(getApplicationContext(),"All Fields are neccessary...",Toast.LENGTH_SHORT).show();
                    return;
                }

                insertionTaskViewModel.insertUser(userId,pass,pack).observe(InsertionTask.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean success) {
                        if (!success)
                            return;
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });
            }
        });

    }

    @Override
    public void getAppName(String appPackageName) {
        pack = appPackageName;
        String appName = apkInfoExtractor.getAppName(appPackageName);
        packageName.setText(appName);
    }
}
