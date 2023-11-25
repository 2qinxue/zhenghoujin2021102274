package com.jnu.myrecycle.data;

import java.io.Serializable;

public class Bill implements Serializable {
    protected String BillName, BillScore, BillTime;
    public String getBillName() {
        return BillName;
    }
    public String getBillScore() {
        return BillScore;
    }

    public String getBillTime() {
        return BillTime;
    }
    public Bill(String BillName, String BillScore, String BillTime) {
        this.BillName = BillName;
        this.BillScore = BillScore;
        this.BillTime = BillTime;
    }
    public void setBillName(String newBillName) {
        this.BillName = newBillName;
    }

    public void setBillScore(String newBillScore) {
        this.BillScore = newBillScore;
    }

    public void setBillTime(String newBillTime) {
        this.BillTime = newBillTime;
    }
}
