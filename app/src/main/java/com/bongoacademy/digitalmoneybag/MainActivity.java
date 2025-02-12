package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.adservices.common.AdData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView Totalexpense,addexpense;
    TextView totalincome,addincome;
    TextView presentbalance;
    TextView showexpensedata;
    TextView showincomedata;
    Databasehelper dbhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Totalexpense = findViewById(R.id.Totalexpense);
        showexpensedata = findViewById(R.id.showexpensedata);
        addexpense = findViewById(R.id.addexpense);
        addincome = findViewById(R.id.addincome);
        totalincome = findViewById(R.id.totalincome);
        presentbalance=findViewById(R.id.presentbalance);
        showincomedata=findViewById(R.id.showincomedata);

        dbhelper = new Databasehelper(MainActivity.this);



        showexpensedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Showdata.EXPENSE=true;

                startActivity(new Intent(MainActivity.this,Showdata.class));
            }
        });



        showincomedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Showdata.EXPENSE=false;

                startActivity(new Intent(MainActivity.this,Showdata.class));


            }
        });




        addexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inputdata.EXPENSE = true;
                startActivity(new Intent(MainActivity.this, Inputdata.class));


            }
        });

        addincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inputdata.EXPENSE = false;
                startActivity(new Intent(MainActivity.this, Inputdata.class));
            }
        });

        updateui();

    }

//=========================================================================================//




    public void updateui() {
        double totalExp = dbhelper.totalexpense();
        double totalInc = dbhelper.totalincome();

        Totalexpense.setText("BDT " + totalExp);
        totalincome.setText("BDT " + totalInc);


        double mainbalence=totalInc-totalExp;
        presentbalance.setText("BDT "+mainbalence);

    }



    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateui();

    }




}