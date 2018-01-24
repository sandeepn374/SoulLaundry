package com.soullaundry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kshravi on 24/01/2018 AD.
 */
public class CustomTabActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_without_icon);

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
        Fragment customFragment = (CallsFragment) getSupportFragmentManager().getFragments().get(0);

        ScrollView add=(ScrollView) customFragment.getView();
        Button adder=(Button) add.findViewById(R.id.adder);

        adder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                Toast.makeText(CustomTabActivity.this,"adder added your stuff",
                        Toast.LENGTH_SHORT).show();
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
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }


    }
}

