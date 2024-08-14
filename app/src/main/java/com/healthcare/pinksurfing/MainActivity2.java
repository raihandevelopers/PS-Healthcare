package com.healthcare.pinksurfing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity2 extends AppCompatActivity {

    private EditText weightIn;
    private EditText heightIn;
    public double w;
    public double h;
    public double result;
    private Button calc;
    private TextView disp;
    private TextView ew;
    private TextView eh;
    private Button weightOption;
    private Button heightOption;
    private Button heightOption2;
    private EditText inch;
    private int b1 = 0, b2 = 0, b3 = 0;
    private Button resetButton;
    private TextView classification;
    private TextView comments;
    private TextView pres;
    private TextView cla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        classification = (TextView) findViewById(R.id.classification);

        cla = (TextView) findViewById(R.id.classtext);

        comments = (TextView) findViewById(R.id.comment);

        pres = (TextView) findViewById(R.id.prescribed);

        ew = (TextView) findViewById(R.id.errorweight);
        eh = (TextView) findViewById(R.id.errorheight);

        weightOption = (Button) findViewById(R.id.weightlist);
        heightOption = (Button) findViewById(R.id.heightlist);
        heightOption2 = (Button) findViewById(R.id.heightlist2);

        inch = (EditText) findViewById(R.id.inches);

        weightIn = (EditText) findViewById(R.id.weightin);
        heightIn = (EditText) findViewById(R.id.heightin);

        disp = (TextView) findViewById(R.id.display);

        calc = (Button) findViewById(R.id.calculate);
        resetButton = (Button) findViewById(R.id.reset);

        weightOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b1 ++;

                if ((b1 % 2) == 1){
                    weightOption.setText("lb");
                }
                else{
                    weightOption.setText("kg");
                }

            }
        });


        heightOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b2 = 1;
                b3 = 0;

                heightOption.setVisibility(View.INVISIBLE);
                inch.setVisibility(View.VISIBLE);
                heightOption2.setVisibility(View.VISIBLE);
                heightIn.setHint("feet");
                heightIn.setText("");
                inch.setText("");

            }
        });

        heightOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b3 = 1;
                b2 = 0;

                heightOption2.setVisibility(View.INVISIBLE);
                inch.setVisibility(View.INVISIBLE);
                heightOption.setVisibility(View.VISIBLE);
                heightIn.setHint("height");
                heightIn.setText("");
                inch.setText("");

            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeKeyboard();
                process(b1, b2, b3);

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weightIn.setText("");
                heightIn.setText("");
                inch.setText("");
                disp.setText("");
                ew.setText("");
                eh.setText("");
                classification.setText("");
                cla.setVisibility(View.INVISIBLE);
                comments.setText("");
                pres.setText("");

            }
        });

    }

    private void process(int a, int b, int c){

        long total;
        int num = 0;
        double h2, p;
        int pre;

        weightIn.clearFocus();
        heightIn.clearFocus();
        inch.clearFocus();

        if (weightIn.getText().toString().toString().isEmpty()){
            ew.setText("Please enter weight");
            num ++;
        }
        else{
            ew.setText("");
        }

        if (heightIn.getText().toString().toString().isEmpty() || ((b % 2) == 1 && inch.getText().toString().toString().isEmpty())){
            eh.setText("Please enter height");
            num ++;
        }
        else{
            eh.setText("");
        }

        if (num > 0) {
            disp.setText("");
            classification.setText("");
            cla.setVisibility(View.INVISIBLE);
            comments.setText("");
            pres.setText("");
            return;
        }

        w = Double.valueOf(weightIn.getText().toString());
        h = Double.valueOf(heightIn.getText().toString());

        if ((a % 2) == 1){
            w = w * 0.45;
        }

        if (b == 1 && c == 0){
            h2 = Double.valueOf(inch.getText().toString());
            h = h * 30.48;
            h = h + (h2 * 2.54);
        }

        if (h <= 0){
            eh.setText("Please enter valid height");
            disp.setText("");
            classification.setText("");
            cla.setVisibility(View.INVISIBLE);
            comments.setText("");
            pres.setText("");
            num ++;
        }

        if (w <= 0){
            ew.setText("Please enter valid weight");
            disp.setText("");
            classification.setText("");
            cla.setVisibility(View.INVISIBLE);
            comments.setText("");
            pres.setText("");
            num ++;
        }

        if (num > 0){
            return;
        }

        cla.setVisibility(View.VISIBLE);

        result = (w / Math.pow(h, 2));
        result = result * Math.pow(10, 4);

        total = Math.round(result);
        disp.setText("Your BMI is: " + total);

        if (total < 12){
            classification.setText("Severely Underweight");
            classification.setTextColor(Color.parseColor("#ff0000"));
            comments.setText("have more food and exercise everyday!");
            p = 23 * Math.pow(h, 2) / Math.pow(10, 4);
            pre = (int) Math.round(p);
            pres.setText("For your height, your weight must be around " + pre + "kg/" + Math.round(pre/0.45) + "lb.");
        }
        else if (total <=18 && total >= 12){
            classification.setText("Underweight");
            classification.setTextColor(Color.parseColor("#ffd700"));
            comments.setText("have more food and exercise everyday!");
            p = 23 * Math.pow(h, 2) / Math.pow(10, 4);
            pre = (int) Math.round(p);
            pres.setText("For your height, your weight must be around " + pre + "kg/" + Math.round(pre/0.45) + "lb.");
        }
        else if (total <= 24 && total >= 19){
            classification.setText("Healthy");
            classification.setTextColor(Color.parseColor("#24ce28"));
            comments.setText("maintain this great shape!");
            pres.setText("");
        }
        else if (total <= 29 && total >= 25){
            classification.setText("Slightly Overweight");
            classification.setTextColor(Color.parseColor("#ffd700"));
            comments.setText("workout everyday!");
            p = 23 * Math.pow(h, 2) / Math.pow(10, 4);
            pre = (int) Math.round(p);
            pres.setText("For your height, your weight must be around " + pre + "kg/" + Math.round(pre/0.45) + "lb.");
        }
        else if (total <= 39 && total >= 30){
            classification.setText("Obese");
            classification.setTextColor(Color.parseColor("#ff8c00"));
            comments.setText("time for a jog! make some diet plans");
            p = 23 * Math.pow(h, 2) / Math.pow(10, 4);
            pre = (int) Math.round(p);
            pres.setText("For your height, your weight must be around " + pre + "kg/" + Math.round(pre/0.45) + "lb.");
        }
        else if (total >= 40){
            classification.setText("Severely Overweight");
            classification.setTextColor(Color.parseColor("#ff0000"));
            comments.setText("exercise regularly and make some diet plans!");
            p = 23 * Math.pow(h, 2) / Math.pow(10, 4);
            pre = (int) Math.round(p);
            pres.setText("For your height, your weight must be around " + pre + "kg/" + Math.round(pre/0.45) + "lb.");
        }

    }

    private void closeKeyboard(){

        View view = this.getCurrentFocus();

        if (view != null){

            InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            m.hideSoftInputFromWindow(view.getWindowToken(),0);

        }

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity2.this,Primary.class));
        super.onBackPressed();
    }
}