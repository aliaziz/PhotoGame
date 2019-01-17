package accepted.challenge.fenix.com.photogame.app.View

import accepted.challenge.fenix.com.photogame.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import javax.inject.Inject

class Home @Inject constructor(): AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
