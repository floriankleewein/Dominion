package com.floriankleewein.dominion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCon = findViewById(R.id.btn_con);
        //Server Start
        KryoServer kserver = new KryoServer();
        kserver.startServer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ClientConnector temp = new ClientConnector();
                try {
                    temp.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
