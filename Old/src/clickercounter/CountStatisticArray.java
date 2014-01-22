package clickercounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class CountStatisticArray extends ArrayList<CountStatistic>
{

	public class CSComparator implements Comparator<CountStatistic>{

	    public int compare(CountStatistic CS1, CountStatistic CS2) {

	        return CS1.getPeriod().compareTo(CS2.getPeriod());
	    }
	}
	
	public void sort()
	{
		Collections.sort(this, new CSComparator());
	}
	
	public int getIndex(Date date)
	{
		sort();
		int ret = Collections.binarySearch(this, new CountStatistic(date, ""), new CSComparator());
		return ret;
	}
}
