package accepted.challenge.fenix.com.photogame.app.View

import accepted.challenge.fenix.com.photogame.Domain.*
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.View.Base.BaseActivity
import accepted.challenge.fenix.com.photogame.app.ViewModel.UserViewModel
import accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory.UserViewModelFactory
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginRegActivity : BaseActivity() {

    private lateinit var userViewModel: UserViewModel
    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()

        if (userViewModel.isLoggedIn) moveTo(HomeActivity::class.java)
        else {
            setContentView(R.layout.activity_login)
            registerListeners()
        }
    }

    private fun registerListeners() {
        switchToLogin()
        viewChangeHandler()
    }

    private fun switchToRegister() {
        orLabel.visibility = View.GONE
        userEmail.visibility = View.VISIBLE
        setLoginListener(LoginType.Register)
        signInButton.text = getString(R.string.sign_up)
        registerButton.text = getString(R.string.back)

        registerButton.setOnClickListener {
            switchToLogin()
        }
    }

    private fun switchToLogin() {
        orLabel.visibility = View.VISIBLE
        userEmail.visibility = View.GONE
        signInButton.text = getString(R.string.sign_in)
        registerButton.text = getString(R.string.sign_up)
        setLoginListener(LoginType.Login)

        registerButton.setOnClickListener {
            switchToRegister()
        }
    }

    private fun setLoginListener(loginType: LoginType) {
        when(loginType) {
            LoginType.Login -> handleLogin()
            LoginType.Register -> handleRegister()
        }
    }

    /**
     * Makes appropriate view model call for logging in User
     */
    private fun handleLogin() {
        signInButton.setOnClickListener {
            val userNameText = userName.text.toString()
            if (areCredsValid(userNameText)) {

                loader = loader()
                loader?.show()

                disposeBag.add(userViewModel.login(userNameText)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(userViewModel::handleSuccess, userViewModel::handleFailure))
            } else toast(getString(R.string.missing_fields))
        }
    }

    /**
     * Makes appropriate view model call for signing up User
     */
    private fun handleRegister() {
        signInButton.setOnClickListener {
            val userNameText = userName.text.toString()
            val userEmailText = userEmail.text.toString()
            if (areCredsValid(userNameText, userEmailText) && isEmailValid(userEmailText)) {

                loader = loader()
                loader?.show()

                disposeBag.add(userViewModel.register(userNameText, userEmailText)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(userViewModel::handleSuccess, userViewModel::handleFailure))
            } else toast(getString(R.string.missing_fields))
        }
    }

    private fun setupView() {
        val loginFactory = UserViewModelFactory(this)
        userViewModel = ViewModelProviders
                .of(this, loginFactory)
                .get(UserViewModel::class.java)

    }


    /**
     * Registers listeners, to listen for response from the view model in case of status change
     */
    private fun viewChangeHandler() {
        val disposable = userViewModel
                .waitEventsSubscription
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {eventResp ->
                    val (showLoader, nextActivity) = eventResp

                    if (showLoader) {
                        loader = loader()
                        loader?.show()
                    } else hide(loader!!)

                    if (nextActivity != null)
                        moveTo(nextActivity); finish()
                }

        val toastDisposable = userViewModel
                .toastHandler
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    toast(it.name)
                }

        disposeBag.addAll(toastDisposable, disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}
