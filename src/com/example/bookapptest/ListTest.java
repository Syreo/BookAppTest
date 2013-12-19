package com.example.bookapptest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import android.widget.EditText;

import com.cycle7.bookapp.HomeScreenActivity;
import com.cycle7.bookapp.R;
import com.jayway.android.robotium.solo.Solo;

public class ListTest extends ActivityInstrumentationTestCase2<HomeScreenActivity> {
	
		private Solo solo;
		public static final String CANCEL_BUTTON = "Cancel";
		public static final String SAVE_BUTTON = "Save";
		
		
	public ListTest() {
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
	

	public void testDialogText(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(3);
		 String listNameText = "List Name:";
		 solo.waitForDialogToOpen();
		 String getDialogText = solo.getText(0).getText().toString();
		 assertEquals(listNameText, getDialogText);
		 solo.clickOnButton(CANCEL_BUTTON);
	}
	
	public void testButtonText(){
		String expectedButtonText = SAVE_BUTTON;
		String expectedButtonText2 = CANCEL_BUTTON;
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(3);
		 solo.waitForDialogToOpen();
		 String firstButtonText = solo.getButton(0).getText().toString();
		 String secondButtonText = solo.getButton(1).getText().toString();
		 assertEquals(expectedButtonText, firstButtonText);
		 assertEquals(expectedButtonText2, secondButtonText);  
		 solo.clickOnButton(CANCEL_BUTTON);
	}
	
	public void testEmptyStringToast(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(3);
		 solo.waitForDialogToOpen();
		 solo.clickOnButton(SAVE_BUTTON);
		 boolean didToastAppear = solo.searchText("Blank or non-unique list names are not allowed");
		 assertTrue(didToastAppear);
	}
	
	public void test1CreateList(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(3);
		 solo.waitForDialogToOpen();
		 String textToInput = randomListName();
		 EditText listInput = (EditText) solo.getView(R.id.listNameInput);
		 solo.enterText(listInput, textToInput);
		 solo.clickOnButton(SAVE_BUTTON);
		 solo.clickOnImageButton(4);
		 boolean isListVisible = solo.searchText(textToInput);
		 assertTrue(isListVisible);
		 solo.goBack();
		 
	}
	
	public void test2AddBookToList(){
		solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		solo.clickOnImageButton(1);
		solo.clickOnCheckBox(0);
		String clickedBook = solo.getText(3).getText().toString();
		solo.clickOnButton("Add");
		solo.goBack();
		solo.clickOnImageButton(4);
		solo.clickInList(0).get(0);
		boolean isBookThere = solo.searchText(clickedBook);
		assertTrue(isBookThere);
		solo.goBackToActivity("HomeScreenActivity");
	}
	
	public void test3RemoveBookFromList(){
		solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		solo.clickOnImageButton(1);
		solo.clickOnText("Remove Books");
		solo.clickOnView((CheckBox)solo.getView(0));
		String clickedBook = solo.getText(3).getText().toString();
		solo.clickOnButton("Remove Selected");
		solo.goBack();
		solo.clickOnImageButton(4);
		solo.clickInList(0).get(0);
		boolean isBookThere = solo.searchText(clickedBook);
		assertFalse(isBookThere);
		solo.goBackToActivity("HomeScreenActivity");
	}

	private String randomListName() {
		String name = "LIST NAME " + Math.random() * (10000 - 1);
		return name;
	}

}
