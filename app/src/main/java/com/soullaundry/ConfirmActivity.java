package com.soullaundry;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.telephony.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.google.firebase.database.*;
import java.util.*;

/**
 * Created by kshravi on 25/12/2017 AD.
 */

public class ConfirmActivity extends Activity{
	ArrayList<BillDetails> billDetailsArrayList= new ArrayList<BillDetails>();
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.soullaundry.R.layout.layout);
		
		User user=(User) getIntent().getSerializableExtra("MyClass");
		
		this.billDetailsArrayList=user.billDetailsArrayList;
        TableLayout stk = (TableLayout) findViewById(com.soullaundry.R.id.table_main);
		TableRow tbrow99 = new TableRow(ConfirmActivity.this);
		TextView tv99 = new TextView(ConfirmActivity.this);
		tv99.setText("Bill Number - " + user.billNumber);
		tv99.setGravity(Gravity.CENTER);
		tbrow99.addView(tv99);
		stk.addView(tbrow99);

		TableRow tbrow0 = new TableRow(ConfirmActivity.this);
		TextView tv0 = new TextView(ConfirmActivity.this);
		tv0.setText(" Cloth Type ");
		tv0.setTextColor(Color.BLACK);
		tv0.setGravity(Gravity.CENTER);
		tbrow0.addView(tv0);
		TextView tv1 = new TextView(ConfirmActivity.this);
		tv1.setText(" SVC Type ");
		tv1.setTextColor(Color.BLACK);
		tv1.setGravity(Gravity.CENTER);
		tbrow0.addView(tv1);
		TextView tv2 = new TextView(ConfirmActivity.this);
	tv2.setText(" Quantity ");
		tv2.setTextColor(Color.BLACK);
		tv2.setGravity(Gravity.CENTER);
		tbrow0.addView(tv2);
		TextView tv3 = new TextView(ConfirmActivity.this);
		tv3.setText(" Price ");
		tv3.setTextColor(Color.BLACK);
		tv3.setGravity(Gravity.CENTER);
		tbrow0.addView(tv3);
		stk.addView(tbrow0);
		for (int i = 0; i < billDetailsArrayList.size(); i++) {
			TableRow tbrow = new TableRow(ConfirmActivity.this);
			TextView t1v = new TextView(ConfirmActivity.this);
			t1v.setText(billDetailsArrayList.get(i).getClothType());
			t1v.setTextColor(Color.BLACK);
			t1v.setGravity(Gravity.CENTER);
			tbrow.addView(t1v);

			TextView t2v = new TextView(ConfirmActivity.this);
			t2v.setText(""+billDetailsArrayList.get(i).getQty());
			t2v.setTextColor(Color.BLACK);
			t2v.setGravity(Gravity.CENTER);
			tbrow.addView(t2v);
			TextView t3v = new TextView(ConfirmActivity.this);
			t3v.setText(billDetailsArrayList.get(i).getPrice());
			t3v.setTextColor(Color.BLACK);
			t3v.setGravity(Gravity.CENTER);
			tbrow.addView(t3v);
			TextView t4v = new TextView(ConfirmActivity.this);
			t4v.setText(billDetailsArrayList.get(i).svcType);
			t4v.setTextColor(Color.BLACK);
			t4v.setGravity(Gravity.CENTER);
			tbrow.addView(t4v);
			stk.addView(tbrow);
		}


		TableRow tr81 = new TableRow(ConfirmActivity.this);
		TextView tv81 = new TextView(ConfirmActivity.this);
	tv81.setText("Total Bill - " + user.total);
		tv81.setTextColor(Color.BLACK);
		tv81.setGravity(Gravity.CENTER);
		tr81.addView(tv81);
		stk.addView(tr81);

		
		TableRow tr90 = new TableRow(ConfirmActivity.this);
		Button tv90 = new Button(ConfirmActivity.this);
		tv90.setText("Confirm");
		//tv81.setTextColor(Color.BLACK);
		tv90.setGravity(Gravity.CENTER);
		tr90.addView(tv90);
		stk.addView(tr90);

		tv90.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("usersG");
					mDatabase.keepSynced(true);
					String userId = mDatabase.push().getKey();

					User user=(User) getIntent().getSerializableExtra("MyClass");
					
					mDatabase.child(userId).setValue(user);
					//dialog.dismiss();
					//Toastmsg(CollectionActivity.this,"Your Order has been placed Successfully");
					float qtyTotal = 0;
					try {
						SmsManager smsManager = SmsManager.getDefault();

						String message = "Your order has been done successfully";
						for (int i = 0; i < billDetailsArrayList.size(); i++) {

							float qty = billDetailsArrayList.get(i).getQty();
							qtyTotal += qty;

						}
						String message1 = "ABC Drycleaners \nThanks for your order \n" + "Bill no:" + user.billNumber + " Quantity: " + qtyTotal + "\nTotal Price :Rs " + user.total + "\nDelivery date: " + user.deliveryDate + "\nFor terms and conditions please refer abcdrycleaners.com";
						String message2 = "\n\nCustomer Name: " + user.name + "\nBill no:" + user.billNumber + " Quantity: " + qtyTotal;
						//message3+= "Cloth Type"+user.billDetailsArrayList;
						message += "Bill no:" + user.billNumber + "Quantity: " + qtyTotal + "Total Price :Rs " + user.total + "Delivery date:      " + user.deliveryDate + "For terms and conditions please refer abcdrycleaners.com";
						for(int k=0;k<billDetailsArrayList.size();k++){
							message+="Cloth Type "+billDetailsArrayList.get(k).clothType+"\n"+"Price "+billDetailsArrayList.get(k).price+"\n"+"Qty "+billDetailsArrayList.get(k).qty+"\n";

						}
						PendingIntent sentPI = PendingIntent.getBroadcast(ConfirmActivity.this, 0, new Intent("SENT_SMS_ACTION_NAME"), 0);
						PendingIntent deliveredPI = PendingIntent.getBroadcast(ConfirmActivity.this, 0, new Intent("DELIVERED_SMS_ACTION_NAME"), 0);


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
				
				}
			});
		
    }
}
