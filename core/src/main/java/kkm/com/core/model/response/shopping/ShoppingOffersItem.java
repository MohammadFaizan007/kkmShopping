package kkm.com.core.model.response.shopping;

import com.google.gson.annotations.SerializedName;

public class ShoppingOffersItem{

	@SerializedName("offerURL")
	private String offerURL;

	@SerializedName("offerOn")
	private String offerOn;

	@SerializedName("smallimages")
	private String smallimages;

	@SerializedName("imageURL")
	private String imageURL;

	@SerializedName("offerCategory")
	private String offerCategory;

	public void setOfferURL(String offerURL){
		this.offerURL = offerURL;
	}

	public String getOfferURL(){
		return offerURL;
	}

	public void setOfferOn(String offerOn){
		this.offerOn = offerOn;
	}

	public String getOfferOn(){
		return offerOn;
	}

	public void setSmallimages(String smallimages){
		this.smallimages = smallimages;
	}

	public String getSmallimages(){
		return smallimages;
	}

	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}

	public String getImageURL(){
		return imageURL;
	}

	public void setOfferCategory(String offerCategory){
		this.offerCategory = offerCategory;
	}

	public String getOfferCategory(){
		return offerCategory;
	}

	@Override
 	public String toString(){
		return 
			"ShoppingOffersItem{" + 
			"offerURL = '" + offerURL + '\'' + 
			",offerOn = '" + offerOn + '\'' + 
			",smallimages = '" + smallimages + '\'' + 
			",imageURL = '" + imageURL + '\'' + 
			",offerCategory = '" + offerCategory + '\'' + 
			"}";
		}
}