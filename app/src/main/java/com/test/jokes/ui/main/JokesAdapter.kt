package com.test.jokes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.data.jokes.models.mapped.Joke
import com.test.jokes.R


class JokesAdapter : RecyclerView.Adapter<JokesAdapter.ViewHolder>() {

    private var jokesItemList = listOf<Joke>()
    private var listener: Listener? = null

    fun updateList(list: List<Joke>) {
        val diffResult = DiffUtil.calculateDiff(MainDiffCallback(jokesItemList, list))
        diffResult.dispatchUpdatesTo(this)
        jokesItemList = list
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_joke, parent, false
            )
        )

    override fun onBindViewHolder(holder: JokesAdapter.ViewHolder, position: Int) =
        holder.bindData(jokesItemList[position])

    override fun getItemCount(): Int = jokesItemList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(model: Joke) {
            setupView(model)
            setListeners()
        }

        private fun setupView(model: Joke) {
            with(itemView) {
            }
        }

        private fun setListeners() {
        }
    }

    interface Listener {
        fun onAmountEntered(amount: Float = 1f)
    }
}