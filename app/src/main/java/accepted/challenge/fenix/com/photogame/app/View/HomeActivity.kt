package accepted.challenge.fenix.com.photogame.app.View

import accepted.challenge.fenix.com.photogame.Domain.moveTo
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.View.HomeFrags.LeadershipFrag
import accepted.challenge.fenix.com.photogame.app.View.HomeFrags.PlayGameFrag
import accepted.challenge.fenix.com.photogame.app.View.HomeFrags.UploadPicFrag
import accepted.challenge.fenix.com.photogame.app.ViewModel.UserViewModel
import accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory.UserViewModelFactory
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.upload_pic -> {
                updateView(UploadPicFrag())
                return@OnNavigationItemSelectedListener true
            }
            R.id.play_game -> {
                updateView(PlayGameFrag())
                return@OnNavigationItemSelectedListener true
            }
            R.id.leader_board -> {
                updateView(LeadershipFrag())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUp()
        updateView(PlayGameFrag())
        navigation.selectedItemId = R.id.play_game
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        logOutButton.setOnClickListener { logout() }
    }

    private fun setUp() {
        val userFactory = UserViewModelFactory(this)
        userViewModel = ViewModelProviders
                .of(this, userFactory)
                .get(UserViewModel::class.java)
    }

    private fun updateView(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()

    }

    private fun logout() {
        userViewModel.logOut()
        moveTo(LoginRegActivity::class.java)
        finish()
    }
}
