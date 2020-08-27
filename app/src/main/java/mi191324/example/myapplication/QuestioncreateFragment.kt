package mi191324.example.myapplication

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
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

    private fun saveDate(){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("Question1", Question1editor.text.toString())
            .putString("Question2", Question2editor.text.toString())
            .putString("Question3", Question3editor.text.toString())
            .putString("Question4", Question4editor.text.toString())
            .putString("Question5", Question5editor.text.toString())
            .apply()
    }

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
        val SendQuestionsBtn: Button = View.findViewById(R.id.SendQuestionsBtn)

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

        SendQuestionsBtn.setOnClickListener(){
            saveDate()
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
