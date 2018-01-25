package com.soullaundry;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kshravi on 24/01/2018 AD.
 */
public class CallsFragment extends Fragment {
	
	OnDataPass dataPasser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView= inflater.inflate(R.layout.fragment_calls, container, false);


        Button btn = (Button) rootView.findViewById(R.id.adder);

        final TableLayout layout = (TableLayout) rootView.findViewById(R.id.IdTable);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


ArrayList<Detail> sending=new ArrayList<Detail>();

                for (int i = 2; i < layout.getChildCount(); i++) {
                    View child = layout.getChildAt(i);

                    if (child instanceof TableRow) {
                        TableRow row = (TableRow) child;

                        for (int x = 0; x < row.getChildCount(); x++) {
                            if(x==0) {
                              //  TextView edi = (TextView) row.getChildAt(x);
                               // passs+=edi.getText();

                            }
                            else if(x==1)
                            {
                                EditText ediqty=(EditText)row.getChildAt(x);
                                System.out.println("murugan"+ediqty.getText()+ediqty.getText().toString().length());
                                if(ediqty.getText().toString().length()==0)
                                    System.out.println("nothing to pass");
                                else{
                                    Detail d=new Detail();
                                    TextView edi = (TextView) row.getChildAt(0);
                                    d.cloth=edi.getText().toString();
                                    d.count=Integer.parseInt(ediqty.getText().toString());


                                    sending.add(d);

                                }
                            }


                        }
                    }
                }

passData(sending);
if(sending.size()==0){

    Toast.makeText(getContext(),"You have not entered any item to add",
            Toast.LENGTH_SHORT).show();
}
else {
    Toast.makeText(getContext(), "Succesfully Added to the Bill",
            Toast.LENGTH_SHORT).show();
}
            }
        });
        return rootView;
    }

    

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }


    public void passData(ArrayList<Detail> data) {
        dataPasser.onDataPass(data);
    }



}

