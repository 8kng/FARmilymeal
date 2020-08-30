package mi191324.example.myapplication

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_messagecreate.*
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessagecreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessagecreateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mp: MediaPlayer
    lateinit var rec: MediaRecorder
    lateinit var fl: File
    val filePath: String = android.os.Environment.getExternalStorageDirectory().toString() + "/sample.wav"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun startRecord() {
        var wavFile: File = File(filePath)
        if (wavFile.exists()){
            wavFile.delete()
        }
        try {
            rec = MediaRecorder()
            rec.setAudioSource(MediaRecorder.AudioSource.MIC)
            rec.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
            rec.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            rec.setOutputFile(filePath)
            rec.prepare()
            rec.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRecord() {
        try {
            rec.stop()
            rec.reset()
            rec.release()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun choose(){
        if(recordingBtn.isChecked == true){
            startRecord()
        } else{
            stopRecord()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View =  inflater.inflate(R.layout.fragment_messagecreate, container, false)
        // Inflate the layout for this fragment
        val recordingBtn : ToggleButton = View.findViewById(R.id.recordingBtn)
        val messagecreateView : View = View.findViewById(R.id.messagecreateView)

        recordingBtn.setOnClickListener(){
            choose()
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
         * @return A new instance of fragment MessagecreateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessagecreateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
