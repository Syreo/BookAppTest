package com.example.bookapptest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.cycle7.bookapp.HomeScreenActivity;
import com.cycle7.bookapp.R;
import com.jayway.android.robotium.solo.Solo;

public class ListTest extends ActivityInstrumentationTestCase2<HomeScreenActivity> {
	
		private Solo solo;
		public static final String CANCEL_BUTTON = "Cancel";
		public static final String SAVE_BUTTON = "Save";
		public static final String EMPTY_STRING_TOAST = "Blank or non-unique list names are not allowed";
		public static final String DIALOG_TEXT_LABEL = "List Name:";
		public static final int CREAT_LIST_BUTTON_INDEX = 3;
		
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
	
	/*
	 * Tests that the dialog fragment for creating a list appears on click and that the text label is correct
	 */
	
	public void testDialogText(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(CREAT_LIST_BUTTON_INDEX);
		 solo.waitForDialogToOpen();
		 String getDialogText = solo.getText(0).getText().toString();
		 assertEquals(DIALOG_TEXT_LABEL, getDialogText);
		 solo.clickOnButton(CANCEL_BUTTON);
	}
	/*
	 * Tests that the buttons are correctly labeled "Cancel" and "Save"
	 */
	public void testButtonText(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(CREAT_LIST_BUTTON_INDEX);
		 solo.waitForDialogToOpen();
		 String firstButtonText = solo.getButton(0).getText().toString();
		 String secondButtonText = solo.getButton(1).getText().toString();
		 assertEquals(SAVE_BUTTON, firstButtonText);
		 assertEquals(CANCEL_BUTTON, secondButtonText);  
		 solo.clickOnButton(CANCEL_BUTTON);
	}
	/*
	 * Tests that the empty string toast appears when you try to submit an empty string for a list name
	 */
	public void testEmptyStringToast(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(CREAT_LIST_BUTTON_INDEX);
		 solo.waitForDialogToOpen();
		 solo.clickOnButton(SAVE_BUTTON);
		 boolean didToastAppear = solo.searchText(EMPTY_STRING_TOAST);
		 assertTrue(didToastAppear);
	}
	/*
	 * Tests that a list is successfully created when inputting a unique string for a list name.
	 */
	
	public void test1CreateList(){
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(CREAT_LIST_BUTTON_INDEX);
		 String randomListName = randomListName();
		 solo.waitForDialogToOpen();
		 EditText listInput = (EditText) solo.getView(R.id.listNameInput);
		 solo.enterText(listInput, randomListName);
		 solo.clickOnButton(SAVE_BUTTON);
		 solo.clickOnImageButton(4);
		 boolean isListVisible = solo.searchText(randomListName);
		 assertTrue(isListVisible);
		 solo.goBack();
		 
	}
	/*
	 * Tests that a toast is displayed when a non-unique name is entered
	 */
	public void testCreateListWithNonUniqueName(){
		 String listName = randomListName();
		 solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(CREAT_LIST_BUTTON_INDEX);
		 solo.waitForDialogToOpen();
		 EditText listInput = (EditText) solo.getView(R.id.listNameInput);
		 solo.enterText(listInput, listName);
		 solo.clickOnButton(SAVE_BUTTON);
		 solo.clickOnImageButton(3);
		 EditText listInputCopy = (EditText) solo.getView(R.id.listNameInput);
		 solo.waitForDialogToOpen();
		 solo.enterText(listInputCopy, listName);
		 solo.clickOnButton(SAVE_BUTTON);
		 boolean isToastThere = solo.searchText(EMPTY_STRING_TOAST);
		 assertTrue(isToastThere);
		 solo.clickOnButton(CANCEL_BUTTON);
	}
	/*
	 * Test deleting book lists.
	 */
	public void testDeleteList(){
		solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		solo.clickOnImageButton(CREAT_LIST_BUTTON_INDEX);
		String randomListName = randomListName();
		solo.waitForDialogToOpen();
		EditText listInput = (EditText) solo.getView(R.id.listNameInput);
		solo.enterText(listInput, randomListName);
		solo.clickOnButton(SAVE_BUTTON);
		solo.clickOnImageButton(4);
		TextView listName2 = (TextView)solo.getText(randomListName);
		int id = listName2.getId();
		CheckBox checkBox = (CheckBox)solo.getView(id + 1);
		solo.clickOnView(checkBox);
		solo.clickOnButton("Delete Selected");
		solo.waitForDialogToOpen();
		solo.clickOnButton("Yes");
		boolean isListThere = solo.searchText(randomListName);
		assertFalse(isListThere);
	}
	
	/*
	 * generates a random list name because list names must be unique
	 */
	private String randomListName() {
		String name = "LIST NAME " + Math.random() * (1000 - 1);
		return name;
	}

}
