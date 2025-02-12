package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Inputdata extends AppCompatActivity {


    EditText edamount;
    EditText edreason;
    Button button;
    TextView tvtitle;
    Databasehelper dbhelper;
    public static boolean EXPENSE=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputdata);

        edamount = findViewById(R.id.amount);
        edreason = findViewById(R.id.reason);
        button = findViewById(R.id.button);
        tvtitle = findViewById(R.id.tvtitle);

        dbhelper = new Databasehelper(Inputdata.this);

        if (EXPENSE) tvtitle.setText("Add Expense");
        else tvtitle.setText("Add Income");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String samount = edamount.getText().toString().trim();
                String reason = edreason.getText().toString().trim();



                if (samount.isEmpty()) {
                    edamount.setError("Please enter an amount");
                    return;
                }


                double amnt = Double.parseDouble(samount);

                if (EXPENSE) {
                    dbhelper.addexpense(amnt, reason);
                    Toast.makeText(Inputdata.this, "Expense Added Successfully", Toast.LENGTH_SHORT).show();


                } else {
                    dbhelper.addincome(amnt, reason);
                    Toast.makeText(Inputdata.this, "Income Added Successfully", Toast.LENGTH_SHORT).show();
                }



                edamount.setText("");
                edreason.setText("");

                startActivity(new Intent(Inputdata.this,MainActivity.class));
            }
        });




    }
}