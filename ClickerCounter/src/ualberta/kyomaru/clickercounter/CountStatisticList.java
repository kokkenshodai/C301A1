package ualberta.kyomaru.clickercounter;

import java.util.Date;


/**
 * @uml.dependency  supplier="ualberta.kyomaru.clickercounter.CountStatisticArray"
 * @uml.dependency  supplier="ualberta.kyomaru.clickercounter.CountStatistic"
 */
public abstract class CountStatisticList
{
	/**
	 * @uml.property  name="statistics"
	 * @uml.associationEnd  
	 */
	private CountStatisticArray statistics;
	
	public CountStatisticList()
	{
		statistics = new CountStatisticArray();
	}
	
	public abstract void addCount(Date count);
	
	/**
	 * @return
	 * @uml.property  name="statistics"
	 */
	public CountStatisticArray getStatistics()
	{
		return statistics;
	}

	/**
	 * @uml.property  name="countStatisticArray"
	 * @uml.associationEnd  
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
