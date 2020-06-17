package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ProductSizeItem{

	@SerializedName("courierPrice")
	private String courierPrice;

	@SerializedName("sizeID")
	private String sizeID;

	@SerializedName("collectBySeniors")
	private String collectBySeniors;

	@SerializedName("unitName")
	private String unitName;

	@SerializedName("collectByHo")
	private String collectByHo;

	@SerializedName("sizeName")
	private String sizeName;

	@SerializedName("bv")
	private String bv;

	@SerializedName("productOldPrice")
	private String productOldPrice;

	@SerializedName("unitID")
	private String unitID;

	@SerializedName("collectByCourier")
	private String collectByCourier;

	@SerializedName("productQty")
	private String productQty;

	@SerializedName("pk_ProductDetailId")
	private String pkProductDetailId;

	@SerializedName("collectByDistrict")
	private String collectByDistrict;

	@SerializedName("productPrice")
	private String productPrice;

	public void setCourierPrice(String courierPrice){
		this.courierPrice = courierPrice;
	}

	public String getCourierPrice(){
		return courierPrice;
	}

	public void setSizeID(String sizeID){
		this.sizeID = sizeID;
	}

	public String getSizeID(){
		return sizeID;
	}

	public void setCollectBySeniors(String collectBySeniors){
		this.collectBySeniors = collectBySeniors;
	}

	public String getCollectBySeniors(){
		return collectBySeniors;
	}

	public void setUnitName(String unitName){
		this.unitName = unitName;
	}

	public String getUnitName(){
		return unitName;
	}

	public void setCollectByHo(String collectByHo){
		this.collectByHo = collectByHo;
	}

	public String getCollectByHo(){
		return collectByHo;
	}

	public void setSizeName(String sizeName){
		this.sizeName = sizeName;
	}

	public String getSizeName(){
		return sizeName;
	}

	public void setBv(String bv){
		this.bv = bv;
	}

	public String getBv(){
		return bv;
	}

	public void setProductOldPrice(String productOldPrice){
		this.productOldPrice = productOldPrice;
	}

	public String getProductOldPrice(){
		return productOldPrice;
	}

	public void setUnitID(String unitID){
		this.unitID = unitID;
	}

	public String getUnitID(){
		return unitID;
	}

	public void setCollectByCourier(String collectByCourier){
		this.collectByCourier = collectByCourier;
	}

	public String getCollectByCourier(){
		return collectByCourier;
	}

	public void setProductQty(String productQty){
		this.productQty = productQty;
	}

	public String getProductQty(){
		return productQty;
	}

	public void setPkProductDetailId(String pkProductDetailId){
		this.pkProductDetailId = pkProductDetailId;
	}

	public String getPkProductDetailId(){
		return pkProductDetailId;
	}

	public void setCollectByDistrict(String collectByDistrict){
		this.collectByDistrict = collectByDistrict;
	}

	public String getCollectByDistrict(){
		return collectByDistrict;
	}

	public void setProductPrice(String productPrice){
		this.productPrice = productPrice;
	}

	public String getProductPrice(){
		return productPrice;
	}

	@Override
 	public String toString(){
		return 
			"ProductSizeItem{" + 
			"courierPrice = '" + courierPrice + '\'' + 
			",sizeID = '" + sizeID + '\'' + 
			",collectBySeniors = '" + collectBySeniors + '\'' + 
			",unitName = '" + unitName + '\'' + 
			",collectByHo = '" + collectByHo + '\'' + 
			",sizeName = '" + sizeName + '\'' + 
			",bv = '" + bv + '\'' + 
			",productOldPrice = '" + productOldPrice + '\'' + 
			",unitID = '" + unitID + '\'' + 
			",collectByCourier = '" + collectByCourier + '\'' + 
			",productQty = '" + productQty + '\'' + 
			",pk_ProductDetailId = '" + pkProductDetailId + '\'' + 
			",collectByDistrict = '" + collectByDistrict + '\'' + 
			",productPrice = '" + productPrice + '\'' + 
			"}";
		}
}