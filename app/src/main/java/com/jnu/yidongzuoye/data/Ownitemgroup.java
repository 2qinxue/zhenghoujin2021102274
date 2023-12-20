package com.jnu.yidongzuoye.data;

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

}

