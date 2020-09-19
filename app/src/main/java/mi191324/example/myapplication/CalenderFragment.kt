package mi191324.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_calender.*
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalenderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalenderFragment<Boolen> : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    data class DayRequest(
        val date: String
    )

    fun CalenderSelect(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.calendarView -> {
                FirstView.setImageResource(R.drawable.ic_today_black_24dp)
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View = inflater.inflate(R.layout.fragment_calender, container, false)
        val calenderView: CalendarView =  View.findViewById(R.id.calendarView)
        val FirstView: ImageView = View.findViewById(R.id.FirstView)
        val SecondView: ImageView = View.findViewById(R.id.SecondView)
        val ThirdView: ImageView = View.findViewById(R.id.ThirdView)
        val text: TextView = View.findViewById(R.id.FirstText)
        val text_2: TextView = View.findViewById(R.id.SecondText)
        val text_3: TextView = View.findViewById(R.id.ThirdText)
        val baseUrl = "https://asia-northeast1-farmily-meal.cloudfunctions.net/Calender_phot"
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val requestAdapter = moshi.adapter(CalenderFragment.DayRequest::class.java)
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
        /*現在日時を取得*/
        val date = Date()
        val format = SimpleDateFormat("yyyyMd", Locale.getDefault())
        val now = format.format(date)
        text.setText(now)/*後で削除*/
        /*現日時に対応する写真を習得*/
        var nowday_1 = now.toString() + "_1"
        var nowday_2 = now.toString() + "_2"
        var nowday_3 = now.toString() + "_3"
        val Daydate_1 = CalenderFragment.DayRequest(date = nowday_1)
        val Daydate_2 = CalenderFragment.DayRequest(date = nowday_2)
        val Daydate_3 = CalenderFragment.DayRequest(date = nowday_3)
        text.setText(nowday_1)
        val httpAsync = (baseUrl)
            .httpPost()
            .header(header).body(requestAdapter.toJson(Daydate_1)) /*今日の朝ご飯の画像を取得*/
            .responseString{ request, response, result ->
                when(result){
                    is Result.Success -> {
                        val photo = result.get()
                        val photouri:Uri = Uri.parse(photo)
                        FirstView.setImageURI(photouri)
                        Picasso.get()
                            .load(photouri)
                            .into(FirstView)
                        text.setText(photo)
                        Log.d("send", nowday_1)
                    }
                    is Result.Failure -> {
                        Log.d("miss", "miss")
                    }
                }
            }
            .header(header).body(requestAdapter.toJson(Daydate_2)) /*今日の昼ごはんの画像習得*/
            .responseString(){ request, response, result ->
                Log.d("hoge", result.toString())
                when(result){
                    is Result.Success -> {
                        val photo: String = result.get()
                        val photouri:Uri = Uri.parse(photo)
                        Picasso.get()
                            .load(photouri)
                            .into(SecondView)
                        text_2.setText(photo)
                        Log.d("send", nowday_2)
                    }
                    is Result.Failure -> {
                        Log.d("miss", "miss")
                    }
                }
            }
            .header(header).body(requestAdapter.toJson(Daydate_3)) /*今日の晩御飯の画像を習得*/
            .responseString(){ request, response, result ->
                Log.d("hoge", result.toString())
                when(result){
                    is Result.Success -> {
                        val photo = result.get()
                        val photouri:Uri = Uri.parse(photo)
                        Picasso.get()
                            .load(photouri)
                            .into(ThirdView)
                        text_3.setText(photo)
                        Log.d("send", nowday_3)
                    }
                    is Result.Failure -> {
                        Log.d("miss", "miss")
                    }
                }
            }
        httpAsync.join()
        /*カレンダータップ時日時情報取得＆サーバーから対応する写真受信*/
        calenderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val monthe = month + 1 /*月情報は一か月ずれるから修正*/
            val date = "$year$monthe$dayOfMonth"
            val selectday_1 = date + "_1"
            val selectday_2 = date + "_2"
            val selectday_3 = date + "_3"
            val sendday_1 = CalenderFragment.DayRequest(date = selectday_1)
            val sendday_2 = CalenderFragment.DayRequest(date = selectday_2)
            val sendday_3 = CalenderFragment.DayRequest(date = selectday_3)
            text.setText(selectday_1)
            text_2.setText("2020919_1")
            val httpAsync = (baseUrl)
                .httpPost()
                .header(header).body(requestAdapter.toJson(sendday_1)) /*選択日の朝ご飯の画像を習得*/
                .responseString() { request, response, result ->
                    Log.d("hoge", result.toString())
                    when (result) {
                        is Result.Success -> {
                            val photo = result.get()
                            val photouri:Uri = Uri.parse(photo)
                            Picasso.get()
                                .load(photouri)
                                .into(FirstView)
                            text.setText(photo)
                            Log.d("send", selectday_1)
                        }
                        is Result.Failure -> {
                            Log.d("miss", "miss")
                        }
                    }
                }
                .header(header).body(requestAdapter.toJson(sendday_2)) /*選択日の昼飯の画像を習得*/
                .responseString() { request, response, result ->
                    Log.d("hoge", result.toString())
                    when (result) {
                        is Result.Success -> {
                            val photo = result.get()
                            val photouri:Uri = Uri.parse(photo)
                            Picasso.get()
                                .load(photouri)
                                .into(SecondView)
                            text.setText(photo)
                            Log.d("send", selectday_2)
                        }
                        is Result.Failure -> {
                            Log.d("miss", "miss")
                        }
                    }
                }
                .header(header).body(requestAdapter.toJson(sendday_3)) /*選択日の晩飯の画像を習得*/
                .responseString() { request, response, result ->
                    Log.d("hoge", result.toString())
                    when (result) {
                        is Result.Success -> {
                            val photo = result.get()
                            val photouri:Uri = Uri.parse(photo)
                            Picasso.get()
                                .load(photouri)
                                .into(SecondView)
                            text.setText(photo)
                            Log.d("send", selectday_3)
                        }
                        is Result.Failure -> {
                            Log.d("miss", "miss")
                        }
                    }
                }
            httpAsync.join()
        }
        return View
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalenderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalenderFragment<Any>().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
