package com.soullaundry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopExpenseActivity extends AppCompatActivity  {



    Spinner paid2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.soullaundry.R.layout.shop);
paid2=(Spinner)findViewById(R.id.bills);




        paid2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(paid2.getSelectedItem().toString().equals("Show Unpaid Bills")){



                    FirebaseDatabase.getInstance().getReference().child("usersG")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                   // setContentView(R.layout.shop);
                                  //  paid2=(Spinner)findViewById(R.id.bills);
                                   // paid2.setSelection(2);


                                    View namebar = View.findViewById(R.id.main_layout);
                                    ((ViewGroup) namebar.getParent()).removeView(namebar);


                                    LinearLayout main = (LinearLayout) findViewById(com.soullaundry.R.id.main_layout);

                                    TableLayout layoutINNER = new TableLayout(ShopExpenseActivity.this);
                                    int totDue=0;
                                    ArrayList<User> users=new ArrayList<User>();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        final User user = snapshot.getValue(User.class);
                                        if(user.due!=0)

                                            users.add(user);


                                    }
                                    java.util.Collections.reverse(users);
                                    int l=0;

                                    for(final User user:users) {
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
                                                TableLayout.LayoutParams.WRAP_CONTENT);
                                        TextView tv1 = new TextView(ShopExpenseActivity.this);
                                        TextView tv2 = new TextView(ShopExpenseActivity.this);

                                        TextView tv3 = new TextView(ShopExpenseActivity.this);

                                        TextView tv4 = new TextView(ShopExpenseActivity.this);

                                        TextView tv5 = new TextView(ShopExpenseActivity.this);

                                        TextView tv0 = new TextView(ShopExpenseActivity.this);




                                        tv1.setText("Name " + user.name);
                                        tv2.setText("Bill Number " + user.billNumber);
                                        tv3.setText("Amount " + user.total);
                                        tv4.setText("Due  " + user.due);
                                        tv5.setText("PickUpDate "+user.pickUpDate+"\n"+"DeliveryDate "+user.deliveryDate+"\n"+"Payment Mode  -"+user.paymentMode);
                                        if (user.due==0)
                                            tv4.setTextColor(Color.GREEN);
                                        else
                                            tv4.setTextColor(Color.RED);


                                        TableRow.LayoutParams trparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                                        tv1.setLayoutParams(trparams);
                                        tv2.setLayoutParams(trparams);
                                        tv3.setLayoutParams(trparams);
                                        tv4.setLayoutParams(trparams);
                                        tv5.setLayoutParams(trparams);
                                        tv0.setLayoutParams(trparams);







                                        layoutINNER.setLayoutParams(params);
                                        TableRow tr = new TableRow(ShopExpenseActivity.this);

                                        tr.setLayoutParams(params);
                                        tr.addView(tv1);
                                        TableRow tr2 = new TableRow(ShopExpenseActivity.this);

                                        tr2.setLayoutParams(params);
                                        tr2.addView(tv2);

                                        TableRow tr3 = new TableRow(ShopExpenseActivity.this);

                                        tr3.setLayoutParams(params);
                                        tr3.addView(tv3);


                                        TableRow tr6 = new TableRow(ShopExpenseActivity.this);

                                        tr6.setLayoutParams(params);
                                        tr6.addView(tv4);

                                        TableRow tr7 = new TableRow(ShopExpenseActivity.this);

                                        tr7.setLayoutParams(params);
                                        tr7.addView(tv5);

                                        layoutINNER.addView(tr);
                                        layoutINNER.addView(tr2);

                                        layoutINNER.addView(tr3);


                                        layoutINNER.addView(tr6);

                                        layoutINNER.addView(tr7);


                                        View line = new View(ShopExpenseActivity.this);
                                        line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 10));
                                        line.setBackgroundColor(Color.rgb(51, 51, 51));

                                        layoutINNER.addView(line);

                                        main.addView(layoutINNER);

                                    }




                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


                }
                else if(paid2.getSelectedItem().toString().equals("Show Paid Bills")){







                    FirebaseDatabase.getInstance().getReference().child("usersG")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                   // setContentView(R.layout.shop);
                                   // paid2.setSelection(1);


                                    View namebar = View.findViewById(R.id.main_layout);
                                    ((ViewGroup) namebar.getParent()).removeView(namebar);

                                    LinearLayout main = (LinearLayout) findViewById(com.soullaundry.R.id.main_layout);

                                    TableLayout layoutINNER = new TableLayout(ShopExpenseActivity.this);
                                    int totDue=0;
                                    ArrayList<User> users=new ArrayList<User>();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        final User user = snapshot.getValue(User.class);
                                        if(user.due==0)

                                        users.add(user);


                                    }
                                    java.util.Collections.reverse(users);
                                    int l=0;

                                    for(final User user:users) {
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
                                                TableLayout.LayoutParams.WRAP_CONTENT);
                                        TextView tv1 = new TextView(ShopExpenseActivity.this);
                                        TextView tv2 = new TextView(ShopExpenseActivity.this);

                                        TextView tv3 = new TextView(ShopExpenseActivity.this);

                                        TextView tv4 = new TextView(ShopExpenseActivity.this);

                                        TextView tv5 = new TextView(ShopExpenseActivity.this);

                                        TextView tv0 = new TextView(ShopExpenseActivity.this);




                                        tv1.setText("Name " + user.name);
                                        tv2.setText("Bill Number " + user.billNumber);
                                        tv3.setText("Amount " + user.total);
                                        tv4.setText("Due  " + user.due);
                                        tv5.setText("PickUpDate "+user.pickUpDate+"\n"+"DeliveryDate "+user.deliveryDate+"\n"+"Payment Mode  -"+user.paymentMode);
                                        if (user.due==0)
                                            tv4.setTextColor(Color.GREEN);
                                        else
                                            tv4.setTextColor(Color.RED);


                                        TableRow.LayoutParams trparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                                        tv1.setLayoutParams(trparams);
                                        tv2.setLayoutParams(trparams);
                                        tv3.setLayoutParams(trparams);
                                        tv4.setLayoutParams(trparams);
                                        tv5.setLayoutParams(trparams);
                                        tv0.setLayoutParams(trparams);







                                        layoutINNER.setLayoutParams(params);
                                        TableRow tr = new TableRow(ShopExpenseActivity.this);

                                        tr.setLayoutParams(params);
                                        tr.addView(tv1);
                                        TableRow tr2 = new TableRow(ShopExpenseActivity.this);

                                        tr2.setLayoutParams(params);
                                        tr2.addView(tv2);

                                        TableRow tr3 = new TableRow(ShopExpenseActivity.this);

                                        tr3.setLayoutParams(params);
                                        tr3.addView(tv3);


                                        TableRow tr6 = new TableRow(ShopExpenseActivity.this);

                                        tr6.setLayoutParams(params);
                                        tr6.addView(tv4);

                                        TableRow tr7 = new TableRow(ShopExpenseActivity.this);

                                        tr7.setLayoutParams(params);
                                        tr7.addView(tv5);

                                        layoutINNER.addView(tr);
                                        layoutINNER.addView(tr2);

                                        layoutINNER.addView(tr3);


                                        layoutINNER.addView(tr6);

                                        layoutINNER.addView(tr7);


                                        View line = new View(ShopExpenseActivity.this);
                                        line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 10));
                                        line.setBackgroundColor(Color.rgb(51, 51, 51));
                                        layoutINNER.addView(line);

                                        main.addView(layoutINNER);

                                    }




                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                }
                else if(paid2.getSelectedItem().toString().equals("Show all Bills")){




                    FirebaseDatabase.getInstance().getReference().child("usersG")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                   // setContentView(R.layout.shop);
                                   // paid2.setSelection(3);


                                    View namebar = View.findViewById(R.id.main_layout);
                                    ((ViewGroup) namebar.getParent()).removeView(namebar);

                                    LinearLayout main = (LinearLayout) findViewById(com.soullaundry.R.id.main_layout);

                                    TableLayout layoutINNER = new TableLayout(ShopExpenseActivity.this);
                                    int totDue=0;
                                    ArrayList<User> users=new ArrayList<User>();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        final User user = snapshot.getValue(User.class);

                                        users.add(user);


                                    }
                                    java.util.Collections.reverse(users);
                                    int l=0;

                                    for(final User user:users) {
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
                                                TableLayout.LayoutParams.WRAP_CONTENT);
                                        TextView tv1 = new TextView(ShopExpenseActivity.this);
                                        TextView tv2 = new TextView(ShopExpenseActivity.this);

                                        TextView tv3 = new TextView(ShopExpenseActivity.this);

                                        TextView tv4 = new TextView(ShopExpenseActivity.this);

                                        TextView tv5 = new TextView(ShopExpenseActivity.this);

                                        TextView tv0 = new TextView(ShopExpenseActivity.this);




                                        tv1.setText("Name " + user.name);
                                        tv2.setText("Bill Number " + user.billNumber);
                                        tv3.setText("Amount " + user.total);
                                        tv4.setText("Due  " + user.due);
                                        tv5.setText("PickUpDate "+user.pickUpDate+"\n"+"DeliveryDate "+user.deliveryDate+"\n"+"Payment Mode  -"+user.paymentMode);
                                        if (user.due==0)
                                            tv4.setTextColor(Color.GREEN);
                                        else
                                            tv4.setTextColor(Color.RED);


                                        TableRow.LayoutParams trparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                                        tv1.setLayoutParams(trparams);
                                        tv2.setLayoutParams(trparams);
                                        tv3.setLayoutParams(trparams);
                                        tv4.setLayoutParams(trparams);
                                        tv5.setLayoutParams(trparams);
                                        tv0.setLayoutParams(trparams);







                                        layoutINNER.setLayoutParams(params);
                                        TableRow tr = new TableRow(ShopExpenseActivity.this);

                                        tr.setLayoutParams(params);
                                        tr.addView(tv1);
                                        TableRow tr2 = new TableRow(ShopExpenseActivity.this);

                                        tr2.setLayoutParams(params);
                                        tr2.addView(tv2);

                                        TableRow tr3 = new TableRow(ShopExpenseActivity.this);

                                        tr3.setLayoutParams(params);
                                        tr3.addView(tv3);


                                        TableRow tr6 = new TableRow(ShopExpenseActivity.this);

                                        tr6.setLayoutParams(params);
                                        tr6.addView(tv4);

                                        TableRow tr7 = new TableRow(ShopExpenseActivity.this);

                                        tr7.setLayoutParams(params);
                                        tr7.addView(tv5);

                                        layoutINNER.addView(tr);
                                        layoutINNER.addView(tr2);

                                        layoutINNER.addView(tr3);


                                        layoutINNER.addView(tr6);

                                        layoutINNER.addView(tr7);


                                        View line = new View(ShopExpenseActivity.this);
                                        line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 10));
                                        line.setBackgroundColor(Color.rgb(51, 51, 51));
                                        layoutINNER.addView(line);

                                        main.addView(layoutINNER);

                                    }




                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                }
                else
                {



                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



    }









    private void Toastmsg(DeliveryActivity collectionActivity, String p1)
    {

        Toast.makeText(this,p1,
                Toast.LENGTH_SHORT).show();
    }




    public void onBackPressed(){
        Intent i=new Intent(ShopExpenseActivity.this,SampleActivity.class);
        startActivity(i);
        setContentView(R.layout.activity_sample);

    }


}
