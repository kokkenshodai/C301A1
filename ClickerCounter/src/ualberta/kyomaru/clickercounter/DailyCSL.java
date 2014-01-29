package ualberta.kyomaru.clickercounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * This class takes events as Dates and
 * aggregates them as CountStatistics 
 * by rounding down to the nearest day
 */

public class DailyCSL extends CountStatisticList
{
    SimpleDateFormat format =
            new SimpleDateFormat("MMM dd");

	public DailyCSL()
	{
		super();
	}

	//the DailyCSL version of addCount
	//
	//stores the number of events that 
	//happen on each day as a CountStatistic
	//
	//
	@Override
	public void addCount(Date event)
	{
		event = toWholeDay(event);
		int index;
		if((index = this.getStatistics().getIndex(event)) >= 0)
		{
			this.getStatistics().get(index).addCount();
		}
		else
		{
			CountStatistic newCount = new CountStatistic(event, format.format(event));
			newCount.addCount();
			this.getStatistics().add(newCount);
		}
	}
	
	//toWholeDay
	//
	//zeroes out the hour, minute, and second values of
	//the provided Date
	//
	//
    static Date toWholeDay(Date d) {
    Calendar c = new GregorianCalendar();
    c.setTime(d);

    c.set(Calendar.HOUR, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);

    return c.getTime();
    }
}
