package com.example.calculatorapps;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView txtAngka;
    Button btnAc, btnDelete;
    Button btnPlus, btnMinus, btnDivide, btnMultiple, btnPercentage, btnTotal;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;

    String txtValue = "0";
    String calculatedString = "";
    Boolean isCalculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAngka = findViewById(R.id.txt_angka);


        // Set Up Button

        assignIdOperation(btnAc, R.id.btn_reset);
        assignIdOperation(btnDelete, R.id.btn_erase);
        assignIdOperation(btnPlus, R.id.btn_plus);
        assignIdOperation(btnMinus, R.id.btn_minus);
        assignIdOperation(btnDivide, R.id.btn_divide);
        assignIdOperation(btnMultiple, R.id.btn_multiple);
        assignIdOperation(btnPercentage, R.id.btn_percentage);
        assignIdOperation(btnTotal, R.id.btn_total);
        assignIdNumb(btn1, R.id.btn_1);
        assignIdNumb(btn2, R.id.btn_2);
        assignIdNumb(btn3, R.id.btn_3);
        assignIdNumb(btn4, R.id.btn_4);
        assignIdNumb(btn5, R.id.btn_5);
        assignIdNumb(btn6, R.id.btn_6);
        assignIdNumb(btn7, R.id.btn_7);
        assignIdNumb(btn8, R.id.btn_8);
        assignIdNumb(btn9, R.id.btn_9);
        assignIdNumb(btn0, R.id.btn_0);
        txtAngka.setText(txtValue);


        // Change Listener Txt View
        txtAngka.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String stringLength = txtAngka.getText().toString();
                int intLength = stringLength.length();
                if (intLength == 6){
                    txtAngka.setTextSize(80);
                } else if (intLength == 8) {
                    txtAngka.setTextSize(60);
                } else if (intLength == 10) {
                    txtAngka.setTextSize(40);
                } else if (intLength >= 16) {
                    txtAngka.setTextSize(20);
                }
            }
        });





    }

    void assignIdNumb(Button btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    //On Click Number
    @Override
    public void onClick(View view) {
        Button numbButton = (Button) view;
        String dataToCalculate = txtAngka.getText().toString();
        String buttonText = numbButton.getText().toString();

        if (txtValue == "0") {
            txtAngka.setText("");
            dataToCalculate = buttonText;
            txtValue = dataToCalculate;

        } else if (txtValue != "0" && isCalculated == true) {
            txtAngka.setText("");
            isCalculated = false;
            dataToCalculate = buttonText;
            txtValue = dataToCalculate;

        } else if (txtValue != "0"){
            dataToCalculate = txtAngka.getText().toString();
            System.out.println(dataToCalculate);
            dataToCalculate = dataToCalculate + buttonText;
            txtValue = dataToCalculate;
        }
        //Toast.makeText(MainActivity.this, dataToCalculate, Toast.LENGTH_SHORT).show();
        txtAngka.setText(dataToCalculate);
    }



    void  assignIdOperation(Button btnOp, int id){
        btnOp = findViewById(id);
        btnOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.btn_total) {
                    calculatedString = calculatedString + txtValue;
                    txtValue = "0";
                    getResult(calculatedString);

                } else if (v.getId() == R.id.btn_erase) {
                    deletePressed();
                } else if (v.getId() == R.id.btn_reset) {
                    if (calculatedString.isEmpty()) {
                        txtValue = "0";
                        txtAngka.setText(txtValue);
                    } else {
                        txtValue = "0";
                        txtAngka.setText(txtValue);
                        calculatedString = "";
                    }
                } else if (v.getId() == R.id.btn_plus || v.getId() == R.id.btn_minus ||
                        v.getId() == R.id.btn_multiple || v.getId() == R.id.btn_divide ||
                        v.getId() == R.id.btn_percentage ) {
                    Button operation1 = (Button) v;
                    String buttonText = operation1.getText().toString();
                    calculatedString = txtValue + buttonText;
                    txtValue = "0";
                    txtAngka.setText(txtValue);
                    operationPressed(buttonText);
                }
            }
        });
    }




    public  void operationPressed(String buttonTextVal){
    }

    public void deletePressed(){
        String txt = txtAngka.getText().toString();
        StringBuilder txtCleared = new StringBuilder(txt);
        txtCleared.deleteCharAt(txt.length()-1);
        String txtFinalCleared = txtCleared.toString();
        txtAngka.setText(txtFinalCleared);
    }

    public  void getResult(String data){
        String total = "";
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript", 1, null).toString();
            total = finalResult;
            txtValue = total.replace(".0", "");
            calculatedString = "";
            txtAngka.setText(txtValue);
            isCalculated = true;
        } catch (Exception e) {
            System.out.println("err");
        }
    }
}