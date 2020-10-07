package mi191324.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val MessageList: List<NotificationFragment.Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)

        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = MessageList[position]

        if (currentItem.kind == "out"){
            holder.textView1.text = "外出のお知らせ"
            holder.textView2.text = currentItem.text
            holder.imageView.setImageResource(R.drawable.ic_baseline_restaurant_24)
        } else {
            holder.textView1.text = currentItem.kind + "の解答"
            holder.textView2.text = currentItem.text
            holder.imageView.setImageResource(R.drawable.ic_baseline_sms_24)
        }
    }
    override fun getItemCount() = MessageList.size

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.m_item_view)
        val textView1: TextView = itemView.findViewById(R.id.m_item_text)
        val textView2: TextView = itemView.findViewById(R.id.m_item2_text)
    }
}