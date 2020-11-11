package mi191324.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    var words: String = "09097865827"

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
        val recycler_view : RecyclerView = View.findViewById(R.id.recycler_me)
        val FAB : FloatingActionButton = View.findViewById(R.id.FAB)

        val httpurl = "https://asia-northeast1-farmily-meal.cloudfunctions.net/familyphoto_n"
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
        val fomat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val day = format.format(Date())
        val calendar1 = Calendar.getInstance()
        val calender2 = Calendar.getInstance()
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val number = pref.getString("number", "")

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

                        val date: Date = format.parse(day)
                        calender2.setTime(format.parse(res.photos[0].DateTime))
                        calender2.add(Calendar.HOUR, 13)
                        calender2.add(Calendar.MINUTE, -19)
                        val time2: Date = calender2.getTime()
                        calendar1.setTime(date)
                        calendar1.add(Calendar.HOUR, 12)
                        val time1_be = calendar1.getTime()
                        calendar1.add(Calendar.MINUTE, -20)
                        val time1_af = calendar1.getTime()
                        Log.d("始まり", time1_be.toString())
                        Log.d("現在", time2.toString())
                        Log.d("終わり", time1_af.toString())
                        if ((time1_be >= time2) && (time1_af <= time2)) {
                            popupWindow(number)
                        }
                    }
                    is Result.Failure -> {
                        val (user, err) = result
                        Log.d("No", "${user}")
                        val myToast: Toast = Toast.makeText(
                            getActivity(),
                            "データの受信に失敗しました",
                            Toast.LENGTH_LONG
                        )
                        myToast.show()
                    }
                }
            }
        httpAsync.join()

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)

        FAB.setOnClickListener{ view ->
            phonenumber(number)
        }

        return View
    }

    data class Photo(
        var DateTime: String,
        var ID: Int,
        var url: String
    )

    data class PhotoListResponse(
        val photos: List<Photo>
    )

    private fun phonenumber(number: String?){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        val myedit = EditText(requireContext())
        var word: String = ""
        myedit.maxLines = 1
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("電話番号入力画面")
        dialog.setMessage("GoogleHomeの電話番号を設定します")
        dialog.setView(myedit)
        myedit.setText(number)
        dialog.setPositiveButton("これでOK", DialogInterface.OnClickListener { dialog, which ->
            editor.putString("number", myedit.text.toString())
                .commit()
            val imm = getActivity()?.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            imm.hideSoftInputFromWindow(myedit.getWindowToken(), 0)
            dialog.dismiss()
        })
        dialog.show()
    }

    private fun popupWindow(phone: String?){/*電話をかけるかのポップアップウィンドウ表示*/
        val number = "tel" + phone
        AlertDialog.Builder(requireContext())
            .setTitle("ただいまお食事をしているようです!")
            .setMessage("Duoアプリに移動して電話を掛けますか?")
            .setPositiveButton("Yes", { dialog, which ->  /*電話をすると選択時*/
                val intent = Intent()
                intent.setAction("com.google.android.apps.tachyon.action.CALL")
                intent.setClassName(
                    "com.google.android.apps.tachyon",
                    "com.google.android.apps.tachyon.ExternalCallActivity"
                ); /*Duoアプリに移動*/
                intent.setData(Uri.parse(number))
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    val Toast: Toast = Toast.makeText(
                        getActivity(),
                        "Duoアプリが見つかりません",
                        Toast.LENGTH_LONG
                    ) /*アプリが見つからなかった場合*/
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