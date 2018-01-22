package com.soullaundry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
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

import static java.security.AccessController.getContext;

public class DeliveryActivity extends Activity implements SearchView.OnQueryTextListener {


    SearchView simpleSearchView;


    static {
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.soullaundry.R.layout.delivery);

        simpleSearchView = (SearchView) findViewById(com.soullaundry.R.id.simpleSearchView);
        simpleSearchView.setOnQueryTextListener(this);



    }


    private void Toastmsg(DeliveryActivity collectionActivity, String p1)
    {

        Toast.makeText(this,p1,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        final String text = query;

        setContentView(com.soullaundry.R.layout.delivery);

        simpleSearchView = (SearchView) findViewById(com.soullaundry.R.id.simpleSearchView);
        simpleSearchView.setOnQueryTextListener(this);


        FirebaseDatabase.getInstance().getReference().child("usersG")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int totDue=0;
                        ArrayList<User> users=new ArrayList<User>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final User user = snapshot.getValue(User.class);

                            System.out.println("db" + user.ph);
                            if (user.ph.equals(text) || user.name.contains(text)) {
                                users.add(user);

                            }
                        }
                        java.util.Collections.reverse(users);
                        int l=0;
                        int tot=0;
                        for(int h=0;h<users.size();h++){
                            tot=tot+users.get(h).due;
                        }
                        for(final User user:users) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT);
                            TextView tv1 = new TextView(DeliveryActivity.this);
                            TextView tv2 = new TextView(DeliveryActivity.this);

                            TextView tv3 = new TextView(DeliveryActivity.this);

                            TextView tv4 = new TextView(DeliveryActivity.this);

                            TextView tv5 = new TextView(DeliveryActivity.this);

                            TextView tv0 = new TextView(DeliveryActivity.this);


                            String rem="";
                            if(user!=null && user.billDetailsArrayList!=null) {
                                for (int p = 0; p < user.billDetailsArrayList.size(); p++) {

                                    rem = rem + "Cloth Type  " + user.billDetailsArrayList.get(p).clothType + "\n";

                                    rem = rem + "Quantity  " + user.billDetailsArrayList.get(p).qty + "\n";

                                    rem = rem + "Price  " + user.billDetailsArrayList.get(p).price + "\n";

                                }
                            }

                            tv1.setText("Name " + user.name);
                            tv2.setText("Bill Number " + user.billNumber);
                            tv3.setText("Amount " + user.total);
                            tv4.setText("Due  " + user.due);
                            if (user.due==0)
                                tv4.setTextColor(Color.GREEN);
                            else
                                tv4.setTextColor(Color.RED);



                            totDue += user.due;
                            TableRow.LayoutParams trparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                            tv1.setLayoutParams(trparams);
                            tv2.setLayoutParams(trparams);
                            tv3.setLayoutParams(trparams);
                            tv4.setLayoutParams(trparams);
                            tv5.setLayoutParams(trparams);
                            tv0.setLayoutParams(trparams);
                            tv0.setTextColor(Color.RED);

                            tv0.setText("Total Due " + tot);
                            TableRow tr0 = new TableRow(DeliveryActivity.this);

                            TableLayout layoutINNER = new TableLayout(DeliveryActivity.this);

                            tr0.setLayoutParams(params);
                            tr0.addView(tv0);
                            if(l==0) {
                                layoutINNER.addView(tr0);
                                l++;
                            }


                            layoutINNER.setLayoutParams(params);
                            TableRow tr = new TableRow(DeliveryActivity.this);

                            tr.setLayoutParams(params);
                            tr.addView(tv1);
                            TableRow tr2 = new TableRow(DeliveryActivity.this);

                            tr2.setLayoutParams(params);
                            tr2.addView(tv2);

                            TableRow tr3 = new TableRow(DeliveryActivity.this);

                            tr3.setLayoutParams(params);
                            tr3.addView(tv3);


                            TableRow tr6 = new TableRow(DeliveryActivity.this);

                            tr6.setLayoutParams(params);
                            tr6.addView(tv4);

                            TableRow tr7 = new TableRow(DeliveryActivity.this);

                            tr7.setLayoutParams(params);
                            tr7.addView(tv5);

                            layoutINNER.addView(tr);
                            layoutINNER.addView(tr2);

                            layoutINNER.addView(tr3);


                            layoutINNER.addView(tr6);

                            layoutINNER.addView(tr7);

                            TableRow tr4 = new TableRow(DeliveryActivity.this);

                            final Button paid = new Button(DeliveryActivity.this);
                            paid.setText("Paid");


                            String[] array = {"Payment Mode", "Cash", "Paytm","Online"};



                            final Spinner paid2 = new Spinner(DeliveryActivity.this);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(DeliveryActivity.this
                                    , android.R.layout.simple_spinner_item, array); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            paid2.setAdapter(spinnerArrayAdapter);

                            paid2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parentView) {
                                    // your code here
                                }

                            });


                            paid.setTextColor(Color.BLACK);
                            paid.setGravity(Gravity.CENTER);
                           // paid.setLayoutParams(trparams);

                            paid.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {



                                    if (paid2.getSelectedItem().toString().trim().equals("Payment Mode")) {
                                        ((TextView) paid2.getChildAt(0)).setError("Please select payment mode");

                                    }
                                    else {


                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();

                                            String message = "Thank you for using the service of SoulLaundry"+"\n"+"For more details Contact"+"\n"+"9980461461";



                                            PendingIntent sentPI = PendingIntent.getBroadcast(DeliveryActivity.this, 0, new Intent("SENT_SMS_ACTION_NAME"), 0);
                                            PendingIntent deliveredPI = PendingIntent.getBroadcast(DeliveryActivity.this, 0, new Intent("DELIVERED_SMS_ACTION_NAME"), 0);


                                            SmsManager sms = SmsManager.getDefault();
                                            ArrayList<String> parts = sms.divideMessage(message);

                                            ArrayList<PendingIntent> sendList = new ArrayList<PendingIntent>();
                                            sendList.add(sentPI);

                                            ArrayList<PendingIntent> deliverList = new ArrayList<PendingIntent>();
                                            deliverList.add(deliveredPI);

                                            sms.sendMultipartTextMessage("+91" + user.ph, null, parts, sendList, deliverList);
                                            //smsManager.sendTextMessage("+91"+phone, null,message, null, null);
                                            Toast.makeText(getApplicationContext(), "SMS Sent!",
                                                    Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(),
                                                    "SMS failed, please try again later!",
                                                    Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }

                                        user.due = 0;


                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.keepSynced(true);
                                        Query query = mDatabase.child("usersG").orderByChild("billNumber");
                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                //String billNumber=null;
                                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                    Log.d("User key", child.getKey());
                                                    Log.d("User val", child.child("billNumber").getValue().toString());
                                                    String billNumber = child.child("billNumber").getValue().toString();
                                                    if (billNumber.equals(user.billNumber)) {
                                                        child.getRef().child("due").setValue(0);

                                                        child.getRef().child("discount").setValue(0);
                                                        child.getRef().child("paymentMode").setValue(paid2.getSelectedItem().toString());

                                                        setContentView(com.soullaundry.R.layout.delivery);

                                                        simpleSearchView = (SearchView) findViewById(com.soullaundry.R.id.simpleSearchView);
                                                        simpleSearchView.setOnQueryTextListener(DeliveryActivity.this);
                                                        Toastmsg(DeliveryActivity.this, "Bill Has been Updated");

                                                    }

                                                }


                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        ;


                                    }
                                }
                            });




                            tr4.setLayoutParams(params);
                            tr4.addView(paid2);
                            tr4.addView(paid);

                            TableRow tr5 = new TableRow(DeliveryActivity.this);
                            tr5.setLayoutParams(params);
                            TableRow tr18 = new TableRow(DeliveryActivity.this);

                            tr18.setLayoutParams(params);



                            layoutINNER.addView(tr4);

                            layoutINNER.addView(tr5);
                            layoutINNER.addView(tr18);

                            View line = new View(DeliveryActivity.this);
                            line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 10));
                            line.setBackgroundColor(Color.rgb(51, 51, 51));
                            layoutINNER.addView(line);
                            LinearLayout main = (LinearLayout) findViewById(com.soullaundry.R.id.main_layout);

                            main.addView(layoutINNER);

                        }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length()==10) {
            String text = newText;
        }
        return false;
    }




}
