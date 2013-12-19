package com.example.bookapptest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cycle7.bookapp.HomeScreenActivity;
import com.cycle7.bookapp.R;
import com.jayway.android.robotium.solo.Solo;


public class ReadingTimerLogTest extends ActivityInstrumentationTestCase2<HomeScreenActivity> {
	private Solo solo;
	
	
	public ReadingTimerLogTest() {
		super(HomeScreenActivity.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		try {
			this.solo.finalize();
			} catch (Throwable e) {
			e.printStackTrace();
			}
			getActivity().finish();
			super.tearDown();
	}

	public void testReadingTimer(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(5);
		 solo.clickOnButton("Start");
		 solo.sleep(4500); //There's a 500 millisecond delay before timer starts so sleep time must match that
		 solo.clickOnButton("Stop");
		 TextView timerScreen = (TextView)solo.getView(R.id.timerScreen);
		 assertEquals("00:00:05", timerScreen.getText().toString());
		 solo.goBackToActivity("HomeScreenActivity");
	}
	
	public void testReadingTimerClear(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(5);
		 solo.clickOnButton("Start");
		 solo.sleep(4500); //There's a 500 millisecond delay before timer starts so sleep time must match that
		 solo.clickOnButton("Stop");
		 solo.clickOnButton("Clear");
		 TextView timerScreen = (TextView)solo.getView(R.id.timerScreen);
		 assertEquals("00:00:00", timerScreen.getText().toString());
		 solo.goBackToActivity("HomeScreenActivity");
	}
	
	public void testReadingTimerResumesTime(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(5);
		 solo.clickOnButton("Start");
		 solo.sleep(4750); //There's roughly a 250-500 millisecond delay before timer starts so sleep time must match that
		 solo.clickOnButton("Stop");
		 solo.clickOnButton("Start");
		 solo.sleep(4250);//Must account for milliseconds it takes to click the stop button and also the delay the timer has.
		 solo.clickOnButton("Stop");
		 TextView timerScreen = (TextView)solo.getView(R.id.timerScreen);
		 assertEquals("00:00:10", timerScreen.getText().toString());
		 solo.goBackToActivity("HomeScreenActivity");
	}
	public void testReadingTimerSave(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(5);
		 solo.clickOnButton("Start");
		 solo.sleep(4750); //There's roughly a 250-500 millisecond delay before timer starts so sleep time must match that
		 solo.clickOnButton("Stop");
		 TextView timerScreen = (TextView)solo.getView(R.id.timerScreen);
		 solo.clickOnButton("Save");
		 boolean didToastAppear = solo.searchText("Time saved successfully!");
		 solo.goBackToActivity("HomeScreenActivity");
		 solo.clickOnImageButton(6);
		 String log = solo.getText(3).getText().toString();
		 String date = solo.getText(2).getText().toString();
		 String currentDate = convertDate(System.currentTimeMillis());
		 assertEquals("00:00:05", timerScreen.getText().toString());
		 assertEquals("00:00:05", log);
		 assertEquals(currentDate, date);
		 assertTrue(didToastAppear);
		 solo.goBackToActivity("HomeScreenActivity");
	}
	
	public String convertDate(long toBeConverted){
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		String dateFormatted = formatter.format(toBeConverted); 
		
		return dateFormatted;
	}
}
