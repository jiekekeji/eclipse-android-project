package com.jk.earn.net.bean;

public class ADSwitch extends BaseBean {

	private long id;
	private int oac;// 0开启，1为关闭

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getOac() {
		return oac;
	}

	public void setOac(int oac) {
		this.oac = oac;
	}

	@Override
	public String toString() {
		return "ADSwitch [id=" + id + ", oac=" + oac + "]";
	}
}
