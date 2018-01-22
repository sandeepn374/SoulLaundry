package com.soullaundry;

import java.io.*;
import java.util.*;

/**
 * Created by kshravi on 01/11/2017 AD.
 */

public class User implements Serializable {

    public String name;
   // public String email;
    public String billNumber;
    public String ph;
   public ArrayList<BillDetails> billDetailsArrayList;
   
    public int total;
    public int due;
    public int discount;
    public String deliveryDate;
    public String pickUpDate;
    public String paymentMode="";


    public User() {
    }

    public User(String name, String ph, ArrayList<BillDetails> billDetailsArrayList, String bill, int total, int due, int discount,String del,String pick) {
        this.ph=ph;
        this.name = name;
this.billDetailsArrayList=billDetailsArrayList;

        this.billNumber=bill;
        this.total=total;
        this.due=due;
        this.discount=discount;
        this.deliveryDate=del;
        this.pickUpDate=pick;
    }
}
