package com.example.my_calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView tv1, tv2;
    private MaterialButton button_c, button_backspace, button_percentage, button_divide;
    private MaterialButton button7, button8, button9, button_multiplication;
    private MaterialButton button4, button5, button6, button_subtraction;
    private MaterialButton button1, button2, button3, button_addition;
    private MaterialButton button_off, button0, button_dot, button_equal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String percentage = "%";
        final String division = "÷";
        final String multiplication = "×";
        final String subtraction = "–";
        final String addition = "+";
        final String dot = ".";

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        button_c = findViewById(R.id.button_c);
        button_backspace = findViewById(R.id.button_backspace);
        button_percentage = findViewById(R.id.button_percentage);
        button_divide = findViewById(R.id.button_divide);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button_multiplication = findViewById(R.id.button_multiplication);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button_subtraction = findViewById(R.id.button_subtraction);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button_addition = findViewById(R.id.button_addition);
        button_off = findViewById(R.id.button_off);
        button0 = findViewById(R.id.button0);
        button_dot = findViewById(R.id.button_dot);
        button_equal = findViewById(R.id.button_equal);

        button_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("");
                tv2.setText("");
                tv1.setTextSize(64);
            }
        });

        button_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (!(charSequence.isEmpty())) {
                    tv1.setText(charSequence.subSequence(0, charSequence.length() - 1));
                }
                if (charSequence.length() > 8) {
                    tv1.setTextSize(64);
                }
            }
        });

        button_percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (!charSequence.isEmpty() && !"+–×÷%".contains(charSequence.substring(charSequence.length() - 1))) {
                    tv1.append(createSpannable(percentage));
                }
            }
        });

        button_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (!charSequence.isEmpty() && !"+–×÷%".contains(charSequence.substring(charSequence.length() - 1))) {
                    tv1.append(createSpannable(division));
                }
            }
        });

        button_multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (!charSequence.isEmpty() && !"+–×÷%".contains(charSequence.substring(charSequence.length() - 1))) {
                    tv1.append(createSpannable(multiplication));
                }
            }
        });

        button_subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();
                if (!charSequence.isEmpty() && !("+–×÷%".contains(charSequence.substring(charSequence.length() - 1)))) {
                    tv1.append(createSpannable(subtraction));
                }
            }
        });

        button_addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();
                if (!charSequence.isEmpty() && !("+–×÷%".contains(charSequence.substring(charSequence.length() - 1)))) {
                    tv1.append(createSpannable(addition));
                }
            }
        });

        button_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                System.exit(0);
            }
        });

        button_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (!(charSequence.isEmpty()) && !("+–×÷%.".contains(charSequence.substring(charSequence.length() - 1)))) {
                    tv1.append(dot);
                } else if ((charSequence.isEmpty()) || ("+–×÷%".contains(charSequence.substring(charSequence.length() - 1)))) {
                    tv1.append("0" + dot);
                }
            }
        });

        tv1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                String charSequence = c.toString();

                charSequence = charSequence.replaceAll("÷", "/");
                charSequence = charSequence.replaceAll("×", "*");
                charSequence = charSequence.replaceAll("–", "-");

                if (charSequence.endsWith("+") || charSequence.endsWith("-") || charSequence.endsWith("/") || charSequence.endsWith("*") || charSequence.endsWith("%")) {
                    tv2.setText("");
                    return;
                }

                if (charSequence.contains("+") || charSequence.contains("-") || charSequence.contains("/") || charSequence.contains("*") || charSequence.contains("%")) {
                    String[] elements = charSequence.split("(?=[+\\-*/%])|(?<=[+\\-*/%])");

                    try {
                        BigDecimal result = new BigDecimal(elements[0].trim());

                        for (int j = 1; j < elements.length; j++) {
                            String element = elements[j].trim();

                            if (element.startsWith("+")) {
                                result = result.add(new BigDecimal(elements[j + 1].trim()));
                            } else if (element.startsWith("-")) {
                                result = result.subtract(new BigDecimal(elements[j + 1].trim()));
                            } else if (element.startsWith("/")) {
                                result = result.divide(new BigDecimal(elements[j + 1].trim()), MathContext.DECIMAL64);
                            } else if (element.startsWith("*")) {
                                result = result.multiply(new BigDecimal(elements[j + 1].trim()));
                            } else if (element.startsWith("%")) {
                                result = result.divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
                                result = result.multiply(new BigDecimal(elements[j + 1].trim()), MathContext.DECIMAL64);
                            }
                        }
                        typeCasting(result);
                    } catch (NumberFormatException | ArithmeticException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence2 = tv2.getText().toString();

                if (!charSequence2.isEmpty()) {
                    tv1.setText(createSpannable(charSequence2));
                    tv2.setText("");
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("1");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("1");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("1");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("2");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("2");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("2");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("3");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("3");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("3");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("4");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("4");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("4");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("5");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("5");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("5");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("6");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("6");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("6");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("7");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("7");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("7");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("8");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("8");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("8");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("9");
                } else if (charSequence.length() >= 15) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("9");
                } else if (!(charSequence.charAt(0) == '0') || ((charSequence.charAt(0) == '0') && (charSequence.contains(dot)))) {
                    tv1.append("9");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String charSequence = tv1.getText().toString();

                if (charSequence.isEmpty()) {
                    tv1.setText("0");
                } else if (charSequence.length() >= 15) {
                    Toast.makeText(getApplicationContext(), "Can't enter more than 15 digits", Toast.LENGTH_SHORT).show();
                } else if (charSequence.length() >= 8) {
                    tv1.setTextSize(36);
                    tv1.append("0");
                }
                else if (!(charSequence.charAt(0) == '0') && (!"+–×÷%".contains(charSequence.substring(charSequence.length() - 1))) || (charSequence.charAt(0) == '0' && charSequence.contains(dot))  ) {
                    tv1.append("0");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "First digit cannot be 0 unless it's a decimal", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    private void typeCasting(BigDecimal result) {
        BigDecimal integerPart = result.setScale(0, RoundingMode.DOWN);

        if (result.compareTo(integerPart) == 0) {
            tv2.setText(result.toBigInteger().toString());
        }
        else {
            tv2.setText(result.stripTrailingZeros().toPlainString());
        }
    }

    private SpannableString createSpannable(String input) {
        SpannableString spannable = new SpannableString(input);

        if ("+–×÷%".contains(input)) {
            spannable.setSpan(
                    new ForegroundColorSpan(Color.rgb(96, 141, 74)),
                            0, input.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        return spannable;
    }
}