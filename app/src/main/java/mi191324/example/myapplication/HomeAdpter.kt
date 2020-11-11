package mi191324.example.myapplication

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class HomeAdpter(private val photoList: List<HomeFragment.Photo>) : RecyclerView.Adapter<HomeAdpter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)

        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = photoList[position]
        val calendar1 = Calendar.getInstance()
        val Uri = Uri.parse(currentItem.url)
        val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
        val datetime: Date = format.parse(currentItem.DateTime)
        calendar1.setTime(datetime)
        calendar1.add(Calendar.HOUR, 13)
        calendar1.add(Calendar.MINUTE, -19)
        val df = SimpleDateFormat("yyyy年MM月dd日HH時mm分ss秒に\n届きました")
        val gettime = calendar1.getTime()
        val datestr = df.format(gettime)

        Picasso.get()
            .load(Uri)
            .fit()
            .into(holder.imageView)
        holder.textView1.text = datestr
    }
    override fun getItemCount() = photoList.size

    class ExampleViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.m_item_view)
        val textView1: TextView = itemView.findViewById(R.id.m_item_text)
    }
}