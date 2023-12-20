package com.jnu.yidongzuoye.data;

import java.io.Serializable;

public class personality_information implements Serializable {
    public String name;
    public int imageresource;
    public personality_information(String name, int imageresource) {
        this.name = name;
        this.imageresource = imageresource;
    }
    public String getname() {
        return name;
    }
    public int getimageresource() {
        return imageresource;
    }
}
