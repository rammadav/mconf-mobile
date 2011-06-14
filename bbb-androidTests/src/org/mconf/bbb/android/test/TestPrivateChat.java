package org.mconf.bbb.android.test;

import org.mconf.bbb.android.Client;
import org.mconf.bbb.android.LoginPage;
import org.mconf.bbb.android.PrivateChat;
import org.mconf.bbb.android.R;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.jayway.android.robotium.solo.Solo;

public class TestPrivateChat extends ActivityInstrumentationTestCase2<LoginPage>  {

	private Solo solo;
	
	public TestPrivateChat() {
		super("org.mconf.bbb.android", LoginPage.class);
		}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.solo = new Solo(getInstrumentation(), getActivity());
		Common.addContactsToMeeting(solo, 5);
		Common.loginAsModerator(solo);
		solo.clickInList(2);
		solo.assertCurrentActivity("didn't open private chat", PrivateChat.class);
	}
	
	@Override
	protected void tearDown() throws Exception{
		try {
			this.solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		Common.removeContactsFromMeeting();
		super.tearDown();
	}
	
	public void changeChat()
	{
		int num =1;
		String first, second;
		
		first = solo.getCurrentActivity().getTitle().toString();
		num=2;
		solo.goBack();
		solo.clickInList(num);
		solo.assertCurrentActivity("didn't open the second chat", PrivateChat.class);
		second = solo.getCurrentActivity().getTitle().toString();
		//TODO can't swipe between chats
		
		
	}
	
	public void testCloseCurrentChat()
	{
		int num =1;

		solo.clickOnMenuItem(solo.getString(R.string.close_chat));
		solo.assertCurrentActivity("didn't close the chat", Client.class);
	}
	
	public void testWriteMessage()
	{
		String test = "testing message";
		int num =1;


		solo.clearEditText(0);
		solo.enterText(0, test);
		solo.clickOnButton(solo.getString(R.string.send_message));
		solo.clearEditText(0);
		assertTrue(solo.searchText(test));
	}
	
}
