package ualberta.kyomaru.clickercounter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Collection;


/**
 * @uml.dependency  supplier="ualberta.kyomaru.clickercounter.CountStatistic"
 */
public class CountStatisticArray extends ArrayList<CountStatistic>
{

	/**
	 * @uml.dependency  supplier="ualberta.kyomaru.clickercounter.CountStatistic"
	 */
	public class CSComparator implements Comparator<CountStatistic>{

	    public int compare(CountStatistic CS1, CountStatistic CS2) {

	        return CS1.getPeriod().compareTo(CS2.getPeriod());
	    }

		/**
		 * @uml.property  name="countStatistic"
		 * @uml.associationEnd  
		 */
		private CountStatistic countStatistic;

		/**
		 * Getter of the property <tt>countStatistic</tt>
		 * @return  Returns the countStatistic.
		 * @uml.property  name="countStatistic"
		 */
		public CountStatistic getCountStatistic()
		
		
		{
		
			return countStatistic;
		}

		/**
		 * Setter of the property <tt>countStatistic</tt>
		 * @param countStatistic  The countStatistic to set.
		 * @uml.property  name="countStatistic"
		 */
		public void setCountStatistic(CountStatistic countStatistic)
		
		
		{
		
			this.countStatistic = countStatistic;
		}
	}
	
	public void sort()
	{
		Collections.sort(this, new CSComparator());
	}
	
	private boolean dateEquals(Date d1, Date d2)
	{
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.setTime(d1);
		c2.setTime(d2);
		
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH))
				&& (c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY))
				&& (c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE))
				&& (c1.get(Calendar.SECOND) == c2.get(Calendar.SECOND));
	}
	
	public int getIndex(Date date)
	{
		sort();
		int ret = -1;
		for(int i = 0; i < this.size(); i++)
		{
			if(dateEquals(date, this.get(i).getPeriod()))
			{
				ret = i;
				break;
			}
		}
		return ret;
	}

	/**
	 * @uml.property  name="countStatisticList"
	 * @uml.associationEnd  
	 */
	private CountStatisticList countStatisticList;

	/** 
	 * Getter of the property <tt>countStatisticList</tt>
	 * @return  Returns the countStatisticList.
	 * @uml.property  name="countStatisticList"
	 */
	public CountStatisticList getCountStatisticList()
	
	
	
	
	{
		return countStatisticList;
	}

	/** 
	 * Setter of the property <tt>countStatisticList</tt>
	 * @param countStatisticList  The countStatisticList to set.
	 * @uml.property  name="countStatisticList"
	 */
	public void setCountStatisticList(CountStatisticList countStatisticList)
	
	
	
	
	{
		this.countStatisticList = countStatisticList;
	}

	/**
	 * @uml.property  name="countStatistic"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="composite" inverse="countStatisticArray:ualberta.kyomaru.clickercounter.CountStatistic"
	 */
	private Collection countStatistic;

	/**
	 * Getter of the property <tt>countStatistic</tt>
	 * @return  Returns the countStatistic.
	 * @uml.property  name="countStatistic"
	 */
	public Collection getCountStatistic()
	
	{
	
		return countStatistic;
	}

	/**
	 * Setter of the property <tt>countStatistic</tt>
	 * @param countStatistic  The countStatistic to set.
	 * @uml.property  name="countStatistic"
	 */
	public void setCountStatistic(Collection countStatistic)
	
	{
	
		this.countStatistic = countStatistic;
	}
}
