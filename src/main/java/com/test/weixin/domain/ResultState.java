package com.test.weixin.domain;

import java.io.Serializable;

/** 
 * 微信API返回状态 
 *  
 * @author phil 
 * @date 2017年7月2日 
 * 
 */  
public class ResultState implements Serializable {  
  
    /** 
     *  
     */  
    private static final long serialVersionUID = 1692432930341768342L;  
  
    private int errcode; // 状态  
      
    private String errmsg; //信息  
  
    public int getErrcode() {  
        return errcode;  
    }  
  
    public void setErrcode(int errcode) {  
        this.errcode = errcode;  
    }  
  
    public String getErrmsg() {  
        return errmsg;  
    }  
  
    public void setErrmsg(String errmsg) {  
        this.errmsg = errmsg;  
    }  
}