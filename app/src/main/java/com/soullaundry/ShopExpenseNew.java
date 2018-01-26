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
public class ShopExpenseNew extends AppCompatActivity  {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
ContactsFragment paid,unpaid,all;

    String[] tabTitle={"Paid","Unpaid","All"};
    int[] unreadCount={0,0,0};


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
        setContentView(R.layout.shopexpense);

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







    }





    private void setupViewPager(ViewPager viewPager)
    {


        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        paid=new ContactsFragment();
        unpaid=new ContactsFragment();
        all=new ContactsFragment();

        adapter.addFragment(paid,"Paid");
        adapter.addFragment(unpaid,"Unpaid");

        adapter.addFragment(all,"All");

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

}

