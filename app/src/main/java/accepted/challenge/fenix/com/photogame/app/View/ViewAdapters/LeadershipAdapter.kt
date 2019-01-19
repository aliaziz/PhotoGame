package accepted.challenge.fenix.com.photogame.app.View.ViewAdapters

import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.Models.LeaderShipModel
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.leadership_item.view.*


class LeadershipAdapter(private val scores: ArrayList<LeaderShipModel>,
                        private val context: Context): RecyclerView.Adapter<LeadershipAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater
                = LayoutInflater.from(context)
                .inflate(R.layout.leadership_item,parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int = scores.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = scores[position].userId
        holder.dislikes.text = scores[position].dislikes.toString()
        holder.likes.text = scores[position].likes.toString()
        holder.views.text = scores[position].views.toString()
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val name = view.name!!
        val likes = view.likes!!
        val dislikes = view.dislikes!!
        val views = view.views!!
    }
}