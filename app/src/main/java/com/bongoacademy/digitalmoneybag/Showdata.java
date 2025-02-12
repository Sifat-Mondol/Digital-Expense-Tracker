package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Showdata extends AppCompatActivity {

    ListView listview;
    TextView tv;
    ArrayList<HashMap<String,String>>arrayList;
   HashMap<String,String>hashMap;

    Databasehelper dbhelper;

    public static  boolean EXPENSE=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);

        tv=findViewById(R.id.topicname);
        listview=findViewById(R.id.listview);
        dbhelper=new Databasehelper(this);



        if (EXPENSE==true)tv.setText("Showing all Expenses");
        else tv.setText("Showing all Incomes");


loaddata();


    }

//===================================================================//



public void loaddata(){

//.............................................................
        Cursor cursor=null;
if (EXPENSE==true) cursor=dbhelper.showallexpensedata();
else cursor= dbhelper.showallincomedata();

//...................................................



    if (cursor!=null&&cursor.getCount()>0){

        arrayList=new ArrayList<>();

        while (cursor.moveToNext()){

            int id= cursor.getInt(0);
            double amount= cursor.getDouble(1);
            String reason= cursor.getString(2);

            HashMap hashMap=new HashMap<>();
            hashMap.put("id",""+id);
            hashMap.put("amount",""+amount);
            hashMap.put("reason",""+reason);
            arrayList.add(hashMap);

        }
        //.....ekhane adapter call korte hbe.........//
        myadapter adapter = new myadapter();
        listview.setAdapter(adapter);



    }else {



        tv.append("No Data found");
    }

}




    //==================================================================================//
    //......................adapter creation........//

    public class myadapter extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater=getLayoutInflater();
          View myview=  layoutInflater.inflate(R.layout.item,parent,false);


            TextView ID=myview.findViewById(R.id.id);
            TextView expense=myview.findViewById(R.id.expense);
          TextView reason=myview.findViewById(R.id.reason);
          TextView delete=myview.findViewById(R.id.delete);




          hashMap=arrayList.get(position);

          String id=hashMap.get("id");
          String AMOUNT=hashMap.get("amount");
            String REASON=hashMap.get("reason");

            ID.setText(""+id);
            reason.setText("Reason: "+REASON);
          expense.setText("BDT: "+AMOUNT);



          delete.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              if (EXPENSE==true) dbhelper.deletebyidforexpense(id);
                else dbhelper.deletebyidforincome(id);

                  loaddata();


              }
          });




            return myview;
        }
    }
}