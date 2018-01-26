package com.soullaundry;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by kshravi on 24/01/2018 AD.
 */


public class Unpaid extends Fragment {


    public Unpaid() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        final View rootView= inflater.inflate(R.layout.fragment_unpaid, container, false);






        FirebaseDatabase.getInstance().getReference().child("usersG")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        LinearLayout main = (LinearLayout) rootView.findViewById(com.soullaundry.R.id.main_layout);
                        main.removeAllViews();



                        TableLayout layoutINNER = new TableLayout(getContext());

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT);


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
                            TextView tv1 = new TextView(getContext());
                            TextView tv2 = new TextView(getContext());

                            TextView tv3 = new TextView(getContext());

                            TextView tv4 = new TextView(getContext());

                            TextView tv5 = new TextView(getContext());

                            TextView tv0 = new TextView(getContext());




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
                            TableRow tr = new TableRow(getContext());

                            tr.setLayoutParams(params);
                            tr.addView(tv1);
                            TableRow tr2 = new TableRow(getContext());

                            tr2.setLayoutParams(params);
                            tr2.addView(tv2);

                            TableRow tr3 = new TableRow(getContext());

                            tr3.setLayoutParams(params);
                            tr3.addView(tv3);


                            TableRow tr6 = new TableRow(getContext());

                            tr6.setLayoutParams(params);
                            tr6.addView(tv4);

                            TableRow tr7 = new TableRow(getContext());

                            tr7.setLayoutParams(params);
                            tr7.addView(tv5);

                            layoutINNER.addView(tr);
                            layoutINNER.addView(tr2);

                            layoutINNER.addView(tr3);


                            layoutINNER.addView(tr6);

                            layoutINNER.addView(tr7);



                            final Button paid4 = new Button(getContext());
                            paid4.setText("Payment"+"\n"+ "Reminder");
                            paid4.setTextColor(Color.BLACK);
                            paid4.setGravity(Gravity.CENTER);


                            TableRow tr99 = new TableRow(getContext());

                            tr99.setLayoutParams(params);
                            tr99.addView(paid4);
                            // paid.setLayoutParams(trparams);

                            paid4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {






                                    try {
                                        SmsManager smsManager = SmsManager.getDefault();

                                        String message = "Payment for your laundry Order with Soul Laundromat with"+ "\n"+"Bill Number "+user.billNumber+"\n"+"Amount-"+user.total+" is pending"+"\n"+"kindly pay it immediatly"+"\n"+"From Soul Landromat "+"\n"+"For any queries please contact  "+"9980461461"+"\n"+"9008181686";;




                                        PendingIntent sentPI = PendingIntent.getBroadcast(getContext(), 0, new Intent("SENT_SMS_ACTION_NAME"), 0);
                                        PendingIntent deliveredPI = PendingIntent.getBroadcast(getContext(), 0, new Intent("DELIVERED_SMS_ACTION_NAME"), 0);


                                        SmsManager sms = SmsManager.getDefault();
                                        ArrayList<String> parts = sms.divideMessage(message);

                                        ArrayList<PendingIntent> sendList = new ArrayList<PendingIntent>();
                                        sendList.add(sentPI);

                                        ArrayList<PendingIntent> deliverList = new ArrayList<PendingIntent>();
                                        deliverList.add(deliveredPI);

                                        sms.sendMultipartTextMessage("+91" + user.ph, null, parts, sendList, deliverList);
                                        //smsManager.sendTextMessage("+91"+phone, null,message, null, null);
                                        Toast.makeText(getContext(), "SMS Sent!",
                                                Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(getContext(),
                                                "SMS failed, please try again later!",
                                                Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }



                                    ;



                                }
                            });

                            layoutINNER.addView(tr99);


                            View line = new View(getContext());
                            line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 10));
                            line.setBackgroundColor(Color.rgb(51, 51, 51));
                            layoutINNER.addView(line);

                            // TEXTVIEW
                            if(main.getParent()!=null)
                                ((ViewGroup)main.getParent()).removeView(main);

                            main.addView(layoutINNER);

                        }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return rootView;
    }


}

