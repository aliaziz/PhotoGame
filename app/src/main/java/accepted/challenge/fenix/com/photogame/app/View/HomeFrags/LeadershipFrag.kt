package accepted.challenge.fenix.com.photogame.app.View.HomeFrags


import accepted.challenge.fenix.com.photogame.Domain.ErrorMessages
import accepted.challenge.fenix.com.photogame.Domain.toast
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.Models.LeaderShipModel
import accepted.challenge.fenix.com.photogame.app.View.ViewAdapters.LeadershipAdapter
import accepted.challenge.fenix.com.photogame.app.ViewModel.GamingViewModel
import accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory.GamingViewModelFactory
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_leadership.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class LeadershipFrag : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gamingViewModel: GamingViewModel
    private lateinit var parentView: View
    private var leadershipAdapter: LeadershipAdapter? = null
    private var scores: ArrayList<LeaderShipModel> = ArrayList()
    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }

    private fun setup() {
        val gamingViewModelFactory = GamingViewModelFactory(requireContext())
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

        }, { requireContext().toast(ErrorMessages.LOAD_ERROR.name)}))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}

