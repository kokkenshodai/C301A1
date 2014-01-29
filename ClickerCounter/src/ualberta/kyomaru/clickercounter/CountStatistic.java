package ualberta.kyomaru.clickercounter;

import java.util.Date;
import java.util.Collection;


/**
 * @author  kyomaru
 * 
 * Container that holds the Date label of count-occurrence-bin,
 * the name of this bin according to the organization scheme,
 * and the number of counts at that Date.
 * 
 */
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
		this.name = name;
		counts = 0;
	}
	
	@Override
	public String toString()
	{
		return name + ":\n" + counts;
	}
	
}
