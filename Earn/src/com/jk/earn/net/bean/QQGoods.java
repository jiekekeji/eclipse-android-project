package com.jk.earn.net.bean;

import java.io.Serializable;

public class QQGoods implements Serializable {

	/**
	 * 产品ID 充值时传给服务器
	 */
	private String i;

	/**
	 * 产品名称
	 */
	private String b;

	/**
	 * 面值
	 */
	private float f;

	/**
	 * 单位
	 */
	private String u;

	/**
	 * 小分类 移动是100，联通是101，电信是102，固话是103 QQ业务是200，Q币Q点是201游戏是大等于300
	 */
	private int t;

	/**
	 * 渠道ID
	 */
	private int c;

	/**
	 * 省份ID
	 */
	private int v;

	/**
	 * 限购数量
	 */
	private String m;

	/**
	 * 商品大类 话费业务是1，QQ业务是2，游戏业务是3
	 */
	private int q;

	/**
	 * 库存 大于0均表示库存充足
	 */
	private int s;

	/**
	 * 排序
	 */
	private int o;

	/**
	 * 豪华版进价旗舰版是p17
	 */
	private float p16;

	/**
	 * 兑换积分
	 */
	private long point;

	/**
	 * 产品图片
	 */
	private String imgurl;

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public float getF() {
		return f;
	}

	public void setF(float f) {
		this.f = f;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public int getO() {
		return o;
	}

	public void setO(int o) {
		this.o = o;
	}

	public float getP16() {
		return p16;
	}

	public void setP16(float p16) {
		this.p16 = p16;
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@Override
	public String toString() {
		return "Goods [i=" + i + ", b=" + b + ", f=" + f + ", u=" + u + ", t=" + t + ", c=" + c + ", v=" + v + ", m="
				+ m + ", q=" + q + ", s=" + s + ", o=" + o + ", p16=" + p16 + ", point=" + point + ", imgurl=" + imgurl
				+ "]";
	}

}
