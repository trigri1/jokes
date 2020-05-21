package com.test.jokes.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.test.data.jokes.models.mapped.Joke

class MainDiffCallback(
    private val oldList: List<Joke>,
    private val newList: List<Joke>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].joke == newList[newItemPosition].joke
    }
}