package clickercounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class HourlyCSL extends CountStatisticList
{
    SimpleDateFormat format =
            new SimpleDateFormat("MMM dd hh:mmaa");

	public HourlyCSL()
	{
		super();
	}

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
			this.getStatistics().add(newCount);
		}
	}
	
    static Date toWholeHour(Date d) {
    Calendar c = new GregorianCalendar();
    c.setTime(d);

    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);

    return c.getTime();
    }

}
