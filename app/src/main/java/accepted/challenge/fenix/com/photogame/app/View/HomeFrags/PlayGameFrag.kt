package accepted.challenge.fenix.com.photogame.app.View.HomeFrags


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import accepted.challenge.fenix.com.photogame.R

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayGameFrag : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_game, container, false)
    }


}
