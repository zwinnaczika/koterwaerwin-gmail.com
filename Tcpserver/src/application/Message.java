package application;

import java.io.Serializable;
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2133421L;
	private String tripDistance;
	private String fuelEff;
	private String fuelType;
	private String result;
	
	public Message(String tripDistance, String fuelEff, String fuelType)
	{
		this.tripDistance = tripDistance;
		this.fuelEff = fuelEff;
		this.fuelType = fuelType;
	}
	public void setResult(String Result)
	{
		this.result = Result;
	}
	public String getTripDistance()
	{
		return tripDistance;
	}
	public String getFuelEff()
	{
		return fuelEff;
	}
	public String getFuelType()
	{
		return fuelType;
	}
	public String getResult()
	{
		return result;
	}
}
