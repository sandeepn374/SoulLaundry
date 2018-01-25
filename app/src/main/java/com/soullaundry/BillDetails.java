package com.soullaundry;

/**
 * Created by kshravi on 05/11/2017 AD.
 */

import java.io.*;

public class BillDetails implements Serializable
{


   public String clothType;
    public int qty;
    public int price;
	public String svcType="";
	public int discount;


    public BillDetails(){}
    public BillDetails(String clothType, int price ,int qty,String svc,int discount){
        this.clothType=clothType;
        this.qty=qty;
        this.price=price;
		this.svcType=svc;
		this.discount=discount;


    }

    public String getClothType(){

        return this.clothType;
    }

    public int getQty(){
        return this.qty;
    }
    public int getPrice(){
        return this.price;
    }


}
