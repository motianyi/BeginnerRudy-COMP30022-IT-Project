package com.honegroupp.utility

import android.content.Intent
import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.honegroupp.familyRegister.R
import com.honegroupp.familyRegister.backend.DatabaseManager.FirebaseDatabaseManager
import com.honegroupp.familyRegister.utility.HashUtil
import com.honegroupp.familyRegister.view.family.FamilyCreateActivity
import com.honegroupp.familyRegister.view.home.HomeActivity
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.runner.RunWith

/**
 * This class is responsible for unit test the SearchActivity class
 *
 *
 * @author Haoran Duan
 * */
@RunWith(AndroidJUnit4::class)
class SearchActivityTest {
    companion object {
        // variables you initialize for the class later in the @BeforeClass method:
        lateinit var intent: Intent

        @BeforeClass
        @JvmStatic
        fun setup() {
            // things to execute once and keep around for the class
            intent = Intent()
            val uid = "qqqqqqqq@qq=com"
            val username = "qqqqqqqq"
            intent.putExtra("UserID", uid)
            intent.putExtra("UserName", username)

            Intents.init()
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            // clean up after this class, leave nothing dirty behind
        }
    }

    // The start test activity of this test class.
    @get:Rule
    val familyCreateActivityRule = ActivityTestRule(FamilyCreateActivity::class.java, false, false)

    /**
     * Test if the FamilyCreateActivity launches successfully.
     * */
    @Test
    fun familyActivityLaunchesSuccessfully() {
        familyCreateActivityRule.launchActivity(intent)
    }

    /**
     * Test when the user enter empty family input, whether the app would show a toast to remind
     * user for entering family name
     * */
    @Test
    fun whenEnterEmptyUserName_1() {
        // 1 launch FamilyCreateActivity
        familyCreateActivityRule.launchActivity(intent)

        // 2 enter empty family name and nothing else

        // 3 click button
        Espresso.onView(ViewMatchers.withId(R.id.familyCreateConfirm))
            .perform(ViewActions.click())

        // 4 check whether show a toast message with string R.string.type_family_name
        Espresso.onView(ViewMatchers.withText(R.string.type_family_name)).inRoot(
            RootMatchers.withDecorView(
                CoreMatchers.not(
                    familyCreateActivityRule.activity.window.decorView
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // wait until the toast message disappear
        ThreadController.stopForNMilliseconds(ThreadController.SHORT_TOAST_WAITING)
    }

    /**
     * Test when the user enter empty family input, whether the app would show a toast to remind
     * user for entering family name
     * */
    @Test
    fun whenEnterEmptyUserName_2() {
        // 1 launch FamilyCreateActivity
        familyCreateActivityRule.launchActivity(intent)

        // 2 enter family name with a lot of blank space nothing else
        Espresso.onView(ViewMatchers.withId(R.id.edit_text_family_name))
            .perform(ViewActions.replaceText("        "))

        // 3 click button
        Espresso.onView(ViewMatchers.withId(R.id.familyCreateConfirm))
            .perform(ViewActions.click())

        // 4 check whether show a toast message with string R.string.type_family_name
        Espresso.onView(ViewMatchers.withText(R.string.type_family_name)).inRoot(
            RootMatchers.withDecorView(
                CoreMatchers.not(
                    familyCreateActivityRule.activity.window.decorView
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // wait until the toast message disappear
        ThreadController.stopForNMilliseconds(ThreadController.SHORT_TOAST_WAITING)
    }

    /**
     * Test when the user enter empty password input, whether the app would show a toast to remind
     * user for entering password
     * */
    @Test
    fun whenEnterEmptyPassword_1() {
        // 1 launch FamilyCreateActivity
        familyCreateActivityRule.launchActivity(intent)

        // 2 enter family name with a lot of blank space nothing else
        Espresso.onView(ViewMatchers.withId(R.id.edit_text_family_name))
            .perform(ViewActions.replaceText("family name"))

        // 3 click button
        Espresso.onView(ViewMatchers.withId(R.id.familyCreateConfirm))
            .perform(ViewActions.click())

        // 4 check whether show a toast message with string R.string.type_family_name
        Espresso.onView(ViewMatchers.withText(R.string.please_enter_the_password)).inRoot(
            RootMatchers.withDecorView(
                CoreMatchers.not(
                    familyCreateActivityRule.activity.window.decorView
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // wait until the toast message disappear
        ThreadController.stopForNMilliseconds(ThreadController.SHORT_TOAST_WAITING)
    }

    /**
     * Test when the user enter empty password input, whether the app would show a toast to remind
     * user for entering password
     * */
    @Test
    fun whenEnterEmptyTwicePassword() {
        // 1 launch FamilyCreateActivity
        familyCreateActivityRule.launchActivity(intent)

        // 2 enter family name with a lot of blank space nothing else
        Espresso.onView(ViewMatchers.withId(R.id.edit_text_family_name))
            .perform(ViewActions.replaceText("family name"))
        Espresso.onView(ViewMatchers.withId(R.id.edit_text_family_password))
            .perform(ViewActions.replaceText("password"))

        // 3 click button
        Espresso.onView(ViewMatchers.withId(R.id.familyCreateConfirm))
            .perform(ViewActions.click())

        // 4 check whether show a toast message with string R.string.type_family_name
        Espresso.onView(ViewMatchers.withText(R.string.please_enter_the_password_again)).inRoot(
            RootMatchers.withDecorView(
                CoreMatchers.not(
                    familyCreateActivityRule.activity.window.decorView
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // wait until the toast message disappear
        ThreadController.stopForNMilliseconds(ThreadController.SHORT_TOAST_WAITING)
    }

    /**
     * Test when the user enter all the input correct, family is created as expected.
     * */
    @Test
    fun whenUserCreateFamilyWithAllValidInput() {
        val familyName = "family name"
        val password = "password"
        // 1 launch FamilyCreateActivity
        familyCreateActivityRule.launchActivity(intent)

        // 2 enter family name with a lot of blank space nothing else
        Espresso.onView(ViewMatchers.withId(R.id.edit_text_family_name))
            .perform(ViewActions.replaceText(familyName))
        Espresso.onView(ViewMatchers.withId(R.id.edit_text_family_password))
            .perform(ViewActions.replaceText(password))
        Espresso.onView(ViewMatchers.withId(R.id.edit_text_family_password_again))
            .perform(ViewActions.replaceText(password))

        // 3 click button
        Espresso.onView(ViewMatchers.withId(R.id.familyCreateConfirm))
            .perform(ViewActions.click())

        // 4 check whether show a toast message with string R.string.type_family_name
        Espresso.onView(ViewMatchers.withText(R.string.family_created_successfully)).inRoot(
            RootMatchers.withDecorView(
                CoreMatchers.not(
                    familyCreateActivityRule.activity.window.decorView
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // 5 check the family on the database has same name and password is correct
        val path = FirebaseDatabaseManager.FAMILY_PATH + familyCreateActivityRule.activity.currUid + "/"
        val databaseRef = FirebaseDatabase.getInstance().getReference(path)

        // retrieve data
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //Don't ignore errors!
                Log.d("TAG", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                // remove this listener, since we only need to use DataSnapshot once
                databaseRef.removeEventListener(this)

                // family name in the database should same as that user typed
                Assert.assertEquals(familyName, p0.child("familyName").value as String)

                // password in the database should match the user's password input
                Assert.assertEquals(HashUtil.applyHash(password), p0.child("password").value as String)
            }
        })

        // wait until the toast message disappear
        ThreadController.stopForNMilliseconds(ThreadController.SHORT_TOAST_WAITING)

        // Check if intent with FamilyJoinActivity it's been launched
        Intents.intended(IntentMatchers.hasComponent(HomeActivity::class.java.name))


    }
}