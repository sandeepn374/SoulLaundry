package com.soullaundry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
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
    CallsFragment dryCleanFragment,OnlyIron;
    ChatFragment washIronFragment,washFoldFragment;

    String[] tabTitle={"Wash/Fold","Wash/Iron","Only Iron","Dry Cleaning"};
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

    float total=0;
    int delCharges=0;

    ArrayList<BillDetails> billDetailsArrayList= new ArrayList<BillDetails>();
    public Button submitaall;

    public ViewPagerAdapter adapter;
    public TextView tv81;


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
                    TextView tv1 = new TextView(CustomTabActivity.this);
                    tv1.setText(" Svc Type ");
                    tv1.setTextColor(Color.BLACK);
                    tv1.setGravity(Gravity.CENTER);
                    tbrow0.addView(tv1);
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

                            TextView t2v = new TextView(CustomTabActivity.this);
                            t2v.setText(billDetailsArrayList.get(i).svcType);
                            t2v.setTextColor(Color.BLACK);
                            t2v.setGravity(Gravity.CENTER);
                            tbrow.addView(t2v);

                            TextView t3v = new TextView(CustomTabActivity.this);
                            t3v.setText(String.valueOf(billDetailsArrayList.get(i).qty));
                            t3v.setTextColor(Color.BLACK);
                            t3v.setGravity(Gravity.CENTER);
                            tbrow.addView(t3v);
                            TextView t4v = new TextView(CustomTabActivity.this);
                            t4v.setText(String.valueOf(billDetailsArrayList.get(i).getPrice()));
                            t4v.setTextColor(Color.BLACK);
                            t4v.setGravity(Gravity.CENTER);
                            tbrow.addView(t4v);
                            stk.addView(tbrow);
                        }


                        TableRow tr81 = new TableRow(CustomTabActivity.this);
                        tv81 = new TextView(CustomTabActivity.this);

                        tv81.setText("Total Bill = " + total);
                        tv81.setTextColor(Color.BLACK);
                        tv81.setGravity(Gravity.CENTER);
                        tr81.addView(tv81);
                        stk.addView(tr81);
					TableRow trdeltype = new TableRow(CustomTabActivity.this);
					TextView tvdeltype = new TextView(CustomTabActivity.this);
					
					if(getIntent().getStringExtra("deltype").equals("Normal Delivery"))
						

					tvdeltype.setText("Del Type = " + "Normal");
				
					else if(getIntent().getStringExtra("deltype").equals("Express Delivery(Normal * 1.5)"))
						tvdeltype.setText("Del Type = " + "Express");
					
						
					tvdeltype.setTextColor(Color.BLACK);
					tvdeltype.setGravity(Gravity.LEFT);
					trdeltype.addView(tvdeltype);
					stk.addView(trdeltype);
					

                        Button confirm = (Button) dialog.findViewById(com.soullaundry.R.id.btn_dialog);
                        confirm.setText("Confirm");
                        confirm.setTextColor(Color.BLACK);

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String deldaysI=getIntent().getStringExtra("deldays");



                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd : HH:mm");// HH:mm:ss");
                                String reg_date = df.format(c.getTime());
                                pickDate = reg_date;

                                //System.out.println(deldays.getSelectedItem().toString());
                                c.add(Calendar.DATE, Integer.parseInt((deldaysI)));  // number of days to add

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
                                    String rem="\n";
                                    for (int p = 0; p < user.billDetailsArrayList.size(); p++) {

                                        rem = rem + "Cloth Type  " + user.billDetailsArrayList.get(p).clothType + "\n";

                                        rem = rem + "Quantity  " + user.billDetailsArrayList.get(p).qty + "\n";

                                        rem = rem + "Price  " + user.billDetailsArrayList.get(p).price + "\n";

                                    }
                                    message1 += "\n \n Bill no:" + billNumberI + "\nTotal Price :Rs " + total + "\nDelivery date: " + time;
                                    message2 += "\n\nPaytm Number for Payment : " + "9980461461";
                                    message3 += "\n \n Thank you.";
                                    message += message1 +rem+ message2 + message3;

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


                final AlertDialog.Builder alert = new AlertDialog.Builder(CustomTabActivity.this);


                final EditText edittext = new EditText(CustomTabActivity.this);
                alert.setMessage("Please enter if delivery charges applicable");
                alert.setTitle("Amount");

                alert.setView(edittext);

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        //alert.dismiss();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        //Editable YouEditTextValue = edittext.getText();
                        //OR


                        String partial = edittext.getText().toString();
                        if(partial.length()!=0) {
                            delCharges = Integer.parseInt(partial);
                            total+=delCharges;

                            tv81.setText("Total Bill = " + total);
                        }


                    }
                });



                final AlertDialog alert1 = alert.create();

                alert1.show();

            }
        });




    }





    private void setupViewPager(ViewPager viewPager)
    {


         adapter = new ViewPagerAdapter(getSupportFragmentManager());
        washFoldFragment=new ChatFragment();
        washIronFragment=new ChatFragment();
        dryCleanFragment=new CallsFragment();
        OnlyIron=new CallsFragment();

        adapter.addFragment(washFoldFragment,"Wash/Fold");
        adapter.addFragment(washIronFragment,"Wash/Iron");

        adapter.addFragment(OnlyIron,"Only Iron");

        adapter.addFragment(dryCleanFragment,"Dry Cleaning");
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

           System.out.println( viewPager.getCurrentItem());


           int pos=viewPager.getCurrentItem();

            Integer actualprice = 0;
            String service="";

            if(pos==3)
                service="Dry Clean";
            else if(pos==0)
                service="Wash /Fold";
            else if(pos==1)
                service="Wash / Iron";
            else if(pos==2)
                service="Only Iron";


            BillDetails b=new BillDetails(data.get(i).cloth,data.get(i).rate,data.get(i).count,service);

            total+= (data.get(i).count* data.get(i).rate);



            billDetailsArrayList.add(b);


        }

        String deltypeI=getIntent().getStringExtra("deltype");
        if(deltypeI.equals("Express Delivery(Normal * 1.5)"))
            total= (int) (total*1.5);


        total=total+delCharges;






    }
}

