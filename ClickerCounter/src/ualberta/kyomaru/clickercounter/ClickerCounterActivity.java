package ualberta.kyomaru.clickercounter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author  kyomaru
 */
public class ClickerCounterActivity extends Activity
{

		//Global variables
		private static final String FILENAME = "file.sav";
		private ListView counterListView;
		private ArrayAdapter<Counter> adapter;
		private Gson gson = new Gson();
		
		//State variables that are saved and loaded
		private static ArrayList<Counter> counters;
		private Counter currentCounter;
		private int currentCounterIndex;
		
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_clicker_counter);
			counterListView = (ListView) findViewById(R.id.counter_frame);

			counters = new ArrayList<Counter>();
			Button createButton = (Button) findViewById(R.id.create);

			createButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					getNewCounter();
				}
			});
			counterListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				
				@Override
				public void onItemClick(AdapterView av, View v, int item, long l)
				{
					setResult(RESULT_OK);
					switchToCounterContext(item);
				}
			});
			
			Button orderButton = (Button) findViewById(R.id.order);

			orderButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					orderCounters();
				}
			});

		}
		
		@Override
		protected void onStart() {
			super.onStart();
		}
		
		@Override
		protected void onResume() {
			super.onResume();
			load();
			adapter = new ArrayAdapter<Counter>(this,
					R.layout.counter_button, counters);
			counterListView.setAdapter(adapter);
		}
		
		// save()
		// saves counters and your current state
		//
		//
		private void save()
		{
			try
			{
				FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
				
				String json = gson.toJson(counters);
				out.write(json + "\n");
				json = gson.toJson(currentCounter);
				out.write(json + "\n");
				json = gson.toJson(currentCounterIndex);
				out.write(json + "\n");
				
				out.close();
				fos.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		// load()
		// loads counters and your previous state
		// always initiates with main menu context
		//
		//
		private void load()
		{
			try
			{
				FileInputStream fis = openFileInput(FILENAME);
				BufferedReader in = new BufferedReader(new InputStreamReader(fis));
				
				String line = in.readLine();
				System.out.println(line);
				counters = gson.fromJson(line, new TypeToken<ArrayList<Counter>>(){}.getType());
				
				line = in.readLine();
				System.out.println(line);
				currentCounter = gson.fromJson(line, Counter.class);
				
				line = in.readLine();
				System.out.println(line);
				currentCounterIndex = gson.fromJson(line, Integer.class);
				
				in.close();
				fis.close();

				if(counters == null)
					counters = new ArrayList<Counter>();
				} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void orderCounters()
		{
			Collections.sort(counters, new Comparator<Counter>(){
			    public int compare(Counter C1, Counter C2) {

			        return C2.getCounts() - C1.getCounts();
			    }
			});
			adapter.notifyDataSetChanged();
			save();
		}

		//Context-switch methods
		//
		//
		//resumeMain()
		//Switches context back to Main
		//
		private void resumeMain() {
			setContentView(R.layout.activity_clicker_counter);
			counterListView = (ListView) findViewById(R.id.counter_frame);
			Button createButton = (Button) findViewById(R.id.create);

			createButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					getNewCounter();
				}
			});
			counterListView.setAdapter(adapter);
			counterListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				
				@Override
				public void onItemClick(AdapterView av, View v, int item, long l)
				{
					setResult(RESULT_OK);
					switchToCounterContext(item);
				}
			});
			
			Button orderButton = (Button) findViewById(R.id.order);

			orderButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					orderCounters();
				}
			});
		}
	
		//switchToCounterContext()
		//Switches context from main to counter
		//
		private void switchToCounterContext(int item)
		{
			setContentView(R.layout.counter_screen);
			currentCounter = counters.get(item);
			currentCounterIndex = item;
			final TextView t1 = (TextView)findViewById(R.id.counterscreentitle);
			t1.setText(currentCounter.getType());
			final TextView t2 = (TextView)findViewById(R.id.counterscreennumber);
			t2.setText(Integer.toString(currentCounter.getCounts()));
			t2.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					currentCounter.count();
					save();
					t2.setText(Integer.toString(currentCounter.getCounts()));
				}
			});
			final Button menu = (Button)findViewById(R.id.countermenu);
			menu.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					switchToMenuContext();
				}
			});
		}
		
		//resumeMCounting()
		//Switches context back to Counter
		//
		private void resumeCounting()
		{
			setContentView(R.layout.counter_screen);
			final TextView t1 = (TextView)findViewById(R.id.counterscreentitle);
			t1.setText(currentCounter.getType());
			final TextView t2 = (TextView)findViewById(R.id.counterscreennumber);
			t2.setText(Integer.toString(currentCounter.getCounts()));
			t2.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					currentCounter.count();
					t2.setText(Integer.toString(currentCounter.getCounts()));
				}
			});
			final Button menu = (Button)findViewById(R.id.countermenu);
			menu.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					switchToMenuContext();
				}
			});
		}
		
		//switchToMenuContext()
		//Switches context to the Counter Menu
		//
		private void switchToMenuContext()
		{
			setContentView(R.layout.counter_menu);
			final Button sbh = (Button)findViewById(R.id.statisticsbyhour);
			sbh.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					getHourlyStats();
				}
			});
			
			final Button sbd = (Button)findViewById(R.id.statisticsbyday);
			sbd.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					getDailyStats();
				}
			});
			
			
			final Button sbw = (Button)findViewById(R.id.statisticsbyweek);
			sbw.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					getWeeklyStats();
				}
			});
			
			
			final Button sbm = (Button)findViewById(R.id.statisticsbymonth);
			sbm.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					getMonthlyStats();
				}
			});
			
			
			
			final Button rename = (Button)findViewById(R.id.renamebutton);
			rename.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					getRenamedCounter();
				}
			});
			
			final Button reset = (Button)findViewById(R.id.counterscreenresetbutton);
			reset.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					resetCounter();
				}
			});
			
			final Button delete = (Button)findViewById(R.id.counterscreendeletebutton);
			delete.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					deleteCounter();
				}
			});
			
			final Button exit = (Button)findViewById(R.id.counterscreenexitbutton);
			exit.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					resumeMain();
				}
			});
			
			final Button resume = (Button)findViewById(R.id.counterscreenresume);
			resume.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					setResult(RESULT_OK);
					resumeCounting();
				}
			});
		}
		//
		//
		//
		
		
		//Methods to get stats
		//
		//
		//
		//getHourlyStats()
		//Provides a popup with the hourly stats
		//
		private void getHourlyStats() {
		    final View mView = View.inflate(this, R.layout.statdialog, null);
		    final ListView lv = (ListView)mView.findViewById(R.id.statlist);
		    final ArrayAdapter<CountStatistic> aa = new ArrayAdapter<CountStatistic>(this,R.layout.counter_button,currentCounter.getHourlyStatistics());
		    lv.setAdapter(aa);
		    
		    final InputMethodManager mInputMethodManager = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    mInputMethodManager.restartInput(mView);

		    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		    mBuilder.setTitle(getString(R.string.hourlystatstitle));
		    mBuilder.setPositiveButton(getString(R.string.ok), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		        	switchToMenuContext();
		        }
		    });
		    mBuilder.setView(mView);
		    mBuilder.show();

		    if (mInputMethodManager != null) {
		    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		    }
		    
		}
		
		//getDailyStats()
		//Provides a popup with the daily stats
		//
		private void getDailyStats() {
		    final View mView = View.inflate(this, R.layout.statdialog, null);
		    final ListView lv = (ListView)mView.findViewById(R.id.statlist);
		    final ArrayAdapter<CountStatistic> aa = new ArrayAdapter<CountStatistic>(this,R.layout.counter_button,currentCounter.getDailyStatistics());
		    lv.setAdapter(aa);
		    
		    final InputMethodManager mInputMethodManager = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    mInputMethodManager.restartInput(mView);

		    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		    mBuilder.setTitle(getString(R.string.dailystatstitle));
		    mBuilder.setPositiveButton(getString(R.string.ok), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		        	switchToMenuContext();
		        }
		    });
		    mBuilder.setView(mView);
		    mBuilder.show();

		    if (mInputMethodManager != null) {
		    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		    }
		    
		}
		
		//getWeeklyStats()
		//Provides a popup with the weekly stats
		//
		private void getWeeklyStats() {
		    final View mView = View.inflate(this, R.layout.statdialog, null);
		    final ListView lv = (ListView)mView.findViewById(R.id.statlist);
		    final ArrayAdapter<CountStatistic> aa = new ArrayAdapter<CountStatistic>(this,R.layout.counter_button,currentCounter.getWeeklyStatistics());
		    lv.setAdapter(aa);
		    
		    final InputMethodManager mInputMethodManager = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    mInputMethodManager.restartInput(mView);

		    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		    mBuilder.setTitle(getString(R.string.weeklystatstitle));
		    mBuilder.setPositiveButton(getString(R.string.ok), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		        	switchToMenuContext();
		        }
		    });
		    mBuilder.setView(mView);
		    mBuilder.show();

		    if (mInputMethodManager != null) {
		    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		    }
		    
		}
		
		//getMonthlyStats()
		//Provides a popup with the monthly stats
		//
		private void getMonthlyStats() {
		    final View mView = View.inflate(this, R.layout.statdialog, null);
		    final ListView lv = (ListView)mView.findViewById(R.id.statlist);
		    final ArrayAdapter<CountStatistic> aa = new ArrayAdapter<CountStatistic>(this,R.layout.counter_button,currentCounter.getMonthlyStatistics());
		    lv.setAdapter(aa);
		    
		    final InputMethodManager mInputMethodManager = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    mInputMethodManager.restartInput(mView);

		    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		    mBuilder.setTitle(getString(R.string.monthlystatstitle));
		    mBuilder.setPositiveButton(getString(R.string.ok), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		        	switchToMenuContext();
		        }
		    });
		    mBuilder.setView(mView);
		    mBuilder.show();

		    if (mInputMethodManager != null) {
		    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		    }
		    
		}
		//
		//
		//
		
		//Methods for counter creation and modification
		//
		//
		//deleteCounter()
		//Deletes current counter (according to user's decision in confirmation dialog) 
		//and returns to main menu
		//
		private void deleteCounter() {
		    final View mView = View.inflate(this, R.layout.deletedialog, null);
		    final TextView text = (TextView) mView.findViewById(R.id.deletedialogtext);
		    text.setText("Are you sure you wish to delete the Counter \"" + currentCounter.getType() +"\"?");
		    
		    final InputMethodManager mInputMethodManager = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    mInputMethodManager.restartInput(mView);

		    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		    mBuilder.setTitle(getString(R.string.deletecountertitle));
		    mBuilder.setPositiveButton(getString(R.string.deleteconfirm), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		 
		        	counters.remove(currentCounterIndex);
		        	currentCounter = null;
		        	currentCounterIndex = -1;
		        	save();
		        	resumeMain();
		      }
		    });
		    mBuilder.setNegativeButton(getString(R.string.deletecancel), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		 
		        	
		      }
		    });
		    mBuilder.setView(mView);
		    mBuilder.show();

		    if (mInputMethodManager != null) {
		    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		    }
		    
		}
		
		//resetCounter()
		//resets current counter (according to user's decision in confirmation dialog) 
		//and returns to counting
		//
		private void resetCounter() {
		    final View mView = View.inflate(this, R.layout.deletedialog, null);
		    final TextView text = (TextView) mView.findViewById(R.id.deletedialogtext);
		    text.setText("Are you sure you wish to reset the Counter \"" + currentCounter.getType() +"\"?");
		    
		    final InputMethodManager mInputMethodManager = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    mInputMethodManager.restartInput(mView);

		    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		    mBuilder.setTitle(getString(R.string.resetcountertitle));
		    mBuilder.setPositiveButton(getString(R.string.resetconfirm), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		 
		        	currentCounter.reset();
		        	save();
		        	resumeCounting();
		      }
		    });
		    mBuilder.setNegativeButton(getString(R.string.resetcancel), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		 
		        	
		      }
		    });
		    mBuilder.setView(mView);
		    mBuilder.show();

		    if (mInputMethodManager != null) {
		    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		    }
		    
		}
		
		//getRenamedCounter()
		//
		//Provides popup interface for renaming the counter
		//returns to counting afterwards
		//
		private void getRenamedCounter() {
		    final View mView = View.inflate(this, R.layout.get_new_name, null);
		    final EditText newNameText = (EditText) mView.findViewById(R.id.newname);
		    newNameText.setText(currentCounter.getType());
		    
		    final InputMethodManager mInputMethodManager = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    mInputMethodManager.restartInput(mView);

		    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		    mBuilder.setTitle(getString(R.string.renamecounter));
		    mBuilder.setPositiveButton(getString(R.string.renamecounterbutton), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		 
		        String mCounterName = newNameText.getText().toString().trim();
		        if (mCounterName.length() > 0) {
		            currentCounter.rename(mCounterName);
		            save();
		            resumeCounting();
		        }
		      }
		    });
		    mBuilder.setView(mView);
		    mBuilder.show();

		    if (mInputMethodManager != null) {
		    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		    }
		    
		}

		//getNewCounter()
		//
		//Provides popup interface for naming the counter
		//returns to main menu afterwards
		//
		private void getNewCounter() {
		    final View mView = View.inflate(this, R.layout.get_new_name, null);
		    final EditText newNameText = (EditText) mView.findViewById(R.id.newname);
		    
		    final InputMethodManager mInputMethodManager = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    mInputMethodManager.restartInput(mView);

		    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		    mBuilder.setTitle(getString(R.string.newcountercreation));
		    mBuilder.setPositiveButton(getString(R.string.newname), new Dialog.OnClickListener() {
		        public void onClick(DialogInterface mDialogInterface, int mWhich) {
		 
		        String mCounterName = newNameText.getText().toString().trim();
		        if (mCounterName.length() > 0) {
		            counters.add(new Counter(mCounterName));
		            adapter.notifyDataSetChanged();
		            save();
		            mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		                mDialogInterface.dismiss();
		        }
		      }
		    });
		    mBuilder.setView(mView);
		    mBuilder.show();

		    if (mInputMethodManager != null) {
		    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		    }
		    
		}
		//
		//
		//
	}

