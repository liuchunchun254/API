package com.lcc.appdome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    APIMessage apiMessage=new APIMessage();
    private Button button;
    private TextView textView;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.Data_Button);
        textView=findViewById(R.id.Data_TextView);
        button.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                   getURLData();

                }
            }).start();

        }
        public String streamToString(InputStream inputStream) throws IOException {
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            byte[] buffer=new byte[2048];
            int len;
            while ((len=inputStream.read(buffer))!=-1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            inputStream.close();
            byte[] array=outputStream.toByteArray();
            return new String(array);
        }

        public void getURLData(){
            try {

                URL url=new URL("https://api.apiopen.top/getJoke?page=1&count=2&type=video");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(30*1000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("Charset","UTF-8");
                connection.setRequestProperty("Accept-Charset","UTF-8");
                connection.connect();
                int code=connection.getResponseCode();
                String message=connection.getResponseMessage();
                if(code== HttpsURLConnection.HTTP_OK){
                    InputStream inputStream=connection.getInputStream();
                    data=streamToString(inputStream);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(data);
                            try {
                                 handleJSONData(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void handleJSONData(String Data) throws JSONException {
//            APIMessage apiMessage=new APIMessage();
            ArrayList<ResultData> resultDataArrayList=new ArrayList<>();
            JSONObject jsonObject=new JSONObject(Data);
            apiMessage.setCode(jsonObject.getInt("code"));
            apiMessage.setMessage(jsonObject.getString("message"));
            JSONArray dataArray=jsonObject.getJSONArray("result");
            if(dataArray!=null&&dataArray.length()>0){
                for(int index=0;index<dataArray.length();index++){
                    JSONObject jsonResultData=dataArray.getJSONObject(index);
                    ResultData resultData=new ResultData();
                    resultData.setComment(jsonResultData.getString("comment"));
                    resultData.setDown(jsonResultData.getString("down"));
                    resultData.setForward(jsonResultData.getString("forward"));
                    resultData.setHeader(jsonResultData.getString("header"));
                    resultData.setImages(jsonResultData.getString("images"));
                    resultData.setName(jsonResultData.getString("name"));
                    resultData.setPasstime(jsonResultData.getString("passtime"));
                    resultData.setSid(jsonResultData.getString("sid"));
                    resultData.setText(jsonResultData.getString("text"));
                    resultData.setThumbnail(jsonResultData.getString("thumbnail"));
                    resultData.setTop_comments_content(jsonResultData.getString("top_comments_content"));
                    resultData.setTop_comments_voiceuri(jsonResultData.getString("top_comments_voiceuri"));
                    resultData.setTop_comments_header(jsonResultData.getString("top_comments_uid"));
                    resultData.setTop_comments_name(jsonResultData.getString("top_comments_name"));
                    resultData.setTop_comments_header(jsonResultData.getString("top_comments_header"));
                    resultData.setUid(jsonResultData.getString("uid"));
                    resultData.setUp(jsonResultData.getString("up"));
                    resultData.setVideo(jsonResultData.getString("video"));
                    resultData.setType(jsonResultData.getString("type"));
                    System.out.println("11111111111111111111111111111111");
                    System.out.println("22222222222222222222222222222");
                    resultDataArrayList.add(resultData);
                }
                apiMessage.setResult(resultDataArrayList);
            }
            for(int i=0;i<apiMessage.getResult().size();i++){
                System.out.println(apiMessage.getResult().get(i).toString());
            }
            Intent intent=new Intent(MainActivity.this, AppDomeListViewActivity.class);
            intent.putExtra("resultDatas",apiMessage);
            startActivity(intent);
        }
    }


}
