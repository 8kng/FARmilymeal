package mi191324.example.myapplication

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.Fuel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.fragment_questioncreate.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestioncreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestioncreateFragment : Fragment(){
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

    data class QuestionRequest (
        val context: String
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val View = inflater.inflate(R.layout.fragment_questioncreate, container, false)

        val Question1editor: EditText = View.findViewById(R.id.Question1editor)
        val Question2editor: EditText = View.findViewById(R.id.Question2editor)
        val Question3editor: EditText = View.findViewById(R.id.Question3editor)
        val Question4editor: EditText = View.findViewById(R.id.Question4editor)
        val Question5editor: EditText = View.findViewById(R.id.Question5editor)
        val allow1: ToggleButton = View.findViewById(R.id.textallowBtn1)
        val allow2: ToggleButton = View.findViewById(R.id.textallowBtn2)
        val allow3: ToggleButton = View.findViewById(R.id.textallowBtn3)
        val allow4: ToggleButton = View.findViewById(R.id.textallowBtn4)
        val allow5: ToggleButton = View.findViewById(R.id.textallowBtn5)
        val send1: Button = View.findViewById(R.id.messagesendBtn1)
        val send2: Button = View.findViewById(R.id.messagesendBtn2)
        val send3: Button = View.findViewById(R.id.messagesendBtn3)
        val send4: Button = View.findViewById(R.id.messagesendBtn4)
        val send5: Button = View.findViewById(R.id.messagesendBtn5)
        val messagesendBtn1: Button = View.findViewById(R.id.messagesendBtn1)
        val messagesendBtn2: Button = View.findViewById(R.id.messagesendBtn2)
        val messagesendBtn3: Button = View.findViewById(R.id.messagesendBtn3)
        val messagesendBtn4: Button = View.findViewById(R.id.messagesendBtn4)
        val messagesendBtn5: Button = View.findViewById(R.id.messagesendBtn5)

        if (allow1.isChecked == false){
            Question1editor.setEnabled(false)
        }
        if (allow2.isChecked == false){
            Question2editor.setEnabled(false)
        }
        if (allow3.isChecked == false){
            Question3editor.setEnabled(false)
        }
        if (allow4.isChecked == false){
            Question4editor.setEnabled(false)
        }
        if (allow5.isChecked == false){
            Question5editor.setEnabled(false)
        }
        allow1.setOnClickListener(){
            allow1method()
        }
        allow2.setOnClickListener(){
            allow2method()
        }
        allow3.setOnClickListener(){
            allow3method()
        }
        allow4.setOnClickListener(){
            allow4method()
        }
        allow5.setOnClickListener(){
            allow5method()
        }
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val Question1 = pref.getString("Question1", "")
        val Question2 = pref.getString("Question2", "")
        val Question3 = pref.getString("Question3", "")
        val Question4 = pref.getString("Question4", "")
        val Question5 = pref.getString("Question5", "")
        Question1editor.setText(Question1)
        Question2editor.setText(Question2)
        Question3editor.setText(Question3)
        Question4editor.setText(Question4)
        Question5editor.setText(Question5)

        messagesendBtn1.setOnClickListener(){
            saveDate1()
        }
        messagesendBtn2.setOnClickListener(){
            saveDate2()
        }
        messagesendBtn3.setOnClickListener(){
            saveDate3()
        }
        messagesendBtn4.setOnClickListener(){
            saveDate4()
        }
        messagesendBtn5.setOnClickListener(){
            saveDate5()
        }
        return View
    }
    private fun saveDate1(){
        /*サーバーにテキストを送信する*/
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val requestAdapter = moshi.adapter(QuestionRequest::class.java)
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
        val Question1 = QuestionRequest(context = Question1editor.getText().toString())
        val (_, _, result) = Fuel.post("https://asia-northeast1-farmily-meal.cloudfunctions.net/familyquestion").header(header).body(requestAdapter.toJson(Question1)).responseString()
        val (date, _) = result
        /*送信完了したことを伝える*/
        val myToast: Toast = Toast.makeText(getActivity(), "質問を送信しました", Toast.LENGTH_LONG)
        myToast.show()
        /*入力したものの保存*/
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("Question1", Question1editor.text.toString())
            .apply()
    }

    private fun saveDate2(){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("Question2", Question2editor.text.toString())
            .apply()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val requestAdapter = moshi.adapter(QuestionRequest::class.java)
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/Json")
        val Question2 = QuestionRequest(context = Question2editor.getText().toString())
        val (_, _, result) = Fuel.post("https://asia-northeast1-farmily-meal.cloudfunctions.net/familyquestion2").header(header).body(requestAdapter.toJson(Question2)).responseString()
        val (date, _) = result
        /*送信完了したことを伝える*/
        val myToast: Toast = Toast.makeText(getActivity(), "質問を送信しました", Toast.LENGTH_LONG)
        myToast.show()
    }
    private fun saveDate3(){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("Question3", Question3editor.text.toString())
            .apply()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val requestAdapter = moshi.adapter(QuestionRequest::class.java)
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/Json")
        val Question3 = QuestionRequest(context = Question3editor.getText().toString())
        val (_, _, result) = Fuel.post("https://asia-northeast1-farmily-meal.cloudfunctions.net/familyquestion3").header(header).body(requestAdapter.toJson(Question3)).responseString()
        val (date, _) = result
        /*送信完了したことを伝える*/
        val myToast: Toast = Toast.makeText(getActivity(), "質問を送信しました", Toast.LENGTH_LONG)
        myToast.show()
    }
    private fun saveDate4(){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("Question4", Question1editor.text.toString())
            .apply()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val requestAdapter = moshi.adapter(QuestionRequest::class.java)
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/Json")
        val Question4 = QuestionRequest(context = Question4editor.getText().toString())
        val (_, _, result) = Fuel.post("https://asia-northeast1-farmily-meal.cloudfunctions.net/familyquestion4").header(header).body(requestAdapter.toJson(Question4)).responseString()
        val (date, _) = result
        /*送信完了したことを伝える*/
        val myToast: Toast = Toast.makeText(getActivity(), "質問を送信しました", Toast.LENGTH_LONG)
        myToast.show()
    }
    private fun saveDate5(){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("Question5", Question1editor.text.toString())
            .apply()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val requestAdapter = moshi.adapter(QuestionRequest::class.java)
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/Json")
        val Question5 = QuestionRequest(context = Question5editor.getText().toString())
        val (_, _, result) = Fuel.post("https://asia-northeast1-farmily-meal.cloudfunctions.net/familyquestion5").header(header).body(requestAdapter.toJson(Question5)).responseString()
        val (date, _) = result
        /*送信完了したことを伝える*/
        val myToast: Toast = Toast.makeText(getActivity(), "質問を送信しました", Toast.LENGTH_LONG)
        myToast.show()
    }
    private fun allow1method(){
        if (textallowBtn1.isChecked == false){
            Question1editor.setEnabled(false)
        } else{
            Question1editor.setEnabled(true)
        }
    }
    private fun allow2method(){
        if (textallowBtn2.isChecked == false){
            Question2editor.setEnabled(false)
        } else{
            Question2editor.setEnabled(true)
        }
    }
    private fun allow3method(){
        if (textallowBtn3.isChecked == false){
            Question3editor.setEnabled(false)
        } else{
            Question3editor.setEnabled(true)
        }
    }
    private fun allow4method(){
        if (textallowBtn4.isChecked == false){
            Question4editor.setEnabled(false)
        } else{
            Question4editor.setEnabled(true)
        }
    }
    private fun allow5method(){
        if (textallowBtn5.isChecked == false){
            Question5editor.setEnabled(false)
        } else{
            Question5editor.setEnabled(true)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestioncreateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestioncreateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
