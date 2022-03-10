package com.example.listgames.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listgames.R
import com.example.listgames.model.dto.GameItemDTO
import com.squareup.picasso.Picasso

class GamesAdapter(
    private var listener: OnCoverClickListener,
    private var games: MutableList<GameItemDTO> = mutableListOf()
) :
    RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.games_item, parent, false)
        return GameViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.itemView.tag = game
        if (game != null) {
            if (game.genres != null) {
                var newGenres = ""
                for (i in 0..game.genres.size - 1) {
                    newGenres += game.genres.get(i).name
                    if (game.genres.size != i + 1) {
                        newGenres += ", "
                    }
                }
                holder.textViewGameGenresName.text =
                    "Género: " + newGenres.substring(0, newGenres.length - 1)
            }
            if (game.screenshots != null) {
                for (i in 0..game.screenshots.size - 1) {
                    if (game.screenshots[i].image_id.isNotEmpty() && game.id.equals(
                            game.screenshots.get(
                                i
                            ).game
                        )
                    ) {
                        Picasso.get().load(
                            "https:///images.igdb.com/igdb/image/upload/t_thumb/" +
                                    game.screenshots.get(i).image_id + ".jpg"
                        ).into(holder.imageViewSmallCover)
                        break
                    }

                }
            }

            holder.textViewGameTitle.text = game.name
        }
    }

    class GameViewHolder(itemView: View, private val listener: OnCoverClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var imageViewSmallCover: ImageView = itemView.findViewById(R.id.imageViewSmallCover)
        var textViewGameTitle: TextView = itemView.findViewById(R.id.textViewGameTitle)
        var textViewGameGenresName: TextView = itemView.findViewById(R.id.textViewGameGenresName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onCoverClick(adapterPosition, itemView.tag as GameItemDTO)
        }
    }

    interface OnCoverClickListener {
        fun onCoverClick(position: Int, game: GameItemDTO)
    }
}
