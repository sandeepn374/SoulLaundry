package com.soullaundry;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
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


public class CollectionActivity extends AppCompatActivity
{




	EditText edt_name;
	EditText edt_phone,qty,edit_email_id;
	TextView edt_email,qtytext;
	Spinner price,cloth,service,deltype,deldays;

	Button btn_submit;
	String message;
	String message1="";
	String message2="";
	String message3="";
	String name,phone,billNumber,emailStr;
	String pickDate,DelDate;

	String time;
	final String[] clothType = {""};

	final String[] svc = {""};

	final String[] qtySpinner = {""};

	final String[] delivery = {""};

	final String[] priceSpinner = {""};

	int total=0;

	ArrayList<BillDetails> billDetailsArrayList= new ArrayList<BillDetails>();



	private void Toastmsg(CollectionActivity collectionActivity, String p1)
	{

		Toast.makeText(this,p1,
					   Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {





		super.onCreate(savedInstanceState);
		setContentView(com.soullaundry.R.layout.collection);
		edt_name= (EditText) findViewById(com.soullaundry.R.id.edt_name);

		edt_phone= (EditText) findViewById(com.soullaundry.R.id.edt_phone);

		edt_email= (TextView) findViewById(com.soullaundry.R.id.edt_email);

		btn_submit = (Button)findViewById(com.soullaundry.R.id.btn_submit);
		deltype=(Spinner)findViewById(R.id.deltype);
		deldays=(Spinner)findViewById(R.id.deldays);
		edit_email_id=(EditText)findViewById(R.id.edt_email_id);


       






		





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
										if(user.ph!=null) {
											if (user.ph.equals(edt_phone.getText())) {
												name = user.name;
												edt_name.setText(user.name);
											}
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
						if(db!=null) {
							if (db.equals(et)) {
								edt_name.setText(user.name);
							}
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
					if(child.child("billNumber").getValue()!=null)
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






		/*btn_add.setOnClickListener(new OnClickListener(){

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
				else if(qty.length()==0){
					qty.setError("PLease enter quantity");

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

					BillDetails b=new BillDetails(clothType[0],"kjs",9,svc[0]);
					addedcontent.append("\n"+clothType[0]+"  price= "+actualprice.toString()+" qty="+qty.getText().toString());

					total+= Integer.parseInt(String.valueOf(qty.getText()))* actualprice;
					billDetailsArrayList.add(b);
					price.setSelection(0);
					qty.setText("");
					cloth.setSelection(0);
					//service.setSelection(0);
					//kgpc.setSelection(0);
					Toastmsg(CollectionActivity.this,"Added");
					
				}




			}



		});*/



		btn_submit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {



				name=edt_name.getText().toString();

				billNumber=edt_email.getText().toString();

				phone=edt_phone.getText().toString();
				emailStr=edit_email_id.getText().toString().trim();

				String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
				//String email = emailValidate.getText().toString().trim();


				 if((name.length() == 0)){
					edt_name.setError("Please Enter  Name");
				}
				else if(phone.length()==0 && emailStr.length()==0){
					Toast.makeText(getApplicationContext(),
							"Please enter either Email or Phone Number",
							Toast.LENGTH_LONG).show();
				}

				else if((emailStr.length() !=0 || phone.length()!=10)){
					if(emailStr.length()>0)
						System.out.println("no ptoblem");
					else
					{
						if (phone.length()!=0)

							edt_phone.setError("Please Enter valid phone number");
					}
				}
				else if(deltype.getSelectedItem().toString().equals("Select Delivery Type")){

					((TextView)deltype.getChildAt(0)).setError("Please select delivery type");


				}
				else if(deldays.getSelectedItem().toString().equals("Select Delivery Days")){

					((TextView)deldays.getChildAt(0)).setError("Please select Delivery days");


				}
				else {

					Intent z = new Intent(CollectionActivity.this, CustomTabActivity.class);

					z.putExtra("name", name);

					z.putExtra("phone", phone);
					z.putExtra("billNumber",billNumber);
					z.putExtra("deltype",deltype.getSelectedItem().toString());
					z.putExtra("deldays", deldays.getSelectedItem().toString());
					startActivity(z);

				}






					}
					});




	
	}
	

	
	
	}





	
