package com.example.gdsc_lets;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ScrollView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Reserve3Activity extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "reserInsert";

    //자동 스크롤 관련
    ScrollView rScrollView;
    TextView dateExp, memExp;
    ImageButton next1, next2;

    EditText reserName;     //예약자명

    //날짜 선택
    CalendarView datePick;
    int year, month, day;

    //예약자 인원수
    Button btnp1, btnm1, btnp2, btnm2, btnp3, btnm3, btnp4, btnm4, save;
    TextView adCnt, chCnt, elCnt, diCnt;
    int adNum, chNum, elNum, diNum = 0;
    int memberCount = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve3);
        rScrollView = (ScrollView) findViewById(R.id.rScrollView);
        dateExp = (TextView) findViewById(R.id.dateExp);

        /** 이름 설정 **/
        reserName = (EditText) findViewById(R.id.reserName);
        next1 = (ImageButton) findViewById(R.id.next1);

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rScrollView.scrollTo(0, dateExp.getTop());
            }
        });

        /** 날짜 설정 **/
        datePick = (CalendarView) findViewById(R.id.datePick);
        memExp = (TextView) findViewById(R.id.memExp);
        next2 = (ImageButton) findViewById(R.id.next2);
        datePick.setMinDate(System.currentTimeMillis());

        datePick.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int y, int m, int d) {
                year = y;
                month = m + 1;
                day = d;
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rScrollView.scrollTo(0, memExp.getTop());
            }
        });

        /** 인원 설정 **/
        //adult 증감
        adCnt = (TextView) findViewById(R.id.adCnt);
        adCnt.setText(adNum + "");
        btnp1 = (Button) findViewById(R.id.btnp1);
        btnm1 = (Button) findViewById(R.id.btnm1);
        btnp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adNum++;
                adCnt.setText(adNum + "");
                memberCount++;
            }
        });
        btnm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adNum--;
                adCnt.setText(adNum + "");
                memberCount--;
            }
        });

        //child 증감
        chCnt = (TextView) findViewById(R.id.chCnt);
        chCnt.setText(chNum + "");
        btnp2 = (Button) findViewById(R.id.btnp2);
        btnm2 = (Button) findViewById(R.id.btnm2);
        btnp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chNum++;
                chCnt.setText(chNum + "");
                memberCount++;
            }
        });
        btnm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chNum--;
                chCnt.setText(chNum + "");
                memberCount--;
            }
        });

        //elderly 증감
        elCnt = (TextView) findViewById(R.id.elCnt);
        elCnt.setText(elNum + "");
        btnp3 = (Button) findViewById(R.id.btnp3);
        btnm3 = (Button) findViewById(R.id.btnm3);
        btnp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elNum++;
                elCnt.setText(elNum + "");
                memberCount++;
            }
        });
        btnm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elNum--;
                elCnt.setText(elNum + "");
                memberCount--;
            }
        });

        //disabled 증감
        diCnt = (TextView) findViewById(R.id.diCnt);
        diCnt.setText(diNum + "");
        btnp4 = (Button) findViewById(R.id.btnp4);
        btnm4 = (Button) findViewById(R.id.btnm4);
        btnp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diNum++;
                diCnt.setText(diNum + "");
                memberCount++;
            }
        });
        btnm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diNum--;
                diCnt.setText(diNum + "");
                memberCount--;
            }
        });

        /** 저장 (유저 아이디 가져오는 기능 & 시간 설정 기능 수정 필요) **/
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = "lets123";
                String reser_name = reserName.getText().toString();
                String member = String.valueOf(memberCount);
                String reser_date = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
                String start_time = "1000";
                String end_time = "1030";

                //String start_time = String.valueOf(StartHour) + String.valueOf(StartMin) + "00";
                //String end_time = String.valueOf(endHour) + String.valueOf(endMin) + "00";

                reserInsert task = new reserInsert();
                task.execute("http://" + IP_ADDRESS + "/reserInsert.php",
                        user_id, reser_name, member, reser_date, start_time, end_time);
            }
        });
    }


    //Db 연동
    class reserInsert extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Reserve3Activity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String userId = (String) params[1];
            String reserName = (String) params[2];
            String member = (String) params[3];
            String reserDate = (String) params[4];
            String startTime = (String) params[5];
            String endTime = (String) params[6];

            //PHP 파일을 실행시킬 수 있는 주소와 전송할 데이터 준비
            String serverURL = (String) params[0];

            String postParameters = "userId=" + userId + "&reserName=" + reserName
                    + "&member=" + member + "&reserDate=" + reserDate
                    + "&startTime=" + startTime + "&endTime=" + endTime;


            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}