package com.floriankleewein.dominion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage extends AppCompatActivity {
    private Button buttonConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);

        buttonConnection = (Button) findViewById(R.id.buttonConnection);
        buttonConnection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openMainActivity();

            }
        });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
