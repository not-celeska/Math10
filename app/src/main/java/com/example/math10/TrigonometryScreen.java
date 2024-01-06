package com.example.math10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TrigonometryScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigonometry_screen);
    }

    public void goToRightTriangleScreen(View view) {
        Intent intent = new Intent(this, RightTriangle.class);
        startActivity(intent);
    }

    public void goToNonRightTriangleScreen(View view) {
        Intent intent = new Intent(this, NonRightTriangle.class);
        startActivity(intent);
    }

    public void closeMenu(View view) {
        finish();
    }
}