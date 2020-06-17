package kkm.com.core.model.response.jioPrepaid;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Addinfo{

	@SerializedName("totalRecords")
	private String totalRecords;

	@SerializedName("planOffering")
	private List<PlanOfferingItem> planOffering;

	@SerializedName("customerFacingServices")
	private List<CustomerFacingServicesItem> customerFacingServices;

	@SerializedName("STATE")
	private String sTATE;

	@SerializedName("circleId")
	private String circleId;

	public void setTotalRecords(String totalRecords){
		this.totalRecords = totalRecords;
	}

	public String getTotalRecords(){
		return totalRecords;
	}

	public void setPlanOffering(List<PlanOfferingItem> planOffering){
		this.planOffering = planOffering;
	}

	public List<PlanOfferingItem> getPlanOffering(){
		return planOffering;
	}

	public void setCustomerFacingServices(List<CustomerFacingServicesItem> customerFacingServices){
		this.customerFacingServices = customerFacingServices;
	}

	public List<CustomerFacingServicesItem> getCustomerFacingServices(){
		return customerFacingServices;
	}

	public void setSTATE(String sTATE){
		this.sTATE = sTATE;
	}

	public String getSTATE(){
		return sTATE;
	}

	public void setCircleId(String circleId){
		this.circleId = circleId;
	}

	public String getCircleId(){
		return circleId;
	}

	@Override
 	public String toString(){
		return 
			"Addinfo{" + 
			"totalRecords = '" + totalRecords + '\'' + 
			",planOffering = '" + planOffering + '\'' + 
			",customerFacingServices = '" + customerFacingServices + '\'' + 
			",sTATE = '" + sTATE + '\'' + 
			",circleId = '" + circleId + '\'' + 
			"}";
		}
}