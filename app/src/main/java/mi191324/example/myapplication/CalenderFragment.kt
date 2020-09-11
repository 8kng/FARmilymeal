package mi191324.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_calender.*

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

    fun CalenderSelect(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.calendarView ->{
                FirstView.setImageResource(R.drawable.ic_today_black_24dp)
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View = inflater.inflate(
            R.layout.fragment_calender,
            container,
            false
        )
        val calenderView: CalendarView =  View.findViewById(R.id.calendarView)
        val FirstView: ImageView = View.findViewById(R.id.FirstView)
        val SecondView: ImageView = View.findViewById(R.id.SecondView)
        val ThirdView: ImageView = View.findViewById(R.id.ThirdView)
        val text: TextView = View.findViewById(R.id.FirstText)

        calenderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$year/$month/$dayOfMonth"
            text.setText(date)
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
