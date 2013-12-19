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
	public static final String ADD_BUTTON = "Add Button";
	public static final String HOMESCREEN_ACTIVITY = "HomeScreenActivity";
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
		 solo.waitForActivity(HOMESCREEN_ACTIVITY);
		 solo.clickOnImageButton(2);
		 boolean isBookThere = solo.searchText(randomBookTitleName);
		 assertTrue(isBookThere);
		 solo.goBack();
	}
	
	
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
		 solo.goBackToActivity(HOMESCREEN_ACTIVITY);
	}
	
	
	public void test3DeleteBook() throws InterruptedException{
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
	
	
	private String randomBookName(){
		String name = "Book Name Test " + 1 + (int)(Math.random() * ((10000 - 1) + 1));
		
		return name;
	}

}
