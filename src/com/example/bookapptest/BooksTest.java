package com.example.bookapptest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.cycle7.bookapp.AddBookActivity;
import com.cycle7.bookapp.HomeScreenActivity;
import com.cycle7.bookapp.R;
import com.jayway.android.robotium.solo.Solo;

public class BooksTest extends ActivityInstrumentationTestCase2<HomeScreenActivity> {

	private Solo solo;
	private EditText bookTitle;
	private EditText bookAuthor;
	private EditText bookPages;
	private RatingBar bookRating;
	private EditText bookReview;
	private String randomBookTitleName = "";
	private static final String TEST_BOOK_AUTHOR = "TEST AUTHOR";
	private static final String TEST_BOOK_REVIEW = "TEST REVIEW";
	public static final String TEST_BOOK_PAGES = "100";
	public static final int TEST_BOOK_RATING = 3;
	public static final String ADD_BUTTON = "Add";
	public static final String BOOK_APP = "bookapp";
	public static final String EDIT_TEST_TITLE = "TEST TITLE EDITED";
	public static final String EDIT_TEST_AUTHOR = "TEST AUTHOR EDITED";
	public static final String EDIT_TEST_REVIEW = "TEST REVIEW EDITED";
	public static final String EDIT_TEST_PAGES = "1000";
	public static final int EDIT_RATING_BAR = 5;
	public static final String EDIT_BUTTON = "Edit";
	public static final String DELETE_BUTTON = "Delete";
	public static final String YES_BUTTON = "Yes";
	public static final String NO_BUTTON = "No";
	public static final String REMOVE_BOOKS_TAB = "Remove Books";
	public static final String REMOVE_SELECTED_BUTTON = "Remove Selected";
	
	public BooksTest() {
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
	 * tests that a new book can be created
	 */
	public void test1CreateNewBook(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(0);
		 bookTitle = (EditText) solo.getView(R.id.bookTitle);
		 bookAuthor = (EditText)solo.getView(R.id.bookAuthor);
		 bookPages = (EditText)solo.getView(R.id.bookPages);
		 bookRating = (RatingBar)solo.getView(R.id.bookRating);
		 bookReview = (EditText)solo.getView(R.id.bookReview);
		 randomBookTitleName =  randomBookName();
		 solo.enterText(bookTitle, randomBookTitleName);
		 solo.enterText(bookAuthor, TEST_BOOK_AUTHOR);
		 solo.enterText(bookPages, TEST_BOOK_PAGES);
		 solo.setProgressBar(bookRating, TEST_BOOK_RATING);
		 solo.enterText(bookReview, TEST_BOOK_REVIEW);
		 solo.clickOnCheckBox(0);
		 solo.clickOnButton(ADD_BUTTON);
		 solo.waitForActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
		 solo.clickOnImageButton(2);
		 boolean isBookThere = solo.searchText(randomBookTitleName);
		 assertTrue(isBookThere);
		 solo.goBack();
	}

	/*
	 * Tests that every field that a book has can be edited and the changes will be saved
	 */
	
	public void test2EditBook(){
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(2);
		 solo.clickInList(0).get(0).getText().toString();
		 solo.waitForFragmentById(R.id.viewPager);
		 CheckBox readCheckBox = (CheckBox) solo.getView(R.id.bookReadCheckbox);
		 boolean checkbox = readCheckBox.isChecked();
		 solo.clickOnButton(EDIT_BUTTON);
		 bookTitle = (EditText) solo.getView(R.id.bookTitleEdit);
		 bookAuthor = (EditText)solo.getView(R.id.bookAuthorEdit);
		 bookPages = (EditText)solo.getView(R.id.bookPagesEdit);
		 bookRating = (RatingBar)solo.getView(R.id.bookRatingEdit);
		 bookReview = (EditText)solo.getView(R.id.bookReviewEdit);
		 solo.clearEditText(bookTitle);
		 solo.clearEditText(bookAuthor);
		 solo.clearEditText(bookPages);
		 solo.clearEditText(bookReview);
		 solo.enterText(bookTitle, EDIT_TEST_TITLE);
		 solo.enterText(bookAuthor, EDIT_TEST_AUTHOR);
		 solo.enterText(bookPages, EDIT_TEST_PAGES);
		 solo.setProgressBar(bookRating, EDIT_RATING_BAR);
		 solo.enterText(bookReview, EDIT_TEST_REVIEW);
		 solo.clickOnCheckBox(0);
		 solo.clickOnButton("Save");
		 RatingBar rating = (RatingBar)solo.getView(R.id.bookRating);
		 boolean isAuthorTextChanged = solo.searchText(EDIT_TEST_AUTHOR);
		 boolean isReviewTextChanged = solo.searchText(EDIT_TEST_REVIEW);
		 boolean isTitleTextChanged = solo.searchText(EDIT_TEST_TITLE);
		 boolean isPageTextChanged = solo.searchText(EDIT_TEST_PAGES);
		 boolean checkboxEdited = readCheckBox.isChecked();
		 assertTrue(isPageTextChanged);
		 assertTrue(isAuthorTextChanged);
		 assertTrue(isReviewTextChanged);
		 assertTrue(isTitleTextChanged);
		 assertTrue(rating.getProgress() == EDIT_RATING_BAR);
		 assertTrue(checkbox != checkboxEdited);
		 solo.goBackToActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
	}

	/*
	 * Tests adding a book to a list
	 */
	public void test3AddBookToList(){
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
		solo.goBackToActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
	}
	/*
	 * Tests removing a book from a list
	 */
	public void test4RemoveBookFromList(){
		solo.assertCurrentActivity(HomeScreenTest.BOOK_APP, HomeScreenActivity.class);
		solo.clickOnImageButton(1);
		solo.clickOnText(REMOVE_BOOKS_TAB);
		solo.clickOnView((CheckBox)solo.getView(0));
		String clickedBook = solo.getText(3).getText().toString();
		solo.clickOnButton(REMOVE_SELECTED_BUTTON);
		solo.goBack();
		solo.clickOnImageButton(4);
		solo.clickInList(0).get(0);
		boolean isBookThere = solo.searchText(clickedBook);
		assertFalse(isBookThere);
		solo.goBackToActivity(HomeScreenTest.HOMESCREEN_ACTIVITY);
	}
	/*
	 * Tests that a book can be deleted
	 */
	
	public void test5DeleteBook() throws InterruptedException{
		 solo.assertCurrentActivity(BOOK_APP, HomeScreenActivity.class);
		 solo.clickOnImageButton(2);
		 String listItemClicked = solo.clickInList(0).get(0).getText().toString();
		 solo.waitForFragmentById(R.id.viewPager);
		 solo.clickOnButton(DELETE_BUTTON);
		 solo.waitForDialogToOpen(3000);
		 solo.clickOnButton(YES_BUTTON);
		 boolean isBookThere = solo.searchText(listItemClicked);
		 assertFalse(isBookThere);
		 solo.goBack();
	}
	
	/*
	 * generate a random book name because book names must be unique
	 */
	
	private String randomBookName(){
		String name = "Book Name Test " + 1 + (int)(Math.random() * ((10000 - 1) + 1));
		
		return name;
	}

}
