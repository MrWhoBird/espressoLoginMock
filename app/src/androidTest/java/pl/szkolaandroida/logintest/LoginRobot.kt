package pl.szkolaandroida.logintest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class LoginRobot(private val loginApi: LoginApi) {

    fun mockSuccessfulLogin() {
        runBlocking {
            whenever(loginApi.getLogin("test","pass")).thenReturn(
                Response.success(
                LoginResponse(
                    "test",
                    "id",
                    "token"
                )
            ))
        }
    }

    fun clickLoginBtn() {
        onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())
    }

    fun enterUsername(username: String) {
        onView(ViewMatchers.withId(R.id.username))
            .perform(ViewActions.replaceText(username))
    }

    fun enterPassword(password: String) {
        onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.replaceText(password))
    }

    fun passwordCantBeTooShort() {
        onView(ViewMatchers.withId(R.id.passwordError))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.password_cant_be_too_short)))
    }

    fun usernameCantBeEmpty() {
        onView(ViewMatchers.withId(R.id.usernameError))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.username_cant_be_empty)))
    }
}