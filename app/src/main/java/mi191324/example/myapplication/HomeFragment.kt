package mi191324.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var photolist = arrayOfNulls<Uri>(20)
    var datelist = arrayOfNulls<DateTimeException>(20)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val View =  inflater.inflate(R.layout.fragment_home, container, false)
        val recycler_view : RecyclerView = View.findViewById(R.id.recycler_view)

        val httpurl = "https://asia-northeast1-farmily-meal.cloudfunctions.net/photolist"
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
        val day = format.format(Date())
        val calendar1 = Calendar.getInstance()

        val httpAsync = httpurl
            .httpGet()
            .responseString() { request, response, result ->
                Log.d("hoge", result.toString())
                when (result) {
                    is Result.Success -> {
                        val data = result.get()
                        Log.d("response", data)
                        val res = moshi.adapter(PhotoListResponse::class.java).fromJson(data)
                        recycler_view.adapter = HomeAdpter(res!!.photos)
                        val time1_be:Date = format.parse(res.photos[0].datetime)
                        val time2:Date = format.parse(day)
                        calendar1.setTime(time1_be)
                        calendar1.add(Calendar.MINUTE, 20)
                        calendar1.add(Calendar.DATE, 1)
                        val time1_af = calendar1.getTime()
                        Log.d("始まり", time1_be.toString())
                        Log.d("現在", time2.toString())
                        Log.d("終わり", time1_af.toString())
                        if ((time1_be <= time2) && (time1_af >= time2)){
                            popupWindow()
                        }
                    }
                    is Result.Failure -> {
                        val (user, err) = result
                        Log.d("No", "${user}")
                    }
                }
            }
        httpAsync.join()

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)
        return View
    }

    data class Photo(
        var id: Int,
        var url: String,
        var datetime: String
    )

    data class PhotoListResponse(
        val photos: List<Photo>
    )

    private fun popupWindow(){  /*電話をかけるかのポップアップウィンドウ表示*/
        AlertDialog.Builder(requireContext())
            .setTitle("ただいまお食事をしているようです!")
            .setMessage("電話を掛けますか?")
            .setPositiveButton("Yes", { dialog, which ->  /*電話をすると選択時*/
                val intent = Intent(Intent.ACTION_MAIN)
                intent.setAction("android.intent.category.LAUNCHER")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setClassName("com.google.android.apps.tachyon", "com.google.android.apps.tachyon.MainActivity");
                try{
                    startActivity(intent)
                } catch (e: Exception){
                    val Toast: Toast = Toast.makeText(getActivity(), "アプリが見つかりません", Toast.LENGTH_LONG)
                    Toast.show()
                }
            })
            .setNegativeButton("No", { dialog, which ->  /*電話しないと選択時*/
            })
            .show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}