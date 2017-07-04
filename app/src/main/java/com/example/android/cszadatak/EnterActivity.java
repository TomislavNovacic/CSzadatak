package com.example.android.cszadatak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tomi on 2.7.2017..
 */

public class EnterActivity extends AppCompatActivity {

    Button započni;
    Button nastavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);

        započni = (Button) findViewById(R.id.createNewGrid);
        nastavi = (Button) findViewById(R.id.continueWithOldGrid);

        započni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterActivity.this, EnterParamsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nastavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
