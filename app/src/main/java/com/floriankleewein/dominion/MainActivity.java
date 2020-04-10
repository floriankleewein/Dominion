package com.floriankleewein.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_connect = findViewById(R.id.btn_con);
        btn_connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btn_con:
                connect();
                break;
        }
    }

    protected void connect() {
        ClientConnector temp = new ClientConnector();
        try {
            Log.d("PRECON", "Trying to connect.");
            temp.connect();
            Log.d("POSTCON", "Connection successfull!");
        } catch (IOException e) {
            Log.d("EXCEPTION", "IOException");
            e.printStackTrace();
        }
    }
}
