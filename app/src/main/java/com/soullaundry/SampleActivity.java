package com.soullaundry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class SampleActivity extends Activity {
    Button collection;
    Button delivery;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private boolean drawerArrowColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.soullaundry.R.layout.activity_sample);

        collection = (Button) findViewById(com.soullaundry.R.id.collection);
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), CollectionActivity.class);
                view.getContext().startActivity(Intent);
            }
        });


        delivery = (Button) findViewById(com.soullaundry.R.id.delivery);
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), DeliveryActivity.class);
                view.getContext().startActivity(Intent);
            }
        });

    }
    public void onBackPressed(){
        finish();
        super.onBackPressed();

    }
}
