package kkm.com.core.model.response.m2p.transaction;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("balance")
	private Object balance;

	@SerializedName("transaction")
	private Transaction transaction;

	public void setBalance(Object balance){
		this.balance = balance;
	}

	public Object getBalance(){
		return balance;
	}

	public void setTransaction(Transaction transaction){
		this.transaction = transaction;
	}

	public Transaction getTransaction(){
		return transaction;
	}
}