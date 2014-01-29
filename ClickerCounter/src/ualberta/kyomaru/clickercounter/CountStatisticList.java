package ualberta.kyomaru.clickercounter;

import java.util.Date;

/*
 * This class provides a framework for a statistics class
 * designed to store an array of count statistics  
 * 
 */


public abstract class CountStatisticList
{

	private CountStatisticArray statistics;
	
	public CountStatisticList()
	{
		statistics = new CountStatisticArray();
	}
	
	public abstract void addCount(Date count);
	
	public CountStatisticArray getStatistics()
	{
		return statistics;
	}

	private CountStatisticArray countStatisticArray;

	public CountStatisticArray getCountStatisticArray()
	
	
	{
		return countStatisticArray;
	}

	public void setCountStatisticArray(CountStatisticArray countStatisticArray)
	
	
	{
		this.countStatisticArray = countStatisticArray;
	}
}
