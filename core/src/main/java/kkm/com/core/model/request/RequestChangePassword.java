package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestChangePassword{

	@SerializedName("NewPassword")
	private String newPassword;

	@SerializedName("UpdatedBy")
	private String updatedBy;

	@SerializedName("OldPassword")
	private String oldPassword;

	@SerializedName("Mobile")
	private String mobile;

	@SerializedName("Formname")
	private String formname;

	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}

	public String getNewPassword(){
		return newPassword;
	}

	public void setUpdatedBy(String updatedBy){
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy(){
		return updatedBy;
	}

	public void setOldPassword(String oldPassword){
		this.oldPassword = oldPassword;
	}

	public String getOldPassword(){
		return oldPassword;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setFormname(String formname){
		this.formname = formname;
	}

	public String getFormname(){
		return formname;
	}

	@Override
 	public String toString(){
		return 
			"RequestChangePassword{" + 
			"newPassword = '" + newPassword + '\'' + 
			",updatedBy = '" + updatedBy + '\'' + 
			",oldPassword = '" + oldPassword + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",formname = '" + formname + '\'' + 
			"}";
		}
}