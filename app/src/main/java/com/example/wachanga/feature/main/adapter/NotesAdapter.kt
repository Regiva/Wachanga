package com.example.wachanga.feature.main.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wachanga.databinding.NotesAdapterItemBinding
import com.example.wachanga.domain.model.Note

class NotesAdapter(
    private val onItemClickedListener: (Note) -> Unit,
    private val onCheckboxClickedListener: (Note) -> Unit,
    private val completed: Boolean,
) : ListAdapter<Note, NotesAdapter.Holder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            NotesAdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            completed,
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        getItem(position)?.let { item ->
            holder.bindAll(item)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            payloads.forEach { payload ->
                when (payload) {
                    is Payload.Checked -> holder.bindChecked(payload.done)
                    is Payload.Content -> holder.bindContent(payload.content)
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    inner class Holder(
        private val binding: NotesAdapterItemBinding,
        private val completed: Boolean,
    ) : RecyclerView.ViewHolder(binding.root) {

        internal fun bindAll(item: Note) {
            itemView.setOnClickListener {
                onItemClickedListener.invoke(item)
            }
            bindChecked(item.done)
            bindContent(item.content)
            binding.checkbox.setOnClickListener {
                onCheckboxClickedListener(item)
            }
        }

        internal fun bindChecked(checked: Boolean) {
            binding.checkbox.isChecked = checked
        }

        internal fun bindContent(content: String) {
            if (completed) {
                binding.tvContent.apply {
                    text = content
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                binding.tvContent.alpha = 0.5f
            } else {
                binding.tvContent.text = content
                binding.tvContent.alpha = 1f
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.content == newItem.content && oldItem.done == newItem.done
        }

        override fun getChangePayload(oldItem: Note, newItem: Note): Any {
            return setOfNotNull(
                if (oldItem.done != newItem.done) {
                    Payload.Checked(newItem.done)
                } else {
                    null
                },
                if (oldItem.content != newItem.content) {
                    Payload.Content(newItem.content)
                } else {
                    null
                },
            )
        }
    }

    sealed interface Payload {
        @JvmInline
        value class Checked(val done: Boolean) : Payload

        @JvmInline
        value class Content(val content: String) : Payload
    }
}
