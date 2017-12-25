package com.soullaundry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class CollectionActivity extends Activity
{


	EditText edt_name;
	EditText edt_phone;
	TextView edt_email;
	Spinner price,cloth,qty,service,deltype;

	Button btn_add,btn_submit;
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

	static {
		//FirebaseDatabase.getInstance().setPersistenceEnabled(true);
	}

	private void billdetails() {

		BillDetails b=new BillDetails(clothType[0],priceSpinner[0],qtySpinner[0],svc[0]);

		total+= Integer.parseInt(qtySpinner[0])* Integer.parseInt(priceSpinner[0]);
		billDetailsArrayList.add(b);
		price.setSelection(0);
		qty.setSelection(0);
		cloth.setSelection(0);
		service.setSelection(0);
		deltype.setSelection(0);
		Toastmsg(CollectionActivity.this,"Added");
	}


	private void Toastmsg(CollectionActivity collectionActivity, String p1)
	{

		Toast.makeText(this,p1,
					   Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd : HH:mm");// HH:mm:ss");
		String reg_date = df.format(c.getTime());
		pickDate=reg_date;

		c.add(Calendar.DATE, 3);  // number of days to add

		time = df.format(c.getTime());
		DelDate=time;
		super.onCreate(savedInstanceState);
		setContentView(com.soullaundry.R.layout.collection);
		edt_name= (EditText) findViewById(com.soullaundry.R.id.edt_name);

		edt_phone= (EditText) findViewById(com.soullaundry.R.id.edt_phone);

		edt_email= (TextView) findViewById(com.soullaundry.R.id.edt_email);

		btn_add = (Button) findViewById(com.soullaundry.R.id.btn_add);

		btn_submit = (Button)findViewById(com.soullaundry.R.id.btn_submit);
		service=(Spinner)findViewById(R.id.service);
        cloth=(Spinner) findViewById(R.id.cloth);


		price = (Spinner) findViewById(com.soullaundry.R.id.price);


		qty = (Spinner) findViewById(com.soullaundry.R.id.qty);
		deltype=(Spinner)findViewById(R.id.deltype);





		price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// your code here

				if (price.getSelectedItem().toString().equals("Others")){

					final AlertDialog.Builder alert = new AlertDialog.Builder(CollectionActivity.this);


					final EditText edittext = new EditText(CollectionActivity.this);
					alert.setMessage("Please enter the price");
					alert.setTitle("Price");

					alert.setView(edittext);


					alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							//What ever you want to do with the value
							//Editable YouEditTextValue = edittext.getText();
							//OR

							priceSpinner[0] =edittext.getText().toString();





						}
					});

					alert.setNegativeButton("No ", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							// what ever you want to do with No option.
							//alert1.dismiss();
						}
					});

					final AlertDialog alert1 = alert.create();

					alert1.show();





				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});




		qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// your code here

				if (qty.getSelectedItem().toString().equals("Others")){

					final AlertDialog.Builder alert = new AlertDialog.Builder(CollectionActivity.this);


					final EditText edittext = new EditText(CollectionActivity.this);
					alert.setMessage("Please enter the Quantity");
					alert.setTitle("Quantity");

					alert.setView(edittext);


					alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							//What ever you want to do with the value
							//Editable YouEditTextValue = edittext.getText();
							//OR

							qtySpinner[0] =edittext.getText().toString();





						}
					});

					alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							// what ever you want to do with No option.
							//alert1.dismiss();
						}
					});

					final AlertDialog alert1 = alert.create();

					alert1.show();





				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
		edt_phone.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

				// you can call or do what you want with your EditText here
				if (s.length() == 10) {
					FirebaseDatabase.getInstance().getReference().child("usersG")
							.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot dataSnapshot) {
									for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
										User user = snapshot.getValue(User.class);

										System.out.println("db" + user.ph);
										System.out.println("et" + edt_phone.getText());
										if (user.ph.equals(edt_phone.getText())) {
											name=user.name;
											edt_name.setText(user.name);
										}
										//System.out.println(user.email);
									}
								}

								@Override
								public void onCancelled(DatabaseError databaseError) {
								}
							});
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			public void onTextChanged(final CharSequence s, int start, int before, int count) {

if (s.length()==10){


	FirebaseDatabase.getInstance().getReference().child("usersG")
			.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
						User user = snapshot.getValue(User.class);
						System.out.println("db"+user.ph);
						System.out.println("et"+edt_phone.getText());
						String db=user.ph;
						String et=s.toString();
						if(db.equals(et)){
							edt_name.setText(user.name);
						}
						//System.out.println(user.email);
					}
				}
				@Override
				public void onCancelled(DatabaseError databaseError) {
				}
			});
}


			}
		});




		DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference().child("usersG");
		mDatabase.keepSynced(true);

		Query query = mDatabase.orderByKey().limitToLast(1);
		query.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				//String billNumber=null;
				for (DataSnapshot child: dataSnapshot.getChildren()) {
					Log.d("User key", child.getKey());
					Log.d("User val", child.child("billNumber").getValue().toString());
					billNumber=child.child("billNumber").getValue().toString();

				}



				if(billNumber!=null) {
					String number = billNumber.substring(1);
					int foo = Integer.parseInt(number);
					foo++;
					if (foo < 9) {
						String last = String.valueOf(foo);
						billNumber = "A000" + last;
						edt_email.setText(billNumber);
					} else if (foo < 99) {
						String last = String.valueOf(foo);
						billNumber = "A00" + last;

						edt_email.setText(billNumber);
					}
				}
				else{

					billNumber="A0001";
					edt_email.setText(billNumber);
				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});;






		btn_add.setOnClickListener(new OnClickListener(){

			public void onClick(View view){

				//String temp=null;
				 name=edt_name.getText().toString();

				 billNumber=edt_email.getText().toString();

				 phone=edt_phone.getText().toString();


				if (cloth.getSelectedItem().toString().equals("Others")){
					System.out.print("cool");
				}
					else {
					clothType[0] = cloth.getSelectedItem().toString();
				}

				//validation
			/*	if (spinner.getSelectedItem().toString().trim().equals("Select Cloth Type")) {
					Toastmsg(CollectionActivity.this, "Please Select Cloth Type");
				}
				else{
					Log.v("Spinner440",spinner.toString());
				}

*/

				if (price.getSelectedItem().toString().equals("Others")){
					System.out.print("cool");
				}else {
					priceSpinner[0] = price.getSelectedItem().toString();
				}
				//String text2 = spinner2.getSelectedItem().toString();
//validation
				/*if (spinner1.getSelectedItem().toString().trim().equals("Select Price")) {
					Toastmsg(CollectionActivity.this, "Please Select Price");
				}
				else{
					Log.v("Spinner456",spinner1.toString());
				}*/

				if (qty.getSelectedItem().toString().equals("Others")){
					System.out.print("cool");
				}else {
					qtySpinner[0] = qty.getSelectedItem().toString();
				}
				//validation
				/*if (spinner3.getSelectedItem().toString().trim().equals("Select Quantity")) {
					Toastmsg(CollectionActivity.this, "Please Select Quantity");
				}
				else{
					Log.v("Spinner469",spinner3.toString());
				}*/

				//text2[0]=spinner2.getSelectedItem().toString();
				//if(text[0].equals("Others")){
//validation
				/*if (spinner2.getSelectedItem().toString().trim().equals("Select Remark")) {
					Toastmsg(CollectionActivity.this, "Please Select Remark");
				}
				else{
					Log.v("Spinner479",spinner2.toString());
				}*/

				if((name.length() == 0)){
					edt_name.setError("Please Enter  Name");
				}
				else if(phone.length()<9){
					edt_phone.setError("Please enter valid Phone Number");
				}
				else if(cloth.getSelectedItem().toString().trim().equals("Select Cloth Type")){
					((TextView)cloth.getChildAt(0)).setError("Please Enter Cloth Type");

				}
				else if(price.getSelectedItem().toString().trim().equals("Select Price")){
					((TextView)price.getChildAt(0)).setError("Please Enter Price");

				}


				else if(qty.getSelectedItem().toString().trim().equals("Select Quantity")){
					((TextView)qty.getChildAt(0)).setError("Please Enter Quantity");

				}

				else{
				billdetails();
				}




			}



		});



		btn_submit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
		

				if(billDetailsArrayList.size()==0){


					Toastmsg(CollectionActivity.this,"You have not entered any bill to submit");
				}
				
				
				
				else{
					Intent j=new Intent(CollectionActivity.this,ConfirmActivity.class);
					User user = new User(name, phone, billDetailsArrayList, billNumber, total, total, 0,DelDate,pickDate);
					j.putExtra("MyClass", user);
					
					startActivity(j);
					}
					
					}
					});
					/*

				final Dialog dialog = new Dialog(CollectionActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setCancelable(false);
				dialog.setContentView(com.soullaundry.R.layout.layout);

				TableLayout stk = (TableLayout) dialog.findViewById(com.soullaundry.R.id.table_main);
				TableRow tbrow99 = new TableRow(CollectionActivity.this);
				TextView tv99 = new TextView(CollectionActivity.this);
				tv99.setText("Bill Number - " + billNumber);
				tv99.setGravity(Gravity.CENTER);
				tbrow99.addView(tv99);
				stk.addView(tbrow99);

				TableRow tbrow0 = new TableRow(CollectionActivity.this);
				TextView tv0 = new TextView(CollectionActivity.this);
				tv0.setText(" Cloth Type ");
				tv0.setTextColor(Color.BLACK);
				tv0.setGravity(Gravity.CENTER);
				tbrow0.addView(tv0);
				TextView tv1 = new TextView(CollectionActivity.this);
				tv1.setText(" SVC Type ");
				tv1.setTextColor(Color.BLACK);
				tv1.setGravity(Gravity.CENTER);
				tbrow0.addView(tv1);
				TextView tv2 = new TextView(CollectionActivity.this);
				tv2.setText(" Quantity ");
				tv2.setTextColor(Color.BLACK);
				tv2.setGravity(Gravity.CENTER);
				tbrow0.addView(tv2);
				TextView tv3 = new TextView(CollectionActivity.this);
				tv3.setText(" Price ");
				tv3.setTextColor(Color.BLACK);
				tv3.setGravity(Gravity.CENTER);
				tbrow0.addView(tv3);
				stk.addView(tbrow0);
				for (int i = 0; i < billDetailsArrayList.size(); i++) {
					TableRow tbrow = new TableRow(CollectionActivity.this);
					TextView t1v = new TextView(CollectionActivity.this);
					t1v.setText(billDetailsArrayList.get(i).getClothType());
					t1v.setTextColor(Color.BLACK);
					t1v.setGravity(Gravity.CENTER);
					tbrow.addView(t1v);

					TextView t2v = new TextView(CollectionActivity.this);
					t2v.setText("Put somethng else");
					t2v.setTextColor(Color.BLACK);
					t2v.setGravity(Gravity.CENTER);
					tbrow.addView(t2v);
					TextView t3v = new TextView(CollectionActivity.this);
					t3v.setText(billDetailsArrayList.get(i).getQty());
					t3v.setTextColor(Color.BLACK);
					t3v.setGravity(Gravity.CENTER);
					tbrow.addView(t3v);
					TextView t4v = new TextView(CollectionActivity.this);
					t4v.setText(billDetailsArrayList.get(i).getPrice());
					t4v.setTextColor(Color.BLACK);
					t4v.setGravity(Gravity.CENTER);
					tbrow.addView(t4v);
					stk.addView(tbrow);
				}


				TableRow tr81 = new TableRow(CollectionActivity.this);
				TextView tv81 = new TextView(CollectionActivity.this);
				tv81.setText("Total Bill - " + total);
				tv81.setTextColor(Color.BLACK);
				tv81.setGravity(Gravity.CENTER);
				tr81.addView(tv81);
				stk.addView(tr81);

	
				Button confirm = (Button) dialog.findViewById(com.soullaundry.R.id.btn_dialog);
				confirm.setText("Confirm");
				confirm.setTextColor(Color.BLACK);


				confirm.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {


						DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("usersG");
						mDatabase.keepSynced(true);
						String userId = mDatabase.push().getKey();

						User user = new User(name, phone, billDetailsArrayList, billNumber, total, total, 0,DelDate,pickDate);

						mDatabase.child(userId).setValue(user);
						dialog.dismiss();
						//Toastmsg(CollectionActivity.this,"Your Order has been placed Successfully");
						int qtyTotal = 0;
						try {
							SmsManager smsManager = SmsManager.getDefault();

							message = "Your order has been done successfully";
							for (int i = 0; i < billDetailsArrayList.size(); i++) {

								String qty = billDetailsArrayList.get(i).getQty();
								qtyTotal += Integer.parseInt(qty);

							}
							message1 += "ABC Drycleaners \nThanks for your order \n" + "Bill no:" + billNumber + " Quantity: " + qtyTotal + "\nTotal Price :Rs " + total + "\nDelivery date: " + time + "\nFor terms and conditions please refer abcdrycleaners.com";
							message2 += "\n\nCustomer Name: " + name + "\nBill no:" + billNumber + " Quantity: " + qtyTotal;
							//message3+= "Cloth Type"+user.billDetailsArrayList;
							message += "Bill no:" + billNumber + "Quantity: " + qtyTotal + "Total Price :Rs " + total + "Delivery date:      " + time + "For terms and conditions please refer abcdrycleaners.com";
							for(int k=0;k<billDetailsArrayList.size();k++){
								message+="Cloth Type "+billDetailsArrayList.get(k).clothType+"\n"+"Price "+billDetailsArrayList.get(k).price+"\n"+"Qty "+billDetailsArrayList.get(k).qty+"\n";

							}
							PendingIntent sentPI = PendingIntent.getBroadcast(CollectionActivity.this, 0, new Intent("SENT_SMS_ACTION_NAME"), 0);
							PendingIntent deliveredPI = PendingIntent.getBroadcast(CollectionActivity.this, 0, new Intent("DELIVERED_SMS_ACTION_NAME"), 0);


							SmsManager sms = SmsManager.getDefault();
							ArrayList<String> parts = sms.divideMessage(message);

							ArrayList<PendingIntent> sendList = new ArrayList<PendingIntent>();
							sendList.add(sentPI);

							ArrayList<PendingIntent> deliverList = new ArrayList<PendingIntent>();
							deliverList.add(deliveredPI);

							sms.sendMultipartTextMessage("+91" + phone, null, parts, sendList, deliverList);
							//smsManager.sendTextMessage("+91"+phone, null,message, null, null);
							Toast.makeText(getApplicationContext(), "SMS Sent!",
									Toast.LENGTH_LONG).show();
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(),
									"SMS failed, please try again later!",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
						edt_name.setText("");
						edt_phone.setText("");
						AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);

						builder.setTitle("Confirm");
						builder.setMessage("DO you want to Print the Bill ?");

						builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								// Do nothing but close the dialog

								//dialog.dismiss();
								Toast.makeText(getApplicationContext(),
										message,
										Toast.LENGTH_LONG).show();
								Intent i = new Intent(CollectionActivity.this, MainActivity.class);
								Bundle b = new Bundle();

								//Inserts a String value into the mapping of this Bundle
								b.putString("MESSAGE1", message1);
								b.putString("MESSAGE2", message2);
								i.putExtras(b);
								startActivity(i);
							}
						});

						builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								// Do nothing
								dialog.dismiss();

								Intent i = new Intent(CollectionActivity.this, SampleActivity.class);

//
								startActivity(i);
							}
						});

						AlertDialog alert = builder.create();
						alert.show();

						//new RetrieveFeedTask().execute("subbu");
						//sendLongSMS("hi hi");
						///////


					}
				});

				Button cancel = (Button) dialog.findViewById(com.soullaundry.R.id.btn_dialog3);
				cancel.setText("Cancel");
				cancel.setTextColor(Color.BLACK);

				cancel.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {

						dialog.dismiss();
					}
				});


				dialog.show();

			}
			
			}


});

	}*/



	
	}}





	
