package clickercounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class WeeklyCSL extends CountStatisticList
{
    SimpleDateFormat format =
            new SimpleDateFormat("MMM dd");

	public WeeklyCSL()
	{
		super();
	}

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
			this.getStatistics().add(newCount);
		}
	}
	
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