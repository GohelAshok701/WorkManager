package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private WorkManager mWorkManager;
    private OneTimeWorkRequest mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWorkManager = WorkManager.getInstance(this);
        Button btnStartWorkManagerService = findViewById(R.id.btnStartWorkManagerService);

        btnStartWorkManagerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                mWorkManager.enqueue(mRequest);
            }
        });
    }

    private void init(){
        mRequest = new OneTimeWorkRequest.Builder(WorkManagerService.class).build();
        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(
                this,
                new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Toast.makeText(MainActivity.this, ""+workInfo.getState(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}