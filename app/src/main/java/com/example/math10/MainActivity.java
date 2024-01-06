package com.example.math10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToTrigonometryScreen(View view) {
        Intent intent = new Intent(this, TrigonometryScreen.class);
        startActivity(intent);
    }

    public void goToAnalyticalGeometryScreen(View view) {
//        Intent intent = new Intent(this, AnalyticalGeometryScreen.class);
//        startActivity(intent);
    }

    public void goToQuadraticsScreen(View view) {
//        Intent intent = new Intent(this, QuadraticsScreen.class);
//        startActivity(intent);
    }

}