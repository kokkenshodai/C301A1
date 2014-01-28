package ualberta.kyomaru.clickercounter;

import java.util.Date;
import java.util.Collection;
import ualberta.kyomaru.clickercounter.CountStatisticArray.CSComparator;


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

	/**
	 * @uml.property  name="cSComparator"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="composite" inverse="countStatistic:ualberta.kyomaru.clickercounter.CountStatisticArray.CSComparator"
	 */
	private Collection csComparator;

	/**
	 * Getter of the property <tt>cSComparator</tt>
	 * @return  Returns the csComparator.
	 * @uml.property  name="cSComparator"
	 */
	public Collection getCSComparator()
	
	{
	
		return csComparator;
	}

	/**
	 * Setter of the property <tt>cSComparator</tt>
	 * @param cSComparator  The csComparator to set.
	 * @uml.property  name="cSComparator"
	 */
	public void setCSComparator(Collection csComparator)
	
	{
	
		this.csComparator = csComparator;
	}

	/**
	 * @uml.property  name="countStatisticArray"
	 * @uml.associationEnd  inverse="countStatistic:ualberta.kyomaru.clickercounter.CountStatisticArray"
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
