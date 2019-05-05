package com.beehive.rocketmq.producer.utils;


import java.io.Serializable;

public class RequestResult
  implements Serializable
{
  private static final long serialVersionUID = 4838374625860902145L;
  protected int code = 200;
  protected String desc;
  protected Object data;
  public static final int OK = 200;
  public static final int NEED_AUTH = 301;
  public static final int PERMISSION_FORBIDDEN = 200;
  public static final int ERROR = 300;
  public static final int UNKNOWN_ERROR = 900;
  
  public RequestResult() {}
  
  public RequestResult(int code)
  {
    this.code = code;
  }
  
  public RequestResult(int code, String desc)
  {
    this.code = code;
    this.desc = desc;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public void setDesc(String desc)
  {
    this.desc = desc;
  }
  
  public Object getData()
  {
    return this.data;
  }
  
  public void setData(Object data)
  {
    this.data = data;
  }
}
