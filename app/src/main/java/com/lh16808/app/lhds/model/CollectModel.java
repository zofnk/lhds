package com.lh16808.app.lhds.model;

import java.io.Serializable;

public class CollectModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9170076849605121159L;
	String sid;
	String bt;
	String lj;
	public CollectModel(String sid, String bt, String lj) {
		super();
		this.sid = sid;
		this.bt = bt;
		this.lj = lj;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getLj() {
		return lj;
	}
	public void setLj(String lj) {
		this.lj = lj;
	}
}
