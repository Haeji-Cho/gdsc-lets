package com.example.gdsc_lets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Initial2Activity extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "userUpdate";

    Button btnGrp, btnInd;
    ImageButton submit;
    Spinner loc, favSpo;

    String location, favSport, nickname;
    int role, group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial2);

        // 개인 & 단체 설정
        btnGrp = (Button) findViewById(R.id.btnGrp);
        btnInd = (Button) findViewById(R.id.btnInd);

        btnGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group = 1;
            }
        });

        btnGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group = 0;
            }
        });


        // 지역, 운동 선택
        loc = (Spinner) findViewById(R.id.loc);
        favSpo = (Spinner) findViewById(R.id.favSpo);

        loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = (String) loc.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        favSpo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                favSport = (String) favSpo.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //initial1 값 받아오기
        Intent intent = getIntent();
        nickname = intent.getStringExtra("nickname");
        role = intent.getIntExtra("role", 0);

        //submit
        submit = (ImageButton) findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home3Activity.class);
                startActivity(intent);
                //test용 (수정 필요)
                String user_id = "12@daum.net";
                String roleF = "0";
                String groupF = "0";
                String nickname = "jeong";
                String location = "노원구";
                String favSport = "gym";

                userUpdate updateTask = new userUpdate();
                updateTask.execute("http://" + IP_ADDRESS + "/userUpdate.php",
                        user_id, nickname, location, favSport, roleF, groupF);
            }
        });
    }

        //Db 연동
        class userUpdate extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Initial2Activity.this,
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
                String nickname = (String) params[2];
                String address = (String) params[3];
                String favSpo = (String) params[4];
                String role = (String) params[5];
                String group = (String) params[6];

                //PHP 파일을 실행시킬 수 있는 주소와 전송할 데이터 준비
                String serverURL = (String) params[0];

                String postParameters = "userId=" + userId + "&nickname=" + nickname
                        + "&address=" + address + "&favSpo=" + favSpo
                        + "&role=" + role + "&group=" + group;


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