package com.test.jokes.ui.jokeslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.data.jokes.models.mapped.Joke
import com.test.jokes.R
import com.test.jokes.utils.show
import kotlinx.android.synthetic.main.item_joke.view.*


class JokesAdapter : RecyclerView.Adapter<JokesAdapter.ViewHolder>() {

    private var jokesItemList = listOf<Joke>()
    private var listener: Listener? = null

    fun updateList(list: List<Joke>) {
        val diffResult = DiffUtil.calculateDiff(
            JokesDiffCallback(
                jokesItemList,
                list
            )
        )
        diffResult.dispatchUpdatesTo(this)
        jokesItemList = list
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_joke, parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(jokesItemList[position])

    override fun getItemCount(): Int = jokesItemList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(model: Joke) {
            setupView(model)
            setListeners()
        }

        private fun setupView(model: Joke) {
            with(itemView) {
                tv_joke.text = model.joke
                val likeStr = if (model.likedId > -1) {
                    R.string.str_un_like
                } else {
                    R.string.str_like
                }
                tv_like.text = context.getString(likeStr)
            }
        }

        private fun setListeners() {
            with(itemView) {
                tv_like.setOnClickListener {
                    if (tv_like.text == context.getString(R.string.str_like)) {
                        tv_like.text = context.getString(R.string.str_un_like)
                        listener?.onLikeClicked(jokesItemList[adapterPosition])
                    } else {
                        tv_like.text = context.getString(R.string.str_like)
                        listener?.onUnLikeClicked(jokesItemList[adapterPosition].id)
                    }
                }

                tv_share.setOnClickListener {
                    listener?.onShareClicked(jokesItemList[adapterPosition].joke)
                }
            }
        }
    }

    interface Listener {
        fun onLikeClicked(joke: Joke)
        fun onUnLikeClicked(id: Long)
        fun onShareClicked(joke: String)
    }
}