package kkm.com.core.model.response.shopping;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseShoppingOffers{

	@SerializedName("response")
	private String response;

	@SerializedName("shoppingOffers")
	private List<ShoppingOffersItem> shoppingOffers;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setShoppingOffers(List<ShoppingOffersItem> shoppingOffers){
		this.shoppingOffers = shoppingOffers;
	}

	public List<ShoppingOffersItem> getShoppingOffers(){
		return shoppingOffers;
	}

	@Override
 	public String toString(){
		return 
			"ResponseShoppingOffers{" + 
			"response = '" + response + '\'' + 
			",shoppingOffers = '" + shoppingOffers + '\'' + 
			"}";
		}
}