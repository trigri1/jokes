package com.test.jokes.ui.myjokes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.data.jokes.models.mapped.Joke
import com.test.jokes.R
import com.test.jokes.ui.jokeslist.JokesDiffCallback
import kotlinx.android.synthetic.main.item_user_joke.view.*


class UserJokesAdapter : RecyclerView.Adapter<UserJokesAdapter.ViewHolder>() {

    private var jokesList = listOf<Joke>()
    private var listener: Listener? = null

    fun updateList(list: List<Joke>) {
        val diffResult = DiffUtil.calculateDiff(
            JokesDiffCallback(
                jokesList,
                list
            )
        )
        diffResult.dispatchUpdatesTo(this)
        jokesList = list
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserJokesAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_joke, parent, false
            )
        )

    override fun onBindViewHolder(holder: UserJokesAdapter.ViewHolder, position: Int) =
        holder.bindData(jokesList[position])

    override fun getItemCount(): Int = jokesList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(model: Joke) {
            setupView(model)
            setListeners()
        }

        private fun setupView(model: Joke) {
            with(itemView) {
                tv_joke.text = model.joke
            }
        }

        private fun setListeners() {
            itemView.tv_delete.setOnClickListener {
                listener?.onDeleteClicked(jokesList[adapterPosition])
            }
        }
    }

    interface Listener {
        fun onDeleteClicked(joke: Joke)
    }
}