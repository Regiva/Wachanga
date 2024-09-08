package com.example.wachanga.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wachanga.databinding.NotesAdapterItemBinding
import com.example.wachanga.domain.model.Note

class NotesAdapter(
    private val onItemClickedListener: (Note) -> Unit,
    private val onCheckboxClickedListener: (Note) -> Unit,
) : RecyclerView.Adapter<NotesAdapter.Holder>() {

    private val itemsDiffer = AsyncListDiffer(this, DiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            NotesAdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        itemsDiffer.currentList.getOrNull(position)?.let { item ->
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

    override fun getItemCount(): Int = itemsDiffer.currentList.size

    fun submitItems(notes: List<Note>) {
        itemsDiffer.submitList(notes)
    }

    inner class Holder(
        val binding: NotesAdapterItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { view ->
                itemsDiffer.currentList.getOrNull(bindingAdapterPosition)?.let { note ->
                    notifyItemChanged(bindingAdapterPosition)
                    onItemClickedListener.invoke(note)
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }

        internal fun bindAll(item: Note) {
            val checked = getChecked(item)
            val content = getContent(item)
            binding.checkbox.isChecked = checked
            binding.checkbox.setOnClickListener {
                onCheckboxClickedListener(item)
            }
            binding.tvContent.text = content
        }

        internal fun bindChecked(checked: Boolean) {
            binding.checkbox.isChecked = checked
        }

        internal fun bindContent(content: String) {
            binding.tvContent.text = content
        }
    }

    private fun getChecked(item: Note): Boolean {
        return item.done
    }

    private fun getContent(item: Note): String {
        return item.content
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.done == newItem.done
                    && oldItem.content == newItem.content
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
