package com.jnu.myrecycle.data;

public class Ownitemgroup {
    protected String buttonname;

    protected int button_position;
    public Ownitemgroup(String buttonname,int position) {
        this.buttonname = buttonname;
        button_position=position;
    }
    public String getButtonname() {
        return buttonname;
    }

    public int Get_id() {
        return button_position;
    }
}

