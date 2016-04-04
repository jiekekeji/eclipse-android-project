package com.jk.earn.net.bean;

/**
 * 显示给用户的订单信息
 * 
 * @author Administrator
 * 
 */
public class OrderUser {

	private Long id;
	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 商品Id,话费充值为对应的充值面额
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 购买数量
	 */
	private Integer buyNum;
	/**
	 * 总价
	 */
	private float price;
	/**
	 * 兑换所需积分
	 */
	private Float point;
	/**
	 * 订单的状态
	 */
	private Integer status;

	/**
	 * 订单的时间
	 */
	private Long time;

	/**
	 * 购买该订单商品的用户的用户名
	 */
	private String userName;

	/**
	 * 充值账号
	 */
	private String account;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Float getPoint() {
		return point;
	}

	public void setPoint(Float point) {
		this.point = point;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderNum=" + orderNum + ", productId="
				+ productId + ", productNname=" + productName + ", buyNum="
				+ buyNum + ", price=" + price + ", point=" + point
				+ ", status=" + status + ", time=" + time + ", userName="
				+ userName + ", account=" + account + "]";
	}

}
