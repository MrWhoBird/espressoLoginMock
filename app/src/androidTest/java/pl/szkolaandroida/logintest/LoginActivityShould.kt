package pl.szkolaandroida.logintest

import androidx.test.espresso.intent.rule.IntentsTestRule
import com.nhaarman.mockitokotlin2.mock

import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.mock.declare

class LoginActivityShould : KoinTest{
    val loginApi: LoginApi = mock()
    @get:Rule
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
        LoginRobot(loginApi).apply {
            clickLoginBtn()
            usernameCantBeEmpty()
        }
    }

    @Test
    fun showErrorOnTooShortPassword() {
        LoginRobot(loginApi).apply {
            enterUsername("test")
            enterPassword("xxx")
            clickLoginBtn()
            passwordCantBeTooShort()
        }
    }

    @Test
    fun goToMainAfterLogin() {
        LoginRobot(loginApi).apply {
            mockSuccessfulLogin()
            enterUsername("test")
            enterPassword("pass")
            clickLoginBtn()
            checkGoToMain()
        }

    }
}