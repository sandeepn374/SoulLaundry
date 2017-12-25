package com.soullaundry;

/**
 * Created by kshravi on 05/11/2017 AD.
 */

public class BillDetails {


    String clothType;

    String qty;
    String price;

    public BillDetails(){}
    public BillDetails(String clothType, String price ,String qty){
        this.clothType=clothType;
        this.qty=qty;
        this.price=price;


    }

    public String getClothType(){

        return this.clothType;
    }

    public String getQty(){
        return this.qty;
    }
    public String getPrice(){
        return this.price;
    }


}
