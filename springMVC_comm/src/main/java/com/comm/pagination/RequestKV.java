/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.comm.pagination;

import java.io.Serializable;

public class RequestKV implements Serializable {
    private static final long serialVersionUID = -1359671272627376899L;
    private String name;
    private String value;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RequestKV(String name, String value) {
        this.name = name;
        this.value = value;
    }
}