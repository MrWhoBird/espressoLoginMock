package pl.szkolaandroida.logintest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.mock.declare
import retrofit2.Response

class LoginActivityShould : KoinTest{
    //given
    val loginApi: LoginApi = mock()
    @get:Rule
    //val rule = ActivityScenarioRule(LoginActivity::class.java)
    val rule = object: IntentsTestRule<LoginActivity>(LoginActivity::class.java){
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            declare {
                single { loginApi }
            }
        }
    }

    @Test
    fun showErrorOnEmptyUsername() {
        //when
        //onView(withText("Login")).perform(click())
        onView(withId(R.id.loginButton)).perform(click())
        //then
        onView(withId(R.id.usernameError)).check(matches(withText(R.string.username_cant_be_empty)))
    }

    @Test
    fun showErrorOnTooShortPassword() {
        onView(withId(R.id.password)).perform(replaceText("xxx"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.passwordError)).check(matches(withText(R.string.password_cant_be_too_short)))
    }

    @Test
    fun doToMainAfterLogin() {

        runBlocking {
            whenever(loginApi.getLogin("test","pass")).thenReturn(Response.success(
                LoginResponse(
                    "test",
                    "id",
                    "token"
                )
            ))
        }

        onView(withId(R.id.username)).perform(replaceText("test"))
        onView(withId(R.id.password)).perform(replaceText("pass"))
        onView(withId(R.id.loginButton)).perform(click())
        //Thread.sleep(5000L)
        intended(hasComponent(MainActivity::class.java.name))
    }

    //    @Before
//    fun setUp() {
//    }
//
//    @After
//    fun tearDown() {
//    }
}