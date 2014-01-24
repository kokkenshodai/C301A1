package ualberta.kyomaru.clickercounter;

import java.util.ArrayList;
import java.util.Date;


public class Counter
{
	private ArrayList<Date> counts;
	private String type;
	
	public Counter(String type)
	{
		this.type = type;
		counts = new ArrayList<Date>();
	}
	
	public void count()
	{
		counts.add(new Date());
	}
	
	public void reset()
	{
		counts = new ArrayList<Date>();
	}
	
	public String getType()
	{
		return type;
	}
	
	public void rename(String newName)
	{
		type = newName;
	}
	
	public int getCounts()
	{
		return counts.size();
	}
	
	public CountStatisticArray getDailyStatistics()
	{
		DailyCSL dcsl = new DailyCSL();
		for(Date count: counts)
		{
			dcsl.addCount(count);
		}
		
		return dcsl.getStatistics();
	}
	
	public CountStatisticArray getWeeklyStatistics()
	{
		WeeklyCSL wcsl = new WeeklyCSL();
		for(Date count: counts)
		{
			wcsl.addCount(count);
		}
		
		return wcsl.getStatistics();
	}
	
	public CountStatisticArray getHourlyStatistics()
	{
		HourlyCSL hcsl = new HourlyCSL();
		for(Date count: counts)
		{
			hcsl.addCount(count);
		}
		
		return hcsl.getStatistics();
	}
	
	public CountStatisticArray getMonthlyStatistics()
	{
		MonthlyCSL mcsl = new MonthlyCSL();
		for(Date count: counts)
		{
			mcsl.addCount(count);
		}
		
		return mcsl.getStatistics();
	}
	
	@Override
	public String toString()
	{
		return "Counter: " + type + "\nCounts : " + getCounts();
	}
}
