package mi191324.example.myapplication

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val View = inflater.inflate(R.layout.fragment_notification, container, false)
        val recyclerView:RecyclerView = View.findViewById(R.id.recycler_me)

        val http = "https://asia-northeast1-farmily-meal.cloudfunctions.net/message_function"
        val httpurl = "https://asia-northeast1-farmily-meal.cloudfunctions.net/photolist"
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val progressDialog = ProgressDialog(requireContext());
        progressDialog.setTitle("タイトル");
        progressDialog.setMessage("メッセージ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        val httpAsync = http
            .httpGet()
            .responseString(){request, response, result ->
                Log.d("hoge", result.toString())
                when (result) {
                    is Result.Success -> {
                        val data = result.get()
                        Log.d("data", data)
                        val res = moshi.adapter(MessageListResponse::class.java).fromJson(data)
                        recyclerView.adapter = MessageAdapter(res!!.texts)
                    }
                    is Result.Failure -> {
                        val (user, err) = result
                        Log.d("No", "${user}")
                        val myToast: Toast = Toast.makeText(getActivity(), "データの受信に失敗しました", Toast.LENGTH_LONG)
                        myToast.show()
                    }
                }
            }
        httpAsync.join()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        progressDialog.dismiss()
        return View
    }

    data class Message(
        var id: Int,
        var kind: String,
        var text: String
    )

    data class MessageListResponse(
        val texts: List<Message>
    )

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotificationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
