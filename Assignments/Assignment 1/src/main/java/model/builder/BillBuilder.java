package model.builder;

import model.Bill;

public class BillBuilder {

    private Bill bill;

    public BillBuilder() {
        bill = new Bill();
    }

    public BillBuilder setBillType(String billType){
        bill.setBillType(billType);
        return this;
    }

    public BillBuilder setBillAmount(Double billAmount){
        bill.setBillAmount(billAmount);
        return this;
    }

    public Bill build(){
        return bill;
    }
}
