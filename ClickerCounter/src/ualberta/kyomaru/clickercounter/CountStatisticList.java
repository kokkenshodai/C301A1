package ualberta.kyomaru.clickercounter;

import java.util.Date;


/**
 * @uml.dependency   supplier="ualberta.kyomaru.clickercounter.CountStatistic"
 * @uml.dependency   supplier="ualberta.kyomaru.clickercounter.CountStatisticArray"
 */
public abstract class CountStatisticList extends 
CountStatistic
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

	/** 
	 * @uml.property name="countStatisticArray"
	 * @uml.associationEnd aggregation="composite" inverse="countStatisticList:ualberta.kyomaru.clickercounter.CountStatisticArray"
	 */
	private CountStatisticArray countStatisticArray;

	/** 
	 * Getter of the property <tt>countStatisticArray</tt>
	 * @return  Returns the countStatisticArray.
	 * @uml.property  name="countStatisticArray"
	 */
	public CountStatisticArray getCountStatisticArray()
	
	
	{
		return countStatisticArray;
	}

	/** 
	 * Setter of the property <tt>countStatisticArray</tt>
	 * @param countStatisticArray  The countStatisticArray to set.
	 * @uml.property  name="countStatisticArray"
	 */
	public void setCountStatisticArray(CountStatisticArray countStatisticArray)
	
	
	{
		this.countStatisticArray = countStatisticArray;
	}
}
