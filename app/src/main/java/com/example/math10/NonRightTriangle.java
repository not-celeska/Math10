package com.example.math10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class NonRightTriangle extends AppCompatActivity {

    TextView infon;

    EditText fieldSideA, fieldSideB, fieldSideC;
    EditText[] sideFields = new EditText[3];
    int SIDE_A = 0; int SIDE_B = 1; int SIDE_C = 2;
    double[] sides = new double[3];
    int sidesInputted;

    EditText fieldAngleA, fieldAngleB, fieldAngleC;
    EditText[] angleFields = new EditText[3];
    int ANGLE_A = 0; int ANGLE_B = 1; int ANGLE_C = 2;
    double[] angles = new double[3];
    int anglesInputted;

    View resultsLayout; // = getLayoutInflater().inflate(R.layout.right_triangle_results, null);

    TextView resultSideA; // = resultsLayout.findViewById(R.id.resultSideA);
    TextView resultSideB;// = resultsLayout.findViewById(R.id.resultSideB);
    TextView resultSideC; // = resultsLayout.findViewById(R.id.resultSideC);
    TextView resultAngleA;// = resultsLayout.findViewById(R.id.resultAngleA);
    TextView resultAngleB; // = resultsLayout.findViewById(R.id.resultAngleB);
    TextView resultAngleC;
    TextView resultPerimeter;
    TextView resultArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_right_triangle);

        infon = findViewById(R.id.infon);

        // Initialize Angle Fields.
        fieldAngleA = findViewById(R.id.angleAn);
        fieldAngleB = findViewById(R.id.angleBn);
        fieldAngleC = findViewById(R.id.angleCn);
        angleFields[ANGLE_A] = fieldAngleA;
        angleFields[ANGLE_B] = fieldAngleB;
        angleFields[ANGLE_C] = fieldAngleC;

        // Initialize Side Fields.
        fieldSideA = findViewById(R.id.sideAn);
        fieldSideB = findViewById(R.id.sideBn);
        fieldSideC = findViewById(R.id.sideCn);
        sideFields[SIDE_A] = fieldSideA;
        sideFields[SIDE_B] = fieldSideB;
        sideFields[SIDE_C] = fieldSideC;
    }

    public void submit(View view) {
        registerInput();

        if (!(Arrays.equals(sides, new double[] {0.0, 0.0, 0.0}) && Arrays.equals(angles, new double[] {0.0, 0.0, 0.0}))) {

            if (isValidInput()) {
                infon.append("\nThats valid input!\n");

                switch (howToCalculate()) {

                    case 1:
                        infon.append("Using 3 sides..\n");
                        solveBySides();
                        infon.append(Arrays.toString(angles));
                        break;
                    case 2:
                        infon.append("Using 2 sides, 1 angle...\n");
                        solveByTwoSidesOneAngle();
                        infon.append(Arrays.toString(angles) + "\n");
                        infon.append(Arrays.toString(sides));

                        break;
                    case 3:
                        infon.append("Using 1 side 2 angles...\n");
                        solveByOneSideTwoAngles();
                        infon.append(Arrays.toString(angles) + "\n");
                        infon.append(Arrays.toString(sides));
                        break;
                    case -1:
                        infon.append("Not enough data..");
                        break;

                }

                // display results in info TextView
                infon.setText("ANGLES [A, B, C]: " + Arrays.toString(angles) + "\nSIDES [a, b, c]: " + Arrays.toString(sides));

                // switch to the results layout

                openResults(view);

            }
        }
        else {
            infon.setText("YOU DID NOT ENTER ANYTHING!");
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
        if (anglesInputted == 3 && angles[ANGLE_A] + angles[ANGLE_B] + angles[ANGLE_C] != 180)
        {
            infon.setText("THE SUM OF ANGLES HAS TO BE EQUAL TO 180!");
            return false;
        }

        return true;
    }

    public int howToCalculate() { // 1. 3 side way | 2. 2 side 1 angle | 3. 1 side 2 angles | -1. cant do anything
        if (sidesInputted >= 3)
        {
            return 1;
        }
        else if ((sidesInputted >= 2) && (anglesInputted >= 1))
        {
            return 2;
        }
        else if ((sidesInputted >= 1) && (anglesInputted >= 2)) {
            return 3;
        }
        else
        {
            return -1;
        }
    }

    public void solveBySides() {
        angles[ANGLE_C] = roundTwoPlaces(Math.toDegrees(Math.acos((Math.pow(sides[SIDE_A], 2) + Math.pow(sides[SIDE_B], 2) - Math.pow(sides[SIDE_C], 2)) / (2 * sides[SIDE_A] * sides[SIDE_B]))));
        angles[ANGLE_B] = roundTwoPlaces(Math.toDegrees(Math.acos((Math.pow(sides[SIDE_A], 2) + Math.pow(sides[SIDE_C], 2) - Math.pow(sides[SIDE_B], 2)) / (2 * sides[SIDE_A] * sides[SIDE_C]))));
        angles[ANGLE_A] = roundTwoPlaces(180 - angles[ANGLE_C] - angles[ANGLE_B]);
    }

    public void solveByOneSideTwoAngles() {
        if (variableKnown('a')) {
            if (variableKnown('A') && variableKnown('B')) {
                angles[ANGLE_C] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_B]);
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_A] * Math.sin(Math.toRadians(angles[ANGLE_B]))) / Math.sin(Math.toRadians(angles[ANGLE_A])));
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_A] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_A])));
            } else if (variableKnown('B') && variableKnown('C')) {
                angles[ANGLE_A] = roundTwoPlaces(180 - angles[ANGLE_C] - angles[ANGLE_B]);
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_A] * Math.sin(Math.toRadians(angles[ANGLE_B]))) / Math.sin(Math.toRadians(angles[ANGLE_A])));
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_A] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_A])));
            } else { // ie a and c are known
                angles[ANGLE_B] = roundTwoPlaces(180 - angles[ANGLE_C] - angles[ANGLE_A]);
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_A] * Math.sin(Math.toRadians(angles[ANGLE_B]))) / Math.sin(Math.toRadians(angles[ANGLE_A])));
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_A] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_A])));
            }
        } else if (variableKnown('b')) {
            if (variableKnown('A') && variableKnown('C')) {
                angles[ANGLE_B] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_C]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
            } else if (variableKnown('A') && variableKnown('B')) {
                angles[ANGLE_C] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_B]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
            } else { // ie b and c are known
                angles[ANGLE_A] = roundTwoPlaces(180 - angles[ANGLE_C] - angles[ANGLE_B]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
            }
        } else { // ie side c is known
            if (variableKnown('A') && variableKnown('B')) {
                angles[ANGLE_C] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_B]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_B]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
            } else if (variableKnown('A') && variableKnown('C')) {
                angles[ANGLE_B] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_C]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_B]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
            } else { // ie a and b are known
                angles[ANGLE_C] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_B]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_B]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
            }
        }
    }

    public void solveByTwoSidesOneAngle() {
        if (variableKnown('A')) {
            if (variableKnown('a') && variableKnown('b')) {
                angles[ANGLE_B] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_A])) * sides[SIDE_B]) / sides[SIDE_A])));
                angles[ANGLE_C] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_B]);
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_A] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_A])));
            } else if (variableKnown('a') && variableKnown('c')) {
                angles[ANGLE_C] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_A])) * sides[SIDE_C]) / sides[SIDE_A])));
                angles[ANGLE_B] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_C]);
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_A] * Math.sin(Math.toRadians(angles[ANGLE_B]))) / Math.sin(Math.toRadians(angles[ANGLE_A])));
            } else if (variableKnown('b') && variableKnown('c')) {
                angles[ANGLE_A] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_B])) * sides[SIDE_C]) / sides[SIDE_B])));
                angles[ANGLE_C] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_B]);
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
            }
        } else if (variableKnown('B')) {
            if (variableKnown('a') && variableKnown('b')) {
                angles[ANGLE_A] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_B])) * sides[SIDE_A]) / sides[SIDE_B])));
                angles[ANGLE_C] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_B]);
                sides[SIDE_C] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_C]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
            } else if (variableKnown('a') && variableKnown('c')) {
                angles[ANGLE_C] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_B])) * sides[SIDE_C]) / sides[SIDE_B])));
                angles[ANGLE_A] = roundTwoPlaces(180 - angles[ANGLE_B] - angles[ANGLE_C]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
            } else if (variableKnown('b') && variableKnown('c')) {
                angles[ANGLE_B] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_B])) * sides[SIDE_C]) / sides[SIDE_B])));
                angles[ANGLE_A] = roundTwoPlaces(180 - angles[ANGLE_B] - angles[ANGLE_C]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_B] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_B])));
            }
        } else { // angle C known
            if (variableKnown('a') && variableKnown('b')) {
                angles[ANGLE_A] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_C])) * sides[SIDE_A]) / sides[SIDE_C])));
                angles[ANGLE_B] = roundTwoPlaces(180 - angles[ANGLE_A] - angles[ANGLE_C]);
                sides[SIDE_B] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_B]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
            } else if (variableKnown('a') && variableKnown('c')) {
                angles[ANGLE_B] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_C])) * sides[SIDE_B]) / sides[SIDE_C])));
                angles[ANGLE_A] = roundTwoPlaces(180 - angles[ANGLE_B] - angles[ANGLE_C]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
            } else if (variableKnown('b') && variableKnown('c')) {
                angles[ANGLE_B] = roundTwoPlaces(Math.toDegrees(Math.asin((Math.sin(Math.toRadians(angles[ANGLE_C])) * sides[SIDE_B]) / sides[SIDE_C])));
                angles[ANGLE_A] = roundTwoPlaces(180 - angles[ANGLE_B] - angles[ANGLE_C]);
                sides[SIDE_A] = roundTwoPlaces((sides[SIDE_C] * Math.sin(Math.toRadians(angles[ANGLE_A]))) / Math.sin(Math.toRadians(angles[ANGLE_C])));
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
            case 'C':
                if (angles[ANGLE_C] != 0.0)
                {
                    return true;
                }
        }
        return false;
    }

    public double roundTwoPlaces(double number) {
        return (Math.round(100 * number) / 100.0);
    }


    public void openResults(View view) {
        resultsLayout = getLayoutInflater().inflate(R.layout.non_right_triangle_results, null);

        resultSideA = resultsLayout.findViewById(R.id.resultSideAn);
        resultSideB = resultsLayout.findViewById(R.id.resultSideBn);
        resultSideC = resultsLayout.findViewById(R.id.resultSideCn);
        resultAngleA = resultsLayout.findViewById(R.id.resultAngleAn);
        resultAngleB = resultsLayout.findViewById(R.id.resultAngleBn);
        resultAngleC = resultsLayout.findViewById(R.id.resultAngleCn);
        resultPerimeter = resultsLayout.findViewById(R.id.perimeterResultn);
        resultArea = resultsLayout.findViewById(R.id.areaResultn);

        // Use the TextViews as needed
        resultSideA.setText(String.valueOf(sides[SIDE_A]));
        resultSideB.setText(String.valueOf(sides[SIDE_B]));
        resultSideC.setText(String.valueOf(sides[SIDE_C]));
        resultAngleA.setText(String.valueOf(angles[ANGLE_A]));
        resultAngleB.setText(String.valueOf(angles[ANGLE_B]));
        resultAngleC.setText(String.valueOf(angles[ANGLE_C]));
        resultPerimeter.setText("P = " + roundTwoPlaces(sides[SIDE_A] + sides[SIDE_B] + sides[SIDE_C]) + " units");
        resultArea.setText("A = " + roundTwoPlaces(Math.sqrt(((sides[SIDE_A] + sides[SIDE_B] + sides[SIDE_C]) / 2) * ((sides[SIDE_A] + sides[SIDE_B] + sides[SIDE_C]) / 2 - sides[SIDE_A]) * ((sides[SIDE_A] + sides[SIDE_B] + sides[SIDE_C]) / 2 - sides[SIDE_B]) * ((sides[SIDE_A] + sides[SIDE_B] + sides[SIDE_C]) / 2 - sides[SIDE_C]))) + " units^2" );

        // ... and so on

        setContentView(resultsLayout);
    }

    public void closeResults(View view)
    {
        setContentView(R.layout.activity_non_right_triangle);

        // Info feedback text area.
        infon = findViewById(R.id.infon);

        // Initialize Angle Fields.
        fieldAngleA = findViewById(R.id.angleAn);
        fieldAngleB = findViewById(R.id.angleBn);
        fieldAngleC = findViewById(R.id.angleCn);
        angleFields[ANGLE_A] = fieldAngleA;
        angleFields[ANGLE_B] = fieldAngleB;
        angleFields[ANGLE_C] = fieldAngleC;

        // Initialize Side Fields.
        fieldSideA = findViewById(R.id.sideAn);
        fieldSideB = findViewById(R.id.sideBn);
        fieldSideC = findViewById(R.id.sideCn);
        sideFields[SIDE_A] = fieldSideA;
        sideFields[SIDE_B] = fieldSideB;
        sideFields[SIDE_C] = fieldSideC;
    }



    public void close(View view) {
        finish();
    }
}