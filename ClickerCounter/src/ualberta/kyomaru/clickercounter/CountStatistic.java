package ualberta.kyomaru.clickercounter;

import java.util.Date;


public class CountStatistic
{
	private Date period;
	private String name;
	private int counts;
	
	public String getString()
	{
		return name;
	}
	
	public Date getPeriod()
	{
	
		return period;
	}
	
	public int getCounts()
	{
	
		return counts;
	}
	
	public void addCount()
	{
		counts++;
	}
	
	public CountStatistic(Date period, String name)
	{
		this.period = period;
		counts = 0;
	}
	
}
