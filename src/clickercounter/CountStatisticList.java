package clickercounter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


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
