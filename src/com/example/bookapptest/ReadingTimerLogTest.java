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

	public static final String START_BUTTON = "Start";
	public static final String STOP_BUTTON = "Stop";
	public static final String CLEAR_BUTTON = "Clear";
	public static final String SAVE_BUTTON = "Save";
	public static final String ZERO_TIME = "00:00:00";
	public static final String TEST_TIME = "00:00:05";
	public static final String TEN_SECONDS = "00:00:10";
	
	/*
	 * Tests that the timer correctly counts 5 seconds. Timer has a 250-500 millisecond delay on start up. 
	 */
	
	public void testReadingTimer(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(5);
		 solo.clickOnButton(START_BUTTON);
		 solo.sleep(4500); //There's a 500 millisecond delay before timer starts so sleep time must match that
		 solo.clickOnButton(STOP_BUTTON);
		 TextView timerScreen = (TextView)solo.getView(R.id.timerScreen);
		 assertEquals(TEST_TIME, timerScreen.getText().toString());
		 solo.goBackToActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
	}
	
	/*
	 * Tests that the timer clears when the clear button is pressed
	 */
	
	public void testReadingTimerClear(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(5);
		 solo.clickOnButton(START_BUTTON);
		 solo.sleep(4500); //There's a 500 millisecond delay before timer starts so sleep time must match that
		 solo.clickOnButton(STOP_BUTTON);
		 solo.clickOnButton(CLEAR_BUTTON);
		 TextView timerScreen = (TextView)solo.getView(R.id.timerScreen);
		 assertEquals(ZERO_TIME, timerScreen.getText().toString());
		 solo.goBackToActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
	}
	/*
	 * Tests that the reading timer doesn't go back to zero when stopped and restarted. 
	 */
	public void testReadingTimerResumesTime(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(5);
		 solo.clickOnButton(START_BUTTON);
		 solo.sleep(4750); //There's roughly a 250-500 millisecond delay before timer starts so sleep time must match that
		 solo.clickOnButton(STOP_BUTTON);
		 solo.clickOnButton(START_BUTTON);
		 solo.sleep(4250);//Must account for milliseconds it takes to click the stop button and also the delay the timer has.
		 solo.clickOnButton(STOP_BUTTON);
		 TextView timerScreen = (TextView)solo.getView(R.id.timerScreen);
		 assertEquals(TEN_SECONDS, timerScreen.getText().toString());
		 solo.goBackToActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
	}
	/*
	 * Tests that the reading time and date is saved when the saved button is clicked
	 */
	public void testReadingTimerSave(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(5);
		 solo.clickOnButton(START_BUTTON);
		 solo.sleep(4750); //There's roughly a 250-500 millisecond delay before timer starts so sleep time must match that
		 solo.clickOnButton(STOP_BUTTON);
		 TextView timerScreen = (TextView)solo.getView(R.id.timerScreen);
		 solo.clickOnButton(SAVE_BUTTON);
		 boolean didToastAppear = solo.searchText("Time saved successfully!");
		 solo.goBackToActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
		 solo.clickOnImageButton(6);
		 String log = solo.getText(3).getText().toString();
		 String date = solo.getText(2).getText().toString();
		 String currentDate = convertDate(System.currentTimeMillis());
		 assertEquals(TEST_TIME, timerScreen.getText().toString());
		 assertEquals(TEST_TIME, log);
		 assertEquals(currentDate, date);
		 assertTrue(didToastAppear);
		 solo.goBackToActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
	}
	/*
	 * converts the current system time to mm/dd/yy format so that it can be compared to the newly created log date.
	 */
	public String convertDate(long toBeConverted){
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		String dateFormatted = formatter.format(toBeConverted); 
		
		return dateFormatted;
	}
}
