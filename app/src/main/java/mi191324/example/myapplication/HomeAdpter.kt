package mi191324.example.myapplication

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class HomeAdpter(private val photoList: List<HomeFragment.Photo>) : RecyclerView.Adapter<HomeAdpter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)

        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = photoList[position]
        val Uri = Uri.parse(currentItem.url)

        Picasso.get()
            .load(Uri)
            .fit()
            .into(holder.imageView)
        holder.textView1.text = "写真が届きました!"
        holder.textView2.text = currentItem.datetime
    }
    override fun getItemCount() = photoList.size

    class ExampleViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val textView1: TextView = itemView.findViewById(R.id.text_view_1)
        val textView2: TextView = itemView.findViewById(R.id.text_view_2)

    }
}