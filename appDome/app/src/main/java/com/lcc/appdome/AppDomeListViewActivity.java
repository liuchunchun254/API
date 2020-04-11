package com.lcc.appdome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppDomeListViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_dome_list_view);
        ListView listView=findViewById(R.id.Data_List_View);
        APIMessage apiMessage= (APIMessage) getIntent().getSerializableExtra("resultDatas");
        List<ResultData> resultDatas=apiMessage.getResult();
        listView.setAdapter(new AppListAdapter(resultDatas));
        listView.setOnItemClickListener(new IntemClickListener(resultDatas));
    }

    //单个item的点击监听器
    public class IntemClickListener implements AdapterView.OnItemClickListener{

        List<ResultData> appName;
        public IntemClickListener(List<ResultData> appName) {
            this.appName=appName;
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle=new Bundle();
            bundle.putString("eachData",appName.get(position).toString());
            Intent intent=new Intent(AppDomeListViewActivity.this,ShowDataActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }



    public  class AppListAdapter extends BaseAdapter {
        List<ResultData> appName;
        public AppListAdapter(List<ResultData> appName) {

            this.appName = appName;
        }

        @Override
        public int getCount() {
            return appName.size();
        }

        @Override
        public Object getItem(int position) {
            return appName.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.activity_item_list_view, null);
                MyImageView itemImageView = convertView.findViewById(R.id.item_imageView);
//            TextView itemTextView=convertView.findViewById(R.id.item_TextView);
                TextView itemId = convertView.findViewById(R.id.LinearLayout_itemId);
                TextView itemText = convertView.findViewById(R.id.LinearLayout_itemText);
                ResultData eachData = appName.get(position);
                //如果getImage为空则设置默认背景
                if (eachData.getImages() == null) {
                    itemImageView.setBackgroundResource(R.drawable.ic_launcher_background);
                } else {
                    itemImageView.setImageURL(eachData.getImages());
                }
//            itemTextView.setText(appName.get(position).toString());

                itemId.setText(appName.get(position).getSid());
                itemText.setText(appName.get(position).getText());
                return convertView;
        }

        public Bitmap getHttpBitmap(String url) {
            URL myFileURL;
            Bitmap bitmap = null;
            try {
                if(url==null)
                    return null;
                else {
                    myFileURL = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
                    conn.setConnectTimeout(6000);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
