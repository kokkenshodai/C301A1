package ualberta.kyomaru.clickercounter;

import java.util.ArrayList;

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

public class ClickerCounterActivity extends Activity
{

		private static final String FILENAME = "file.sav";
		private static ArrayList<Counter> counters;
		private ListView counterListView;
		private ArrayAdapter<Counter> adapter;
		private Counter currentCounter;
		private int currentCounterIndex;
		//private Gson gson = new Gson();
		
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
		}

		public void resumeMain() {
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
		}
		
		@Override
		protected void onStart() {
			super.onStart();
			adapter = new ArrayAdapter<Counter>(this,
					R.layout.counter_button, counters);
			counterListView.setAdapter(adapter);
		}
		
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
		
		private void switchToMenuContext()
		{
			setContentView(R.layout.counter_menu);
			final Button sbh = (Button)findViewById(R.id.statisticsbyhour);
			final Button sbd = (Button)findViewById(R.id.statisticsbyday);
			final Button sbw = (Button)findViewById(R.id.statisticsbyweek);
			final Button sbm = (Button)findViewById(R.id.statisticsbymonth);
			
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
		
	}

