package com.example.bookapptest;

import android.test.ActivityInstrumentationTestCase2;
import com.cycle7.bookapp.AddBookActivity;
import com.cycle7.bookapp.HomeScreenActivity;
import com.cycle7.bookapp.ReadingLogActivity;
import com.cycle7.bookapp.ReadingTimerActivity;
import com.cycle7.bookapp.ViewBookListActivity;
import com.cycle7.bookapp.ViewListActivity;
import com.jayway.android.robotium.solo.Solo;



public class HomeScreenTest extends ActivityInstrumentationTestCase2<HomeScreenActivity> {

		private Solo solo;
		public static final String BOOK_APP = "bookapp";
		public static final String ADD_BUTTON = "Add Button";
		public static final String VIEW_ALL_BOOKS_BUTTON = "View All Books";
		public static final String CREATE_LIST_BUTTON = "List Name:";
		public static final String VIEW_LISTS_BUTTON = "View Lists";
		public static final String READING_TIMER_BUTTON = "Reading Timer";
		public static final String READING_LOG_BUTTON = "Reading Log";
		public static final String CANCEL_BUTTON = "Cancel";
		
		
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

	
	public void testAddBookButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = ADD_BUTTON; 
		 String test = solo.getText(1).getText().toString();
		 solo.clickOnImageButton(0);
		 solo.assertCurrentActivity(BOOK_APP, AddBookActivity.class);
		 assertEquals(textView, test);
		 solo.goBack();
	}
	public void testAddBookToListButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = VIEW_ALL_BOOKS_BUTTON; 
		 String test = solo.getText(3).getText().toString();
		 solo.clickOnImageButton(2);
		 solo.assertCurrentActivity(BOOK_APP, ViewBookListActivity.class);
		 assertEquals(textView, test);
		 solo.goBack();
	}
	
	public void testCreateListButton() throws InterruptedException{
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = "List Name:"; 
		 String textView2 = CREATE_LIST_BUTTON;
		 String getButtonText = solo.getText(4).getText().toString();
		 solo.clickOnImageButton(3);
		 solo.waitForDialogToOpen();
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 assertEquals(textView2, getButtonText);
		 solo.clickOnButton(CANCEL_BUTTON);
	}
	public void testViewListsButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = VIEW_LISTS_BUTTON; 
		 String test = solo.getText(5).getText().toString();
		 solo.clickOnImageButton(4);
		 solo.assertCurrentActivity(BOOK_APP, ViewListActivity.class);
		 assertEquals(textView, test);
		 solo.goBack();
	}
	
	public void testReadingTimerButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = READING_TIMER_BUTTON; 
		 String test = solo.getText(6).getText().toString();
		 solo.clickOnImageButton(5);
		 solo.assertCurrentActivity(BOOK_APP, ReadingTimerActivity.class);
		 assertEquals(textView, test);
		 solo.goBack();
	}
	
	public void testReadingLogButton(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 String textView = READING_LOG_BUTTON; 
		 String test = solo.getText(7).getText().toString();
		 solo.clickOnImageButton(6);
		 solo.assertCurrentActivity(BOOK_APP, ReadingLogActivity.class);
		 assertEquals(textView, test);
		 solo.goBack();
	}
}
