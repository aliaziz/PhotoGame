package accepted.challenge.fenix.com.photogame.app.View.HomeFrags


import accepted.challenge.fenix.com.photogame.Domain.*
import accepted.challenge.fenix.com.photogame.Domain.managers.ErrorMessages
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.ViewModel.GamingViewModel
import accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory.GamingViewModelFactory
import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_play_game.*
import kotlinx.android.synthetic.main.fragment_play_game.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayGameFrag : Fragment() {

    @Inject
    lateinit var gamingViewModelFactory:GamingViewModelFactory

    private lateinit var gamingViewModel: GamingViewModel
    private val disposeBag = CompositeDisposable()
    private var loadDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(requireActivity())
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
        gamingViewModel =
                ViewModelProviders
                        .of(this, gamingViewModelFactory)
                        .get(GamingViewModel::class.java)

        registerListeners()
        loadDialog = requireActivity().loader()
    }

    private fun registerListeners() {
        gamingViewModel.init()
        subscriptions()
    }

    private fun subscriptions() {
        disposeBag.add(gamingViewModel.nextPic.subscribe(::loadPic) {
            requireContext().toast(Helpers.message(ErrorMessages.LOAD_ERROR))
        })

        disposeBag.add(gamingViewModel.messageSubscription.subscribe {
            requireContext().toast(it)
        })

        disposeBag.add(gamingViewModel.showLoader.subscribe {
            if (it) loadDialog?.show()
            else { loadDialog?.let { dialog -> hide(dialog) } }
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
