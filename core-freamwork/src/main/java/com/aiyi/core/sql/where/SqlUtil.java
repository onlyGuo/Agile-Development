package com.aiyi.core.sql.where;

import com.aiyi.core.annotation.po.*;
import com.aiyi.core.beans.Po;
import com.aiyi.core.beans.Pram;
import org.apache.http.client.utils.DateUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

/**
 * Sql���ɹ�����
 * @author ��ʤ��
 * @time 2016��5��3������3:39:51
 * @email 719348277@qq.com
 * @param <T> Ҫ����Sql��ʵ����
 */
public class SqlUtil<T extends Po> {
	
	
	private static Map<String, PropertyDescriptor> propertyDescriptorMap = new HashMap<>();

    /**
     * @param field   字段名称
     * @param c       实体类Class
     * @Description : 获得一个实体类指定字段的内省对象
     * @Creation Date : 2018/5/16 下午4:39
     * @Author : 郭胜凯
     */
	private static PropertyDescriptor getPropertyDescriptor(String field, Class c){
	  String key = field + "_" + c.getName();
      PropertyDescriptor propertyDescriptor = propertyDescriptorMap.get(key);
      if (null == propertyDescriptor){
        try {
          propertyDescriptor = new PropertyDescriptor(field, c);
          propertyDescriptorMap.put(key, propertyDescriptor);
        }catch (IntrospectionException e){
          throw new RuntimeException("解析实体类异常", e);
        }
      }
      return propertyDescriptor;
    }

    /**
     * @param t          实体类Class
     * @param fieldName  字段名称
     * @Description : 通过实体类的Class获得这个实体类型指定的字段实例.
     * @Creation Date : 2018/5/16 下午5:34
     * @Author : 郭胜凯
     */
	public Field getField(Class<?> t, String fieldName){
		Field[] fields = t.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

    /**
     * @param field   要获取sql参数的实体类字段
     * @param o       该字段对应实体类的实例
     * @Description : 获取一个实体类实例中指定字段的Sql映射参数
     * @Creation Date : 2018/5/16 下午4:56
     * @Author : 郭胜凯
     */
	private static Pram getFieldParam(Field field, Object o, boolean as){
      // 获得实体类中字段名和数据库中字段名
      String fName = field.getName();
      String fieldName = "";
      if (field.isAnnotationPresent(FieldName.class)) {
        fieldName = field.getAnnotation(FieldName.class).name();
      }else{
        fieldName = toTableString(fName);
      }

      //获得实体类中对应的值
      PropertyDescriptor descriptor = getPropertyDescriptor(fName, o.getClass());
      Method readMethod = descriptor.getReadMethod();
      if (as){
        try {
            Object invoke = readMethod.invoke(o);
			return new Pram(fieldName, field, readMethod.invoke(o));
        }catch (IllegalAccessException|InvocationTargetException e){
          throw new RuntimeException("解析实体类异常", e);
        }
      }else{
        try {
          return new Pram(fieldName, field, readMethod.invoke(o));
        }catch (IllegalAccessException|InvocationTargetException e){
          throw new RuntimeException("解析实体类异常", e);
        }
      }

    }

    /**
     * @param obj         实体类
     * @param excludeId   是否排除ID字段
     * @Description : 获得指定实体类的字段Sql映射的静态方法, 最原始公共方法
     * @Creation Date : 2018/5/16 下午5:15
     * @Author : 郭胜凯
     */
    public static List<Pram> getParamListCommon(Object obj, boolean excludeId){
      List<Pram> list = new ArrayList<>();
      Field[] declaredFields = obj.getClass().getDeclaredFields();
      for (Field field : declaredFields) {
        if (field.isAnnotationPresent(TempField.class)){
          continue;
        }
        if (excludeId){
          if (field.getName().equalsIgnoreCase("ID") || field.isAnnotationPresent(ID.class)){
            continue;
          }
        }
        list.add(getFieldParam(field, obj, !excludeId));
      }
	  return list;
    }

    /**
     * @param obj  实体类
     * @Description : 获得指定实体类的字段Sql映射的静态方法, 最原始公共方法的复写方法.默认不排除ID字段
     * @Creation Date : 2018/5/16 下午5:15
     * @Author : 郭胜凯
     */
    public static List<Pram> getParamListCommon(Object obj){
	  return getParamListCommon(obj, false);
    }

    /**
     * @param po   实体类
     * @Description : 获得指定实体类的字段Sql映射的静态方法, 不含ID
     * @Creation Date : 2018/5/16 下午5:15
     * @Author : 郭胜凯
     */
	public static<T extends Po> List<Pram> getPramListofStatic(Po po){
		return getParamListCommon(po, true);
	}

    /**
     * @param po  实体类Class
     * @Description : 通过Class获得这个类型实体类默认的Sql映射, 不含ID
     * @Creation Date : 2018/5/16 下午5:13
     * @Author : 郭胜凯
     */
	public List<Pram> getPramList(Class<T> po){
      try {
        T t = po.newInstance();
        return getParamListCommon(t, true);
      } catch (InstantiationException|IllegalAccessException e) {
        throw new RuntimeException("解析实体类异常", e);
      }
    }

    /**
     * @param po  实体类
     * @Description : 获得指定实体类的字段Sql映射, 不含ID
     * @Creation Date : 2018/5/16 下午5:13
     * @Author : 郭胜凯
     */
	public List<Pram> getPramList(T po){
      return getParamListCommon(po, true);
	}


    /**
     * @param po  实体类
     * @Description : 获得指定实体类的字段Sql映射
     * @Creation Date : 2018/5/16 下午5:13
     * @Author : 郭胜凯
     */
	public List<Pram> getPramListOfSelect(Class<T> po){
      try {
        T t = po.newInstance();
        return getParamListCommon(t);
      } catch (InstantiationException|IllegalAccessException e) {
        throw new RuntimeException("解析实体类异常", e);
      }
	}


    /**
     * @param po  实体类型Class
     * @Description : 获得一个实体类型对应的表名
     * @Creation Date : 2018/5/16 下午5:21
     * @Author : 郭胜凯
     */
	public String getTableName(Class<T> po){
		if(po.isAnnotationPresent(TableName.class)){
			return po.getAnnotation(TableName.class).name();
		}else{
			String tName = toTableString(po.getSimpleName());
			String poName = tName.substring(tName.length() - 2, tName.length());
			if("po".equals(poName)){
				tName = tName.substring(0,tName.length() - 3);
			}
			return tName;
		}
	}
	
	/**
	 * 获得主键名称
	 * @param po
	 * @return
	 */
	public String getPkName(Class<T> po) {
		Field[] fields = po.getDeclaredFields();
		boolean isId = false;
		for (Field field : fields) {
			if(field.getName().equalsIgnoreCase("id")) {
				isId = true;
			}
			ID id = field.getAnnotation(ID.class);
			if(null != id) {
				FieldName fieldName = field.getAnnotation(FieldName.class);
				if(null != fieldName) {
					return fieldName.name();
				}
				return toTableString(field.getName());
			}
		}
		if(isId) {
			return "id";
		}
		return null;
	}

	public String getIdName(Class<T> po){
		Field[] fields = po.getDeclaredFields();
		boolean isId = false;
		for (Field field : fields) {
			if(field.getName().equalsIgnoreCase("id")) {
				isId = true;
			}
			ID id = field.getAnnotation(ID.class);
			if(null != id) {
				return field.getName();
			}
		}
		if(isId) {
			return "id";
		}
		return null;
	}


    /**
     * @param po        实体类
     * @param fileName  实体类中的字段名
     * @Description :   获得一个实体类中指定字段的值
     * @Creation Date : 2018/5/16 下午5:26
     * @Author : 郭胜凯
     */
	public Serializable getFileValue(Po po, String fileName){
      PropertyDescriptor propertyDescriptor = getPropertyDescriptor(fileName, po.getClass());
      Method readMethod = propertyDescriptor.getReadMethod();
      try {
        Object invoke = readMethod.invoke(po);
        return null == invoke ? null : (Serializable) invoke;
      } catch (IllegalAccessException|InvocationTargetException e) {
        throw new RuntimeException("解析实体类异常", e);
      }
	}


    /**
     * @param po          实体类对象实例
     * @param fileName    字段名称
     * @param fileValue   字段值
     * @Description :   给某个实例这只指定字段的值
     * @Creation Date : 2018/5/16 下午5:36
     * @Author : 郭胜凯
     */
	public static boolean setFileValue(Po po, String fileName, Serializable fileValue){
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(fileName, po.getClass());
        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (null == writeMethod) return false;
        try {
			String typeName = propertyDescriptor.getPropertyType().getTypeName();
			if (typeName.equals("int") || typeName.equals(Integer.class.toString())){
				writeMethod.invoke(po, Integer.valueOf(String.valueOf(fileValue)));
			}else if(typeName.equals("long") || typeName.equals(Long.class.toString())){
				writeMethod.invoke(po, Long.valueOf(String.valueOf(fileValue)));
			}else if(typeName.equals("boolean") || typeName.equals(Boolean.class.toString())){
				boolean v = false;
				if (null != fileValue && !"0".equalsIgnoreCase(fileValue.toString()) &&
						!"N".equalsIgnoreCase(fileValue.toString())){
					v = true;
				}
				writeMethod.invoke(po, v);
			}else if (typeName.equals("String") || typeName.equals(String.class.getName())){
				String v = fileValue.toString();
				if (fileValue instanceof Clob){
					Clob clob = (Clob)fileValue;
					StringBuffer sb = new StringBuffer();
					try (Reader characterStream = clob.getCharacterStream()) {
						char[] chars = new char[4096];
						for(int i = characterStream.read(chars); i > 0; i = characterStream.read(chars)){
							sb.append(chars, 0, i);
						}
					}catch (SQLException| IOException e){
						throw new RuntimeException(e);
					}
					v = sb.toString();
				}
				writeMethod.invoke(po, v);
			}else{
				writeMethod.invoke(po, fileValue);
			}
          return true;
        } catch (IllegalAccessException|InvocationTargetException e) {
          throw new RuntimeException("解析实体类异常", e);
        }
	}

	public static boolean setFileValueByOracle(Po po, String fileName, Serializable fileValue){
	    if (null == fileName){
	        return true;
        }
        Field[] declaredFields = po.getClass().getDeclaredFields();
        for(Field field: declaredFields){
            if (field.getName().toUpperCase().equalsIgnoreCase(fileName)){
            	if (field.getAnnotation(DateTime.class) != null){
					return setFileValue(po, field.getName(), new Date(Long.valueOf(fileValue.toString())));
				}
                return setFileValue(po, field.getName(), fileValue);
            }
        }
        return false;
    }


    /**
     * @param text  驼峰标识的文本
     * @Description : 将一段驼峰标识的文本转换成下划线格式
     * @Creation Date : 2018/5/16 下午5:30
     * @Author : 郭胜凯
     */
	public static String toTableString(String text){
		String tName = text.substring(0, 1).toLowerCase();
		for(int i = 1; i < text.length(); i++){
			if(!Character.isLowerCase(text.charAt(i))){
				tName += "_";
			}
			tName += text.substring(i, i + 1);
		}
		return tName.toLowerCase();
	}

	public String getTableNameByClazz(Class<? extends Po> po) {
		if(po.isAnnotationPresent(TableName.class)){
			return po.getAnnotation(TableName.class).name();
		}else{
			String tName = toTableString(po.getSimpleName());
			if("po".equals(tName.substring(tName.length() - 3, tName.length() - 1))){
				tName = tName.substring(0,tName.length() - 3);
			}
			return tName;
		}
	}

	public String getIdType(Class<T> po) {
		Field[] fields = po.getDeclaredFields();
		Field tid = null;
		for (Field field : fields) {
			if(field.getName().equalsIgnoreCase("id")) {
				tid = field;
			}
			ID id = field.getAnnotation(ID.class);
			if(null != id) {
				tid = field;
				break;
			}
		}
		if(null == tid) {
			return null;
		}
		return tid.getGenericType().getTypeName();
	}
	
}
