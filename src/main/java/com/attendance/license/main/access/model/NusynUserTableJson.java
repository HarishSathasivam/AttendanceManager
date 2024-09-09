package com.nusyn.license.main.access.model;

import java.util.ArrayList;
import java.util.List;

public class NusynUserTableJson {

	int draw = 1;
	int iTotalRecords;

	int iTotalDisplayRecords;

	String sEcho;

	String sColumns;

	List<NusynUserJson> data = new ArrayList<NusynUserJson>();
	
	String errorMessage = "";
	
	public void addUser(NusynUserJson userJson)
	{
		if(data == null)
		{
			data = new ArrayList<NusynUserJson>();
		}
		data.add(userJson);
	}
	
	//Getters and Setters
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

	public List<NusynUserJson> getData() {
		return data;
	}

	public void setData(List<NusynUserJson> data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
