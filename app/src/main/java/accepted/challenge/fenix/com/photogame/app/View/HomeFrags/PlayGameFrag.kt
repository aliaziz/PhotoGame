package accepted.challenge.fenix.com.photogame.app.View.HomeFrags


import accepted.challenge.fenix.com.photogame.Domain.ErrorMessages
import accepted.challenge.fenix.com.photogame.Domain.toast
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.ViewModel.GamingViewModel
import accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory.GamingViewModelFactory
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_play_game.*
import kotlinx.android.synthetic.main.fragment_play_game.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayGameFrag : Fragment() {

    private lateinit var gamingViewModel: GamingViewModel
    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_play_game, container, false)
        view.dislike.setOnClickListener { gamingViewModel.dislikePic() }
        view.like.setOnClickListener { gamingViewModel.likePic() }
        return view
    }

    private fun setup() {
        val gamingViewModelFactory = GamingViewModelFactory(requireContext())
        gamingViewModel =
                ViewModelProviders
                        .of(this, gamingViewModelFactory)
                        .get(GamingViewModel::class.java)

        registerListeners()
    }

    private fun registerListeners() {
        gamingViewModel.init()
        subscriptions()
    }

    private fun subscriptions() {
        disposeBag.add(gamingViewModel.nextPic.subscribe(::loadPic) {
            requireContext().toast(ErrorMessages.LOAD_ERROR.name)
        })

        disposeBag.add(gamingViewModel.messageSubscription.subscribe {
            requireContext().toast(it)
        })
    }

    private fun loadPic(url: String) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.camera)
                .into(picture)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }

}
