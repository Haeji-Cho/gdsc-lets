package com.example.gdsc_lets;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.CalendarView;

import java.util.Calendar;

public class MainActivity5 extends AppCompatActivity {

    //예약자명
    EditText reserName;

    //날짜 선택
    CalendarView datePick;

    //예약자 인원수 증감
    Button btnp1, btnm1, btnp2, btnm2, btnp3, btnm3, btnp4, btnm4;
    TextView adCnt, chCnt,elCnt,diCnt;
    int adNum, chNum, elNum, diNum = 0;
    int memberCount = 0;

    //Next 버튼 & 받아온 값 확인
    ImageButton next1, next2, save;
    TextView nameResult, dateResult, memberResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        /**** 이름 설정 ****/
        reserName = (EditText)findViewById(R.id.reser_Name);
//        reserName.getText(new View.OnClickListener(){});
        nameResult = (TextView)findViewById(R.id.name_result);
        next1 = (ImageButton)findViewById(R.id.nextBtn1);

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameResult.setText(reserName.getText().toString());
            }
        });

        /**** 날짜 설정 ****/
        datePick = (CalendarView) findViewById(R.id.calendarView);
        dateResult = (TextView)findViewById(R.id.date_result);
        next2 = (ImageButton)findViewById(R.id.nextBtn2);

        datePick.setMinDate(System.currentTimeMillis());
        datePick.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dateResult.setText(month+1 + "," + dayOfMonth);
            }
        });

        /**** 인원 설정 ****/
        //adult 증감
        adCnt = (TextView)findViewById(R.id.memAdult);
        adCnt.setText(adNum+"");
        btnp1 = (Button)findViewById(R.id.p_btn1);
        btnm1 = (Button)findViewById(R.id.m_btn1);
        memberResult = (TextView)findViewById(R.id.member_result);

        btnp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adNum++;
                adCnt.setText(adNum+"");
                memberCount++;
            }
        });

        btnm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adNum--;
                adCnt.setText(adNum+"");
                memberCount--;
            }
        });

        //child 증감
        chCnt = (TextView)findViewById(R.id.memChild);
        chCnt.setText(chNum+"");
        btnp2 = (Button)findViewById(R.id.p_btn2);
        btnm2 = (Button)findViewById(R.id.m_btn2);

        btnp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chNum++;
                chCnt.setText(chNum+"");
                memberCount++;
            }
        });

        btnm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chNum--;
                chCnt.setText(chNum+"");
                memberCount--;
            }
        });


        //elderly 증감
        elCnt = (TextView)findViewById(R.id.memEldery);
        elCnt.setText(elNum+"");
        btnp3 = (Button)findViewById(R.id.p_btn3);
        btnm3 = (Button)findViewById(R.id.m_btn3);

        btnp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elNum++;
                elCnt.setText(elNum+"");
                memberCount++;
            }
        });

        btnm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elNum--;
                elCnt.setText(elNum+"");
                memberCount--;
            }
        });


        //disabled 증감
        diCnt = (TextView)findViewById(R.id.memDisabled);
        diCnt.setText(diNum+"");
        btnp4 = (Button)findViewById(R.id.p_btn4);
        btnm4 = (Button)findViewById(R.id.m_btn4);

        btnp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diNum++;
                diCnt.setText(diNum+"");
                memberCount++;
            }
        });

        btnm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diNum--;
                diCnt.setText(diNum+"");
                memberCount--;
            }
        });

        /**** 저장(수정 필요) ****/
        save = (ImageButton)findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberResult.setText("" +memberCount);
            }
        });
    }
}

