package com.aiyi.core.beans;

import com.aiyi.core.exception.ServiceInvokeException;
import com.aiyi.core.sql.where.C;

import javax.validation.ValidationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WherePrams {

	private String pram;
	
	private String limit;
	
	private String orderBy;

	private List<Serializable> parms = new ArrayList<Serializable>();
	
	private List<Serializable> limitParms = new ArrayList<Serializable>();
	
//	private WherePrams(String prams){
//		pram = " where " + prams;
//	}
//	
	public WherePrams(){
		this.pram = " where 1=1 ";
	}
	
	public WherePrams(String file, String where, Serializable value){
		
		if(null == file && null == where && value == where){
			return;
		}
		
		if (null == value) {
			
			if (where.equals("=")) {
				where = " is";
			}else{
				where = " is not ";
			}
			this.pram = " where " + file + where + "null";
		}else if(value instanceof Boolean){
			if ("like".equals(where)) {
				this.pram = " where " + file + where + " %" + value + "% ";
			}else{
				this.pram = " where " + file + where + " " + value + " ";
			}
		}else{
			if (value instanceof String){
				value = ((String) value).replace("'", "''");
			}
			if ("like".equals(where)) {
				this.pram = " where " + file + where + " '%" + value + "%'";
			}else{
				this.pram = " where " + file + where + " '" + value + "'";
			}
		}
		
		
		
		
		
		
		
		
//		this.pram = " where " + file + where + "?";
//		if("like".equals(where)){
//			parms.add("%" + value + "%");
//		}else{
//			parms.add(value);
//		}
	}
	
	/**
	 * and条件
	 * @param pram
	 * @return
	 *//*
	public WherePrams and(String pram){
		this.pram += " and " + pram;
		return this;
	}*/


	public WherePrams and(String file, String where, List<Serializable> values){
		if (null == values || values.isEmpty()){
			return this;
		}
		StringBuffer valueBuffer = getListValueParams(where, values);
		return and(file, where, valueBuffer.toString());
	}

	/**
	 * and条件
	 * @param file
	 * @param where
	 * @param value
	 * @return
	 */
	public WherePrams and(String file, String where, Serializable value){
		if (null == value) {
			if (where.trim().equals("=")) {
				where = " is ";
			} else {
				where = " is not ";
			}
			this.pram += " and " + file + where + "null";
		} else if(value instanceof Boolean) {
			if ("like".equals(where)) {
				this.pram += " and " + file + " " + where + " %" + value + "% ";
			}else{
				this.pram += " and " + file + " " + where + " " + value + " ";
			}
		}else{
			if (value instanceof String){
				value = ((String) value).replace("'", "''");
			}
			if ("like".equals(where)) {
				this.pram += " and " + file + " " + where + " '%" + value + "%'";
			}else if ("in".equalsIgnoreCase(where)){
				this.pram += " and " + file + " " + where + " " + value + "";
			}else{
				this.pram += " and " + file + " " + where + " '" + value + "'";
			}
		}
		
		
		
//		if("like".equals(where)){
//			this.pram = " where " + file + where + "%" + value + "%";
////			this.pram += " and " + file + " " + where + " ?";
////			parms.add("%" + value + "%");
//		}else{
//			this.pram = " where " + file + where + value;
////			this.pram += " and " + file + " " + where + " ?";
////			parms.add(value);
//		}
		
		return this;
	}
	public WherePrams and(String file, C c, Serializable value){
		String where = C.getSqlWhere(c);
		return and(file, where, value);
	}

	public WherePrams and(String file, C c, List<Serializable> value){
		String where = C.getSqlWhere(c);
		return and(file, where, value);
	}
	
/*	*//**
	 * or条件
	 * @param pram
	 * @return
	 *//*
	public WherePrams or(String pram){
		this.pram += " or " + pram;
		return this;
	}*/
	private StringBuffer getListValueParams(String where, List<Serializable> values){
		if (!where.equalsIgnoreCase("in")){
			throw new ValidationException("This value cannot be a collection type");
		}
		StringBuffer valueBuffer = new StringBuffer("(");
		for(int i = 0; i < values.size(); i++){
			valueBuffer.append("'").append(values.get(i)).append("'");
			if (i < values.size() - 1){
				valueBuffer.append(", ");
			}else{
				valueBuffer.append(")");
			}
		}
		return valueBuffer;
	}

	public WherePrams or(String file, String where, List<Serializable> values){
		if (null == values || values.isEmpty()){
			return this;
		}
		StringBuffer valueBuffer = getListValueParams(where, values);
		return or(file, where, valueBuffer.toString());
	}

	/**
	 * or条件
	 * @param file
	 * @param where
	 * @param value
	 * @return
	 */
	public WherePrams or(String file, String where, Serializable value){
		
		if (null == value) {
			if (where.equals("=")) {
				where = " is";
			} else {
				where = " is not ";
			}
			this.pram = " or " + file + " " + where + "null";
		} else {
			if (value instanceof String){
				value = ((String) value).replace("'", "''");
			}
			if ("like".equals(where)) {
				this.pram += " or " + file + " " + where + " '%" + value + "%'";
			}else if ("in".equalsIgnoreCase(where)){
				this.pram += " and " + file + " " + where + " " + value + "";
			}else{
				this.pram += " or " + file + " " + where + " '" + value + "'";
			}
		}
		
		
//		if("like".equals(where)){
////			this.pram += " or " + file + " " + where + " ?";
//			this.pram = " or " + file + where + "%" + value + "%";
////			parms.add("%" + value + "%");
//		}else{
//			this.pram = " or " + file + where + value;
////			this.pram += " or " + file + " " +  where + " ?";
////			parms.add(value);
//		}
		return this;
	}
	public WherePrams or(String file, C c, Serializable value){
		String where = C.getSqlWhere(c);
		return or(file, where, value);
	}

	public WherePrams or(String file, C c, List<Serializable> value){
		String where = C.getSqlWhere(c);
		return or(file, where, value);
	}
	
	public WherePrams limit(int startNum, int length) {
//		this.limit = " limit ? , ? ";
		this.limit = " limit " + startNum + " , " + length + " ";
//		limitParms.add(startNum);
//		limitParms.add(length);
		return this;
	}

	public String getLimit() {
		return limit;
	}

	public WherePrams orderBy(String order){
		if(this.orderBy != null){
			this.orderBy += "," + order;
		}else{
			this.orderBy = order;
		}
		return this;
	}

	@Override
	public String toString() {
		return "WherePrams [pram=" + pram + "]";
	}
	
	/**
	 * 获取prams
	 * @return
	 */
	public String getWherePrams(){
		String p = "";
		p += null == this.pram ? "" : this.pram;
		p += null == this.orderBy ? "" : (" order by " + this.orderBy);
		p += null == this.limit ? "" : this.limit;
		return p;
	}
	
	public Serializable[] listParmsByFmt(){
		parms.addAll(limitParms);
		return parms.toArray(new Serializable[parms.size()]);
	}
	
	public Serializable[] listParms(){
		int length = getWherePrams().indexOf("?");
		if(-1 == length){
			return new Serializable[0];
		}
//		if(null != orderBy){
//			parms.add(orderBy);
//		}
		parms.addAll(limitParms);
		return parms.toArray(new Serializable[parms.size()]);
	}

	public WherePrams and(String sql) {
		this.pram += " and " + sql;
		return this;
	}
}
