package accepted.challenge.fenix.com.photogame.app.View.HomeFrags


import accepted.challenge.fenix.com.photogame.Domain.Helpers
import accepted.challenge.fenix.com.photogame.Domain.managers.ErrorMessages
import accepted.challenge.fenix.com.photogame.Domain.managers.Keys
import accepted.challenge.fenix.com.photogame.Domain.toast
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.Models.LeaderShipModel
import accepted.challenge.fenix.com.photogame.app.View.ViewAdapters.LeadershipAdapter
import accepted.challenge.fenix.com.photogame.app.ViewModel.GamingViewModel
import accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory.GamingViewModelFactory
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_leadership.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class LeadershipFrag : Fragment() {

    private lateinit var gamingViewModelFactory:GamingViewModelFactory
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gamingViewModel: GamingViewModel
    private lateinit var parentView: View
    private var leadershipAdapter: LeadershipAdapter? = null
    private var scores: ArrayList<LeaderShipModel> = ArrayList()
    private val disposeBag = CompositeDisposable()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (arguments?.getSerializable(Keys.FACTORY_KEY) as GamingViewModelFactory).let {
            gamingViewModelFactory = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(requireActivity())
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onResume() {
        super.onResume()
        loadScores()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        parentView = inflater.inflate(R.layout.fragment_leadership, container, false)
        registerAdapter()
        return parentView
    }

    private fun registerAdapter() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        parentView.leadershiplist.layoutManager = linearLayoutManager
        parentView.leadershiplist
                .addItemDecoration(DividerItemDecoration(requireContext(), linearLayoutManager.orientation ))
    }

    private fun setup() {
        gamingViewModel =
                ViewModelProviders
                        .of(this, gamingViewModelFactory)
                        .get(GamingViewModel::class.java)

        loadScores()
    }

    private fun loadScores() {
        disposeBag.add(gamingViewModel.getScores().subscribe({
            scores.clear()
            scores.addAll(it)

            if (leadershipAdapter == null) {
                leadershipAdapter = LeadershipAdapter(scores, requireContext())
                parentView.leadershiplist.adapter = leadershipAdapter
            } else leadershipAdapter?.notifyDataSetChanged()

        }, { requireContext().toast(Helpers.message(ErrorMessages.LOAD_ERROR))}))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}

