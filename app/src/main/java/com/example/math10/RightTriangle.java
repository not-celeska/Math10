package com.example.math10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class RightTriangle extends AppCompatActivity {

    // == DATA SHOWN =====
    TextView info;

    // == ANGLES =====================
    EditText fieldAngleA, fieldAngleB;
    EditText[] angleFields = new EditText[2];
    int ANGLE_A = 0; int ANGLE_B = 1;
    double angles[] = {0, 0, 90}; // Angles [a, b, c]
    int anglesInputted;


    // == SIDE LENGTHS =========================
    EditText fieldSideA, fieldSideB, fieldSideC;
    int SIDE_A = 0; int SIDE_B = 1; int SIDE_C = 2;
    EditText[] sideFields = new EditText[3];
    double[] sides = new double[3];
    int sidesInputted;

    // == RESULTS ===========
//    TextView resultSideA = findViewById(R.id.resultSideA);
//    TextView resultSideB = findViewById(R.id.resultSideC);
//    TextView resultSideC = findViewById(R.id.resultSideB);
//    TextView resultAngleA = findViewById(R.id.resultAngleA);
//    TextView resultAngleB = findViewById(R.id.resultAngleB);
    View resultsLayout; // = getLayoutInflater().inflate(R.layout.right_triangle_results, null);

    TextView resultSideA; // = resultsLayout.findViewById(R.id.resultSideA);
    TextView resultSideB;// = resultsLayout.findViewById(R.id.resultSideB);
    TextView resultSideC; // = resultsLayout.findViewById(R.id.resultSideC);
    TextView resultAngleA;// = resultsLayout.findViewById(R.id.resultAngleA);
    TextView resultAngleB; // = resultsLayout.findViewById(R.id.resultAngleB);
    TextView resultPerimeter;
    TextView resultArea;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_triangle);

        // Info feedback text area.
        info = findViewById(R.id.inputtedData);

        // Initialize Angle Fields.
        fieldAngleA = findViewById(R.id.angleA);
        fieldAngleB = findViewById(R.id.angleB);
        angleFields[ANGLE_A] = fieldAngleA;
        angleFields[ANGLE_B] = fieldAngleB;

        // Initialize Side Fields.
        fieldSideA = findViewById(R.id.sideA);
        fieldSideB = findViewById(R.id.sideB);
        fieldSideC = findViewById(R.id.sideC);
        sideFields[SIDE_A] = fieldSideA;
        sideFields[SIDE_B] = fieldSideB;
        sideFields[SIDE_C] = fieldSideC;

        // Result Text..
//        resultsLayout = getLayoutInflater().inflate(R.layout.right_triangle_results, null);
//        resultSideA = resultsLayout.findViewById(R.id.resultSideA);
//        resultSideB = resultsLayout.findViewById(R.id.resultSideB);
//        resultSideC = resultsLayout.findViewById(R.id.resultSideC);
//        resultAngleA = resultsLayout.findViewById(R.id.resultAngleA);
//        resultAngleB = resultsLayout.findViewById(R.id.resultAngleB);


    }

    public void submitData(View view) {

        // == REGISTER [LOG] INPUT ======
        registerInput();

        // == CHECK IF NOTHING WAS ENTERED =====
        if (!(Arrays.equals(sides, new double[] {0.0, 0.0, 0.0}) && Arrays.equals(angles, new double[] {0.0, 0.0, 90.0}))) {
            // == CHECK IF INVALID INPUT =====
            if (isValidInput()) {
                switch (howToCalculate()) {
                    case 1:
                        solveBySides();
                        break;
                    case 2:
                        solveBySideAngle();
                        break;
                    case -1:
                        info.setText("NOT ENOUGH DATA");
                }

                // display results in info TextView
                info.setText("ANGLES [A, B, C]: " + Arrays.toString(angles) + "\nSIDES [a, b, c]: " + Arrays.toString(sides));

                // switch to the results layout

                openResults(view);

            }
        } else {
            info.setText("YOU DID NOT ENTER ANYTHING!");
        }
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

    public boolean isValidInput() {

        // == ANGLES =============================================
        if (angles[ANGLE_A] >= 90.0 || angles[ANGLE_B] >= 90.0)
        {
            info.setText("ANGLES OTHER THAN C CANNOT BE 90 DEGREES!");
            return false;
        }
        else if (anglesInputted == 2 && angles[ANGLE_A] + angles[ANGLE_B] != 90)
        {
            info.setText("THE SUM OF ANGLES A AND B HAS TO BE EQUAL TO 90!");
            return false;
        }

        // == SIDES =====
        if (sides[SIDE_C] != 0.0) // Side C has been entered.
        {
            // Side C is the greatest.
            for (int side = 0; side < (sides.length - 1); side++)
            {
                if (sides[side] >= sides[SIDE_C])
                {
                    info.setText("SIDE C MUST BE THE LONGEST!");
                    return false;
                }
            }

            // Pythagorean Theorem..
            if (sides[SIDE_A] != 0.0 && sides[SIDE_B] != 0)
            {
                if ((Math.pow(sides[SIDE_A], 2) + Math.pow(sides[SIDE_B], 2)) != Math.pow(sides[SIDE_C], 2))
                {
                    info.setText("THOSE SIDES DONT SEEM TO BE ADDING UP...");
                    return false;
                }
            }

            // TODO check sin cos tan validity? (prob not)

        }

        return true;
    }

    public int howToCalculate() { // 1. 2 side way | 2. 1 side 1 angle | -1. cant do anything
        if (sidesInputted >= 2)
        {
            return 1;
        }
        else if ((sidesInputted >= 1) && (anglesInputted >= 1))
        {
            return 2;
        }
        else
        {
            return -1;
        }
    }

    public void solveBySides() {
        if (variableKnown('a') && variableKnown('b'))
        {
            sides[SIDE_C] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_A], 2) + (Math.pow(sides[SIDE_B], 2)))));
            angles[ANGLE_A] = roundTwoPlaces(Math.toDegrees(Math.atan(sides[SIDE_A] / sides[SIDE_B])));
            angles[ANGLE_B] = 90 - angles[ANGLE_A];
        }
        else if (variableKnown('a') && variableKnown('c'))
        {
            sides[SIDE_B] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_C], 2) - (Math.pow(sides[SIDE_A], 2)))));
            angles[ANGLE_A] = roundTwoPlaces(Math.toDegrees(Math.asin(sides[SIDE_A] / sides[SIDE_C])));
            angles[ANGLE_B] = 90 - angles[ANGLE_A];
        }
        else // b and c are known
        {
            sides[SIDE_A] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_C], 2) - (Math.pow(sides[SIDE_B], 2)))));
            angles[ANGLE_B] = roundTwoPlaces(Math.toDegrees(Math.asin(sides[SIDE_B] / sides[SIDE_C])));
            angles[ANGLE_A] = 90 - angles[ANGLE_B];
        }
    }

    public void solveBySideAngle() {
        if (variableKnown('A')) {
            angles[ANGLE_B] = 90 - angles[ANGLE_A];

            if (variableKnown('a')) {
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_A] / Math.tan(Math.toRadians(angles[ANGLE_A]))));
                sides[SIDE_C] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_A], 2) + (Math.pow(sides[SIDE_B], 2)))));
            } else if (variableKnown('b')) {
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_B] * Math.tan(Math.toRadians(angles[ANGLE_A]))));
                sides[SIDE_C] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_A], 2) + (Math.pow(sides[SIDE_B], 2)))));
            } else { // angle 'a' and side 'c' combo
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_A]))));
                sides[SIDE_B] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_C], 2) - (Math.pow(sides[SIDE_A], 2)))));
            }
        }
        else // angle B known
        {
            angles[ANGLE_A] = 90 - angles[ANGLE_B];

            if (variableKnown('a'))
            {
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_A] * Math.tan(Math.toRadians(angles[ANGLE_B]))));
                sides[SIDE_C] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_A], 2) + (Math.pow(sides[SIDE_B], 2)))));
            }
            else if (variableKnown('b'))
            {
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_B] / Math.tan(Math.toRadians(angles[ANGLE_B]))));
                sides[SIDE_C] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_A], 2) + (Math.pow(sides[SIDE_B], 2)))));
            }
            else // angle b and side c combo
            {
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_B]))));
                sides[SIDE_A] = roundTwoPlaces(Math.sqrt((Math.pow(sides[SIDE_C], 2) - (Math.pow(sides[SIDE_B], 2)))));
            }
        }
    }

    public boolean variableKnown(char variable)
    {
        switch (variable)
        {
            // SIDE LENGTHS
            case 'a':
                if (sides[SIDE_A] != 0.0)
                {
                    return true;
                }
                break;
            case 'b':
                if (sides[SIDE_B] != 0.0)
                {
                    return true;
                }
                break;
            case 'c':
                if (sides[SIDE_C] != 0.0)
                {
                    return true;
                }
                break;

            // ANGLES
            case 'A':
                if (angles[ANGLE_A] != 0.0)
                {
                    return true;
                }
                break;
            case 'B':
                if (angles[ANGLE_B] != 0.0)
                {
                    return true;
                }
                break;
        }
        return false;
    }

    public double roundTwoPlaces(double number) {
        return (Math.round(100 * number) / 100.0);
    }

    public void openResults(View view) {
        resultsLayout = getLayoutInflater().inflate(R.layout.right_triangle_results, null);

        resultSideA = resultsLayout.findViewById(R.id.resultSideA);
        resultSideB = resultsLayout.findViewById(R.id.resultSideB);
        resultSideC = resultsLayout.findViewById(R.id.resultSideC);
        resultAngleA = resultsLayout.findViewById(R.id.resultAngleA);
        resultAngleB = resultsLayout.findViewById(R.id.resultAngleB);
        resultPerimeter = resultsLayout.findViewById(R.id.perimeterResult);
        resultArea = resultsLayout.findViewById(R.id.areaResult);

        // Use the TextViews as needed
        resultSideA.setText(String.valueOf(sides[SIDE_A]));
        resultSideB.setText(String.valueOf(sides[SIDE_B]));
        resultSideC.setText(String.valueOf(sides[SIDE_C]));
        resultAngleA.setText(String.valueOf(angles[ANGLE_A]));
        resultAngleB.setText(String.valueOf(angles[ANGLE_B]));
        resultPerimeter.setText("P = " + roundTwoPlaces(sides[SIDE_A] + sides[SIDE_B] + sides[SIDE_C]) + " units");
        resultArea.setText("A = " + roundTwoPlaces((sides[SIDE_A] * sides[SIDE_B]) / 2.0) + " units^2");

        // ... and so on

        setContentView(resultsLayout);
    }

    public void closeResults(View view)
    {
        setContentView(R.layout.activity_right_triangle);

        // Info feedback text area.
        info = findViewById(R.id.inputtedData);

        // Initialize Angle Fields.
        fieldAngleA = findViewById(R.id.angleA);
        fieldAngleB = findViewById(R.id.angleB);
        angleFields[ANGLE_A] = fieldAngleA;
        angleFields[ANGLE_B] = fieldAngleB;

        // Initialize Side Fields.
        fieldSideA = findViewById(R.id.sideA);
        fieldSideB = findViewById(R.id.sideB);
        fieldSideC = findViewById(R.id.sideC);
        sideFields[SIDE_A] = fieldSideA;
        sideFields[SIDE_B] = fieldSideB;
        sideFields[SIDE_C] = fieldSideC;
    }

    public void closeActivity(View view)
    {
        finish();
    }
}