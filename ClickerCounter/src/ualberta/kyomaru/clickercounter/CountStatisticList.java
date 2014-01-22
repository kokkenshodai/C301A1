package ualberta.kyomaru.clickercounter;

import java.util.Date;


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
}
