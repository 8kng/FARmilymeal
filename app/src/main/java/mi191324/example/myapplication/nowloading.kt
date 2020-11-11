package mi191324.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class nowloading : Fragment(){
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View =  inflater.inflate(R.layout.nowloading, container, false)
        val progressBar: ProgressBar = View.findViewById(R.id.progress)
        val text : TextView = View.findViewById(R.id.loadtxt)
        progressBar.setVisibility(android.widget.ProgressBar.VISIBLE)
        return View
    }
}