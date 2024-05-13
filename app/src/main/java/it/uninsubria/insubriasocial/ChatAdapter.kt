package it.uninsubria.insubriasocial

import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) {
            /* itemView.findViewById<TextView>(R.id.text_message_sender).text = message.senderName
            itemView.findViewById<TextView>(R.id.text_message_body).text = message.body
            itemView.findViewById<TextView>(R.id.text_message_timestamp).text = message.timestamp */
        }
    }
}