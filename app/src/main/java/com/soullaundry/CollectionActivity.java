package com.soullaundry;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.telephony.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.google.firebase.database.*;
import java.text.*;
import java.util.*;

import android.view.View.OnClickListener;


public class CollectionActivity extends Activity
{


	EditText edt_name;
	EditText edt_phone,qty;
	TextView edt_email,addedcontent;
	Spinner price,cloth,service,deltype,deldays,kgpc;

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


    private void initCustomSpinner() {

        cloth= (Spinner) findViewById(R.id.cloth);
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        
		languages.add("Cloth");
		languages.add("Double Quilt Blanket");
		languages.add("Single QuiltBlanket");
        languages.add("Suit(2pcs)");
        languages.add("Suit(3pcs)");
        languages.add("Jacket Blazer");
        languages.add("Half-JacketBlazer");
		languages.add("Leather Jacket");
        languages.add("Sherwani");
        languages.add("Ladies Top");
		languages.add("Ladies Skirt");
        languages.add("Sweater");
		languages.add("Kurta");
        languages.add("Silk Kurta");
        languages.add("Fancy Kurta");
		languages.add("Pyjama");
        languages.add("Silk Pyjama");
        languages.add("Lehenga");
        languages.add("Dress -Small");
        languages.add("Dress- Long");
		languages.add("Anarkali Dress");
        languages.add("Selwar");
		languages.add("Dupatta");
		languages.add("Gown ");
		languages.add("Frock ");
		languages.add("Shirts /Tshirts ");
        languages.add("Trousers/Jeans ");
		languages.add("Coat ");
        languages.add("OverCoat ");
        languages.add("Carpet ");
		languages.add("Shall ");
		languages.add("PLain Saree ");
		languages.add("Silk Saree ");
        languages.add("Silk Saree (Fancy)");
		languages.add("Cotton Saree ");
        languages.add("Single Bedsheet ");
        languages.add("Double Bedsheet ");
        languages.add("Pillow Cover ");
		languages.add("Door Curtain ");
        languages.add("Window Curtain");
		languages.add("Towel ");
		languages.add("Dhoti cotton ");
		languages.add("Dhoti silk");
		languages.add("Others ");
			
		
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(CollectionActivity.this,languages);
        cloth.setAdapter(customSpinnerAdapter);
    		
    }


	private void Toastmsg(CollectionActivity collectionActivity, String p1)
	{

		Toast.makeText(this,p1,
					   Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {





		super.onCreate(savedInstanceState);
		setContentView(com.soullaundry.R.layout.collection);
		initCustomSpinner();
	//	kgpc=(Spinner)findViewById(R.id.kgpc);
		edt_name= (EditText) findViewById(com.soullaundry.R.id.edt_name);

		edt_phone= (EditText) findViewById(com.soullaundry.R.id.edt_phone);

		edt_email= (TextView) findViewById(com.soullaundry.R.id.edt_email);
		addedcontent=(TextView)findViewById(R.id.adddedcontent);

		btn_add = (Button) findViewById(com.soullaundry.R.id.btn_add);

		btn_submit = (Button)findViewById(com.soullaundry.R.id.btn_submit);
		service=(Spinner)findViewById(R.id.service);
       


		price = (Spinner) findViewById(com.soullaundry.R.id.price);


		qty = (EditText) findViewById(com.soullaundry.R.id.qty);
		deltype=(Spinner)findViewById(R.id.deltype);
		deldays=(Spinner)findViewById(R.id.deldays);
      
		addedcontent=(TextView)findViewById(R.id.adddedcontent);
		



        price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


				if (price.getSelectedItem().toString().equals("Others")){

					final AlertDialog.Builder alert = new AlertDialog.Builder(CollectionActivity.this);


					final EditText edittext = new EditText(CollectionActivity.this);
					alert.setMessage("Please enter the price");
					alert.setTitle("Price");

					alert.setView(edittext);


					alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {


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

		
				for (DataSnapshot child: dataSnapshot.getChildren()) {
					
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


				if (price.getSelectedItem().toString().equals("Others")){
					System.out.print("cool");
				}else {
					priceSpinner[0] = price.getSelectedItem().toString();
				}

				if(phone.length()<9){
					edt_phone.setError("Please enter valid Phone Number");
				}
				else if((name.length() == 0)){
					edt_name.setError("Please Enter  Name");
				}
				
				else if(cloth.getSelectedItem().toString().trim().equals("Cloth")){
					((TextView)cloth.getChildAt(0)).setError("Please Enter Cloth Type");

				}
				else if(price.getSelectedItem().toString().trim().equals("Price")){
					((TextView)price.getChildAt(0)).setError("Please Enter Price");

				}


				else if(service.getSelectedItem().toString().trim().equals("Select Service Type")){
					((TextView)service.getChildAt(0)).setError("Please select service type ");

				}
				


				else{
			
					
					Integer actualprice = 0;

					if(deltype.getSelectedItem().toString().equals("Express Delivery(Normal * 1.5)"))
						actualprice= (int) (Integer.parseInt(priceSpinner[0])*1.5);
					else
						actualprice=Integer.parseInt(priceSpinner[0]);

					BillDetails b=new BillDetails(clothType[0],actualprice.toString(),qty.getText().toString(),svc[0]);
					addedcontent.append("\n"+clothType[0]+"  price= "+actualprice.toString()+" qty="+qty.getText().toString()+" svc"+svc[0]);

					total+= Integer.parseInt(String.valueOf(qty.getText()))* actualprice;
					billDetailsArrayList.add(b);
					price.setSelection(0);
					qty.setSelection(0);
					//cloth.setSelection(0);
					service.setSelection(0);
					//kgpc.setSelection(0);
					Toastmsg(CollectionActivity.this,"Added");
					
				}




			}



		});



		btn_submit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
		

				if(billDetailsArrayList.size()==0){


					Toastmsg(CollectionActivity.this,"You have not entered any bill to submit");
				}
				
				
				
				else {
					if (deldays.getSelectedItem().toString().trim().equals("Select Delivery Days")) {
						((TextView) deldays.getChildAt(0)).setError("Please select delivery days");

					}
					else if(deltype.getSelectedItem().toString().trim().equals("Select Delivery Type"))
					{
						((TextView) deltype.getChildAt(0)).setError("Please select delivery type");

					}

						else {


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


								Calendar c = Calendar.getInstance();
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd : HH:mm");// HH:mm:ss");
								String reg_date = df.format(c.getTime());
								pickDate = reg_date;

								System.out.println(deldays.getSelectedItem().toString());
								c.add(Calendar.DATE, Integer.parseInt(deldays.getSelectedItem().toString()));  // number of days to add

								time = df.format(c.getTime());
								DelDate = time;

								DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("usersG");
								mDatabase.keepSynced(true);
								String userId = mDatabase.push().getKey();

								User user = new User(name, phone, billDetailsArrayList, billNumber, total, total, 0, DelDate, pickDate);

								mDatabase.child(userId).setValue(user);
								dialog.dismiss();
								//Toastmsg(CollectionActivity.this,"Your Order has been placed Successfully");
								int qtyTotal = 0;
								try {
									SmsManager smsManager = SmsManager.getDefault();

									message = "Your order has been placed successfully with Soul Laundromat";
									for (int i = 0; i < billDetailsArrayList.size(); i++) {

										String qty = billDetailsArrayList.get(i).getQty();
										qtyTotal += Integer.parseInt(qty);

									}
									message1 += "\n \n Bill no:" + billNumber + "\nTotal Price :Rs " + total + "\nDelivery date: " + time;
									message2 += "\n\nPaytm Number for Payment : " + "9980461461";
									message3 += "\n \n Thank you.";
									message += message1 + message2 + message3;

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
					
					}
					});




	
	}
	
	

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context,ArrayList<String> asr) {
            this.asr=asr;
            activity = context;
        }



        public int getCount()
        {
            return asr.size();
        }

        public Object getItem(int i)
        {
            return asr.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(CollectionActivity.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(CollectionActivity.this);
            txt.setGravity(Gravity.LEFT);
            txt.setPadding(0, 0, 0, 0);
            txt.setTextSize(10);
           // txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#110000"));
            return  txt;
        }

    }
	
	
	}





	
