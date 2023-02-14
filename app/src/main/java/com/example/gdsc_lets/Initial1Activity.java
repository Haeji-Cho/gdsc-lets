package com.example.gdsc_lets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Initial1Activity extends AppCompatActivity {

    Button btnLic, btnExe;
    ImageButton nextBtn;
    EditText userName;

    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial1);

        userName = (EditText) findViewById(R.id.nickname);
        btnLic = (Button) findViewById(R.id.btnLic);
        btnExe = (Button) findViewById(R.id.btnExe);

        //License & Exerciser 설정
        btnLic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 1;
            }
        });

        btnExe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 0;
            }
        });

        Intent intent = new Intent(Initial1Activity.this, Initial2Activity.class);
        intent.putExtra("nickname", userName.getText().toString());
        intent.putExtra("role", role);

        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

}

