package com.soullaundry;

/**
 * Created by kshravi on 05/11/2017 AD.
 */

import java.io.*;

public class BillDetails implements Serializable
{


    String clothType;
    int qty;
    String price;
	String svcType;

    public BillDetails(){}
    public BillDetails(String clothType, String price ,int qty,String svc){
        this.clothType=clothType;
        this.qty=qty;
        this.price=price;
		this.svcType=svc;


    }

    public String getClothType(){

        return this.clothType;
    }

    public int getQty(){
        return this.qty;
    }
    public String getPrice(){
        return this.price;
    }


}
