package com.lcc.appdome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowDataActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        Bundle bundle=getIntent().getExtras();
        String resultData=bundle.getString("eachData");
        textView=findViewById(R.id.showData);
        textView.setText(resultData);
    }
}
