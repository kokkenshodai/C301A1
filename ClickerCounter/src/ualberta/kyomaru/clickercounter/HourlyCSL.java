package ualberta.kyomaru.clickercounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * This class takes events as Dates and
 * aggregates them as CountStatistics 
 * by rounding down to the nearest hour
 */

public class HourlyCSL extends CountStatisticList
{
    SimpleDateFormat format =
            new SimpleDateFormat("MMM dd hh:mmaa");

	public HourlyCSL()
	{
		super();
	}

	//the HourlyCSL version of addCount
	//
	//stores the number of events that 
	//happen on each hour as a CountStatistic
	//
	//
	@Override
	public void addCount(Date event)
	{
		event = toWholeHour(event);
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
	
	
	//toWholeHour
	//
	//zeroes out the minute and second values of
	//the provided Date
	//
	//
    static Date toWholeHour(Date d) {
    Calendar c = new GregorianCalendar();
    c.setTime(d);

    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);

    return c.getTime();
    }

}
