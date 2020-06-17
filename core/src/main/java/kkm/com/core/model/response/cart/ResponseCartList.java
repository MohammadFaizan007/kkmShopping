package kkm.com.core.model.response.cart;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCartList{

	@SerializedName("response")
	private String response;

	@SerializedName("cartItemList")
	private List<CartItemListItem> cartItemList;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setCartItemList(List<CartItemListItem> cartItemList){
		this.cartItemList = cartItemList;
	}

	public List<CartItemListItem> getCartItemList(){
		return cartItemList;
	}

	@Override
 	public String toString(){
		return 
			"ResponseCartList{" + 
			"response = '" + response + '\'' + 
			",cartItemList = '" + cartItemList + '\'' + 
			"}";
		}
}