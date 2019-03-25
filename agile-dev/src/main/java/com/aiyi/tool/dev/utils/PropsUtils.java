package com.aiyi.tool.dev.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * 配置文件操作类
 * @author 71934
 *
 */
public class PropsUtils {

  private static Properties prop = null;
  
  /**
   * 获得配置值
   * @param key
   * @return
   */
  public static String get(String key) {
    String property = System.getProperty("user.dir");
    FileInputStream fileInputStream = null;
    try {
      if(null == prop) {
        prop = new Properties();
        fileInputStream = new FileInputStream(property + "/conf.properties");
        prop.load(fileInputStream);
      }
     return prop.getProperty(key);
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        if (fileInputStream != null) {
          fileInputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
  
  public static void set(String key, String value) {
    String property = System.getProperty("user.dir");
    FileOutputStream out = null;
    if (null == prop) {
      get("default");
    }
    prop.setProperty(key, value);
    try {
      out = new FileOutputStream(property);
      prop.store(out, "账号");
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args) {
    System.out.println(get("bc.server.address"));
  }
}
