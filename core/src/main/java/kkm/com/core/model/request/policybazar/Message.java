package kkm.com.core.model.request.policybazar;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message{

	@SerializedName("referralCode")
	private List<String> referralCode;

	public void setReferralCode(List<String> referralCode){
		this.referralCode = referralCode;
	}

	public List<String> getReferralCode(){
		return referralCode;
	}

	@Override
 	public String toString(){
		return 
			"Message{" + 
			"referralCode = '" + referralCode + '\'' + 
			"}";
		}
}