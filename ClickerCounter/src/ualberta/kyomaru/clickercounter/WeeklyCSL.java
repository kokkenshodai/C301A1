package ualberta.kyomaru.clickercounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * This class takes events as Dates and
 * aggregates them as CountStatistics 
 * by rounding down to the nearest week
 */

public class WeeklyCSL extends CountStatisticList
{
    SimpleDateFormat format =
            new SimpleDateFormat("MMM dd");

	public WeeklyCSL()
	{
		super();
	}

	//the WeeklyCSL version of addCount
	//
	//stores the number of events that 
	//happen on each week as a CountStatistic
	//(weeks start on Monday)
	//
	//
	@Override
	public void addCount(Date event)
	{
		event = toWholeWeek(event);
		int index;
		if((index = this.getStatistics().getIndex(event)) >= 0)
		{
			this.getStatistics().get(index).addCount();
		}
		else
		{
			CountStatistic newCount = new CountStatistic(event, "Week of " + format.format(event));
			newCount.addCount();
			this.getStatistics().add(newCount);
		}
	}
	
	
	//toWholeWeek
	//
	//zeroes out the hour, minute, and second values of
	//the provided Date and sets the Date to that of the
	//most recent Monday
	//
	//
    static Date toWholeWeek(Date d) {
    Calendar c = new GregorianCalendar();
    c.setTime(d);

    while(c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
    {
    	c.add(Calendar.DATE, -1);
    }
    
    c.set(Calendar.HOUR, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);

    return c.getTime();
    }
}