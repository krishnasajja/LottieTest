package com.ksajja.lottietest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //downloadFileAndPlay();
                Intent intent = new Intent(MainActivity.this, LottieActivity.class);
                startActivity(intent);
            }
        });
    }

    private void downloadFileAndPlay() {
        String downloadUrl = "https://www.dropbox.com/s/0prnussoyt4ujto/data.json?dl=1";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        //String jsonString = response.body().string();
                        FileOutputStream fOut = new FileOutputStream(getFile());
                        OutputStreamWriter osw = new OutputStreamWriter(fOut);
                        // Write the string to the file
                        osw.write(response.body().string());

       /* ensure that everything is
        * really written out and close */
                        fOut.flush();
                        fOut.close();
                        osw.flush();
                        osw.close();

                        Intent intent = new Intent(MainActivity.this, LottieActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private File getFile() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "tmp.json");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


}
