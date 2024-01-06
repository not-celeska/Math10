package com.example.math10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class NonRightTriangle extends AppCompatActivity {

    TextView info;

    EditText fieldSideA, fieldSideB, fieldSideC;
    int SIDE_A = 0; int SIDE_B = 1; int SIDE_C = 2;
    EditText[] sideFields = new EditText[3];
    double[] sides = new double[3];
    int sidesInputted;

    EditText fieldAngleA, fieldAngleB, fieldAngleC;
    EditText[] angleFields = new EditText[2];
    int ANGLE_A = 0; int ANGLE_B = 1; int ANGLE_C  = 2;
    double[] angles = new double[3]; // Angles [a, b, c]
    int anglesInputted;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_right_triangle);

//        // Initialize Angle Fields.
//        fieldAngleA = findViewById(R.id.angleAn);
//        fieldAngleB = findViewById(R.id.angleBn);
//        fieldAngleC = findViewById(R.id.angleCn);
//        angleFields[ANGLE_A] = fieldAngleA;
//        angleFields[ANGLE_B] = fieldAngleB;
//        angleFields[ANGLE_C] = fieldAngleC;
//
//        // Initialize Side Fields.
//        fieldSideA = findViewById(R.id.sideAn);
//        fieldSideB = findViewById(R.id.sideBn);
//        fieldSideC = findViewById(R.id.sideCn);
//        sideFields[SIDE_A] = fieldSideA;
//        sideFields[SIDE_B] = fieldSideB;
//        sideFields[SIDE_C] = fieldSideC;
//
//        info = findViewById(R.id.infon);
//        info.setText("Info will go here..");
//
    }

    public void sumbitData(View view) {

        info.setText("Data submitted..\n");
        registerInput();
        info.append(Arrays.toString(angles));
        info.append("\n" + Arrays.toString(sides));


    }

    public void registerInput() {

        anglesInputted = 0;
        sidesInputted = 0;

        // == REGISTER ANGLES =====
        for (int angleField = 0; angleField < angleFields.length; angleField++) {

            String angle = angleFields[angleField].getText().toString().trim();

            if (!angle.isEmpty()) {

                angles[angleField] = Double.parseDouble(angle);
//                angleFields[angleField].getText().clear();
                anglesInputted++;
            }
            else // if there was nothing entered.
            {
                angles[angleField] = 0.0;
            }
        }

        // == REGISTER SIDES =====
        for (int sideField = 0; sideField < sideFields.length; sideField++) {

            String side = sideFields[sideField].getText().toString().trim();

            if (!side.isEmpty()) {
                sides[sideField] = Double.parseDouble(side);
//                sideFields[sideField].getText().clear();
                sidesInputted++;
            }
            else // if there was nothing entered.
            {
                sides[sideField] = 0.0;
            }
        }
    }



    public void close(View view) {
        finish();
    }

}