package com.soullaundry;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kshravi on 24/01/2018 AD.
 */
public class CustomTabActivity extends AppCompatActivity implements OnDataPass {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments

    CallsFragment chatFragment;
    CallsFragment callsFragment;
    CallsFragment contactsFragment,OnlyIron;

    String[] tabTitle={"Dry Cleaning","Wash/Fold","Wash/Iron"};
    int[] unreadCount={0,5,0};


    String message;
    String message1="";
    String message2="";
    String message3="";
    String name,phone,billNumber;
    String pickDate,DelDate;

    String time;
    final String[] clothType = {""};

    final String[] svc = {""};

    final String[] qtySpinner = {""};

    final String[] delivery = {""};

    final String[] priceSpinner = {""};

    int total=0;

    ArrayList<BillDetails> billDetailsArrayList= new ArrayList<BillDetails>();
    public Button submitaall;


    private void Toastmsg(CustomTabActivity collectionActivity, String p1)
    {

        Toast.makeText(this,p1,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_without_icon);
        submitaall=(Button)findViewById(R.id.btn_submit_all);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        try
        {
            setupTabIcons();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        submitaall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                if(billDetailsArrayList.size()==0){


                    Toastmsg(CustomTabActivity.this,"You have not entered any bill to submit");
                }



                else {


                        final Dialog dialog = new Dialog(CustomTabActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(com.soullaundry.R.layout.layout);

                        TableLayout stk = (TableLayout) dialog.findViewById(com.soullaundry.R.id.table_main);
                        TableRow tbrow99 = new TableRow(CustomTabActivity.this);
                        TextView tv99 = new TextView(CustomTabActivity.this);

                    String billNumberI = getIntent().getStringExtra("billNumber");
                        tv99.setText("Bill Number - " + billNumberI);
                        tv99.setGravity(Gravity.CENTER);
                        tbrow99.addView(tv99);
                        stk.addView(tbrow99);

                        TableRow tbrow0 = new TableRow(CustomTabActivity.this);
                        TextView tv0 = new TextView(CustomTabActivity.this);
                        tv0.setText(" Cloth Type ");
                        tv0.setTextColor(Color.BLACK);
                        tv0.setGravity(Gravity.CENTER);
                        tbrow0.addView(tv0);
                        TextView tv2 = new TextView(CustomTabActivity.this);
                        tv2.setText(" Quantity ");
                        tv2.setTextColor(Color.BLACK);
                        tv2.setGravity(Gravity.CENTER);
                        tbrow0.addView(tv2);
                        TextView tv3 = new TextView(CustomTabActivity.this);
                        tv3.setText(" Price ");
                        tv3.setTextColor(Color.BLACK);
                        tv3.setGravity(Gravity.CENTER);
                        tbrow0.addView(tv3);
                        stk.addView(tbrow0);
                        for (int i = 0; i < billDetailsArrayList.size(); i++) {
                            TableRow tbrow = new TableRow(CustomTabActivity.this);
                            TextView t1v = new TextView(CustomTabActivity.this);
                            t1v.setText(billDetailsArrayList.get(i).getClothType());
                            t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.CENTER);
                            tbrow.addView(t1v);

                            TextView t3v = new TextView(CustomTabActivity.this);
                            t3v.setText("some qty");
                            t3v.setTextColor(Color.BLACK);
                            t3v.setGravity(Gravity.CENTER);
                            tbrow.addView(t3v);
                            TextView t4v = new TextView(CustomTabActivity.this);
                            t4v.setText(billDetailsArrayList.get(i).getPrice());
                            t4v.setTextColor(Color.BLACK);
                            t4v.setGravity(Gravity.CENTER);
                            tbrow.addView(t4v);
                            stk.addView(tbrow);
                        }


                        TableRow tr81 = new TableRow(CustomTabActivity.this);
                        TextView tv81 = new TextView(CustomTabActivity.this);
                        tv81.setText("Total Bill - " + total);
                        tv81.setTextColor(Color.BLACK);
                        tv81.setGravity(Gravity.CENTER);
                        tr81.addView(tv81);
                        stk.addView(tr81);


                        Button confirm = (Button) dialog.findViewById(com.soullaundry.R.id.btn_dialog);
                        confirm.setText("Confirm");
                        confirm.setTextColor(Color.BLACK);

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd : HH:mm");// HH:mm:ss");
                                String reg_date = df.format(c.getTime());
                                pickDate = reg_date;

                                //System.out.println(deldays.getSelectedItem().toString());
                                c.add(Calendar.DATE, 3);  // number of days to add

                                time = df.format(c.getTime());
                                DelDate = time;

                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("usersG");
                                mDatabase.keepSynced(true);
                                String userId = mDatabase.push().getKey();

                                String nameI = getIntent().getStringExtra("name");

                                String phoneI = getIntent().getStringExtra("phone");

                                String billNumberI = getIntent().getStringExtra("billNumber");

                                User user = new User(nameI, phoneI, billDetailsArrayList, billNumberI, total, total, 0, DelDate, pickDate);

                                mDatabase.child(userId).setValue(user);
                                dialog.dismiss();
                                //Toastmsg(CollectionActivity.this,"Your Order has been placed Successfully");
                                int qtyTotal = 0;
                                try {
                                    SmsManager smsManager = SmsManager.getDefault();

                                    message = "Your order has been placed successfully with Soul Laundromat";
                                    for (int i = 0; i < billDetailsArrayList.size(); i++) {


                                    }
                                    message1 += "\n \n Bill no:" + billNumber + "\nTotal Price :Rs " + total + "\nDelivery date: " + time;
                                    message2 += "\n\nPaytm Number for Payment : " + "9980461461";
                                    message3 += "\n \n Thank you.";
                                    message += message1 + message2 + message3;

                                    PendingIntent sentPI = PendingIntent.getBroadcast(CustomTabActivity.this, 0, new Intent("SENT_SMS_ACTION_NAME"), 0);
                                    PendingIntent deliveredPI = PendingIntent.getBroadcast(CustomTabActivity.this, 0, new Intent("DELIVERED_SMS_ACTION_NAME"), 0);


                                    SmsManager sms = SmsManager.getDefault();
                                    ArrayList<String> parts = sms.divideMessage(message);

                                    ArrayList<PendingIntent> sendList = new ArrayList<PendingIntent>();
                                    sendList.add(sentPI);

                                    ArrayList<PendingIntent> deliverList = new ArrayList<PendingIntent>();
                                    deliverList.add(deliveredPI);

                                    sms.sendMultipartTextMessage("+91" + phoneI, null, parts, sendList, deliverList);
                                    //smsManager.sendTextMessage("+91"+phone, null,message, null, null);
                                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                                            Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),
                                            "SMS failed, please try again later!",
                                            Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                                Intent i=new Intent(CustomTabActivity.this,SampleActivity.class);
                                startActivity(i);

                            }
                        });

                        Button cancel = (Button) dialog.findViewById(com.soullaundry.R.id.btn_dialog3);
                        cancel.setText("Cancel");
                        cancel.setTextColor(Color.BLACK);

                        cancel.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {

                                dialog.dismiss();
                            }
                        });


                        dialog.show();



                }

            }
        });




    }





    private void setupViewPager(ViewPager viewPager)
    {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        callsFragment=new CallsFragment();
        chatFragment=new CallsFragment();
        contactsFragment=new CallsFragment();
        OnlyIron=new CallsFragment();

        adapter.addFragment(callsFragment,"Dry Cleaning");
        adapter.addFragment(chatFragment,"Wash/Fold");
        adapter.addFragment(contactsFragment,"Wash/Iron");

        adapter.addFragment(OnlyIron,"Only Iron");
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if(unreadCount[pos]>0)
        {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText(""+unreadCount[pos]);
        }
        else
            tv_count.setVisibility(View.GONE);


        return view;
    }

    private void setupTabIcons()
    {

        for(int i=0;i<tabTitle.length;i++)
        {

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }


    }

    @Override
    public void onDataPass(ArrayList<Detail> data) {
        Log.d("LOG","hello " + data);
        for(int i=0;i<data.size();i++){
            Log.d("LOG","hello " + data.get(i).cloth+"  "+data.get(i).count);


            Integer actualprice = 0;


            BillDetails b=new BillDetails(data.get(i).cloth,"10",data.get(i).count,"some service type");

            total+= data.get(i).count* 10;
            billDetailsArrayList.add(b);


        }






    }
}

