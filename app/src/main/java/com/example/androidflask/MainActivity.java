package com.example.androidflask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView ans;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =(EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        ans = (TextView) findViewById(R.id.response);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connect();
            }
        });

    }

    private void connect()
    {
        String postUrl = "http://192.168.43.110:5000/";
        String postBodyText = "Hello";
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody postBody = RequestBody.create(mediaType,postBodyText);

        postRequest(postUrl,postBody);
    }

    void postRequest(String postUrl, RequestBody postBody)
    {
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(postUrl).post(postBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                call.cancel();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ans.setText("Android and server not connected");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            ans.setText(response.body().string());
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
