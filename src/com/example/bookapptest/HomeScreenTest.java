package com.example.bookapptest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cycle7.bookapp.AddBookActivity;
import com.cycle7.bookapp.HomeScreenActivity;
import com.cycle7.bookapp.ReadingLogActivity;
import com.cycle7.bookapp.ReadingTimerActivity;
import com.cycle7.bookapp.ViewBookListActivity;
import com.cycle7.bookapp.ViewListActivity;
import com.cycle7.bookapp.R;
import com.jayway.android.robotium.solo.Solo;



public class HomeScreenTest extends ActivityInstrumentationTestCase2<HomeScreenActivity> {

		private Solo solo;
		public static final String BOOK_APP = "bookapp";
		public static final String ADD_BUTTON = "Add Book";
		public static final String VIEW_ALL_BOOKS_BUTTON = "View All Books";
		public static final String CREATE_LIST_BUTTON = "Create List";
		public static final String VIEW_LISTS_BUTTON = "View Lists";
		public static final String READING_TIMER_BUTTON = "Reading Timer";
		public static final String READING_LOG_BUTTON = "Reading Log";
		public static final String CANCEL_BUTTON = "Cancel";
		public static final String HOMESCREEN_ACTIVITY = "HomeScreenActivity";
		
	public HomeScreenTest() {
		super(HomeScreenActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		
	}
	@Override
	public void tearDown() throws Exception {
		try {
			this.solo.finalize();
			} catch (Throwable e) {
			e.printStackTrace();
			}
			getActivity().finish();
			super.tearDown();
	}

	/*
	 * Tests the Add Book navigation button
	 */
	
	public void testAddBookButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = ADD_BUTTON; 
		 String test = solo.getText(1).getText().toString();
		 solo.clickOnImageButton(0);
		 solo.assertCurrentActivity(BOOK_APP, AddBookActivity.class);
		 assertEquals(textView, test);
		 solo.goBack();
	}
	/*
	 * Tests the Add Book to List navigation button
	 */
	public void testViewAllBooks(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = solo.getString(R.string.view_read_book_list); 
		 String test = solo.getText(3).getText().toString();
		 solo.clickOnImageButton(2);
		 solo.assertCurrentActivity(BOOK_APP, ViewBookListActivity.class);
		 assertEquals(textView, test);
		 solo.goBack();
	}
	/*
	 * Tests the Create List navigation button
	 */
	public void testCreateListButton() throws InterruptedException{
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String getButtonText = solo.getString(R.string.create_book_list_button);
		 solo.clickOnImageButton(3);
		 solo.waitForDialogToOpen();
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 assertEquals(CREATE_LIST_BUTTON, getButtonText);
		 solo.clickOnButton(CANCEL_BUTTON);
	}
	
	/*
	 * Tests the View List navigation button
	 */
	public void testViewListsButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = solo.getString(R.string.view_book_lists_button); 
		 TextView buttonText = (TextView) solo.getView(R.id.home_view_list_text);
		 solo.clickOnImageButton(4);
		 solo.assertCurrentActivity(BOOK_APP, ViewListActivity.class);
		 assertEquals(textView, buttonText.getText().toString());
		 solo.goBack();
	}
	/*
	 * Tests the Reading Timer navigation button
	 */
	public void testReadingTimerButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = solo.getString(R.string.reading_timer);
		 TextView buttonText = (TextView) solo.getView(R.id.home_reading_timer_text);
		 solo.clickOnImageButton(5);
		 solo.assertCurrentActivity(BOOK_APP, ReadingTimerActivity.class);
		 assertEquals(textView, buttonText.getText().toString());
		 solo.goBack();
	}
	/*
	 * Tests the Reading Log navigation button
	 */
	public void testReadingLogButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = solo.getString(R.id.logReadingButton);
		 TextView buttonText = (TextView) solo.getView(R.id.home_reading_log_text);
		 solo.clickOnImageButton(6);
		 solo.assertCurrentActivity(BOOK_APP, ReadingLogActivity.class);
		 assertEquals(textView, buttonText.getText().toString());
		 solo.goBack();
	}
}
