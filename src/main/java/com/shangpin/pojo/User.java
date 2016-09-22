package com.shangpin.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by wenyu on 2016/9/22.
 */
@Getter
@Setter
public class User implements Serializable{
    private static final long serialVersionUID = 2120869894112984147L;
    private String id;
    private String name;
    private String age;
}
