package com.jnu.yidongzuoye.data;

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

}
