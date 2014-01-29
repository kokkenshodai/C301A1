package ualberta.kyomaru.clickercounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * This class takes events as Dates and
 * aggregates them as CountStatistics 
 * by rounding down to the nearest month
 */


public class MonthlyCSL extends CountStatisticList
{
    SimpleDateFormat format =
            new SimpleDateFormat("MMM");

	public MonthlyCSL()
	{
		super();
	}

	//the MonthlyCSL version of addCount
	//
	//stores the number of events that 
	//happen on each month as a CountStatistic
	//
	//
	@Override
	public void addCount(Date event)
	{
		event = toWholeMonth(event);
		int index;
		if((index = this.getStatistics().getIndex(event)) >= 0)
		{
			this.getStatistics().get(index).addCount();
		}
		else
		{
			CountStatistic newCount = new CountStatistic(event, "Month of " + format.format(event));
			newCount.addCount();
			this.getStatistics().add(newCount);
		}
	}
	
	
	//toWholeMonth
	//
	//zeroes out the hour, minute, and second values of
	//the provided Date and sets the day-of-month to 1
	//(the first of the month)
	//
	//
    static Date toWholeMonth(Date d) {
    Calendar c = new GregorianCalendar();
    c.setTime(d);

    c.set(Calendar.DAY_OF_MONTH, 1);
    c.set(Calendar.HOUR, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);

    return c.getTime();
    }
}