package com.ksajja.lottietest;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.airbnb.lottie.Cancellable;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.airbnb.lottie.TextDelegate;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LottieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        downloadFileAndPlay();

    }


    private void downloadFileAndPlay() {
        //curtis file
        String downloadUrl = "https://www.dropbox.com/s/0prnussoyt4ujto/data.json?dl=1";

        //text comparision - small
        //String downloadUrl = "https://www.dropbox.com/s/wzqtw15asb28vqj/android_text_comparison.json?dl=1";
        //String downloadUrl = "https://www.dropbox.com/s/z27xfq293p4j21n/text_test_android.json?dl=1";

        //sample lottie
        //String downloadUrl = "https://www.dropbox.com/s/9wffepjiyu4stsp/done.json?dl=1";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String jsonString = response.body().string();
                        final LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
                        Cancellable compositionCancellable = LottieComposition.Factory.fromJson(getResources(),
                                new JSONObject(jsonString), new OnCompositionLoadedListener() {
                                    @Override
                                    public void onCompositionLoaded(@Nullable LottieComposition composition) {
                                        try {
                                            lottieAnimationView.setComposition(composition);
                                            lottieAnimationView.setImageAssetsFolder("images/");
                                            setTextDelegate(lottieAnimationView);
                                            lottieAnimationView.playAnimation();
                                            ((TextView) findViewById(R.id.textView2)).setText("Download completed");
                                        } catch (Throwable e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTextDelegate(LottieAnimationView lottieAnimationView) {
        TextDelegate textDelegate = new TextDelegate(lottieAnimationView) {
            @Override
            public String getText(String input) {
//                String name = "John Wayne";
//                if (name.length() > 5 || name.split(" ").length > 0) {
//                    if ("name".equals(input)) {
//                        return name.split(" ")[0];
//                    }
//                    if ("name_backup".equals(input)) {
//                        return name.split(" ")[1];
//                    }
//                }

                if ("center center".equals(input)) {
                    return "ce";
                }
                return super.getText(input);
            }
        };

        lottieAnimationView.setTextDelegate(textDelegate);
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(Environment.getExternalStorageDirectory() + File.separator + "tmp.json");
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }
}
