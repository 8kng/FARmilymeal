package mi191324.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.httpUpload
import com.github.kittinunf.result.Result
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.fragment_messagecreate.*
import java.io.File
import java.io.IOException


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
    private var Boolen  = false
    lateinit var mp: MediaPlayer
    private val recordPermission = Manifest.permission.RECORD_AUDIO
    private val PERMISSION_CODE = 21
    private var mediaRecorder: MediaRecorder? = null
    private var recordFile: String? = null
    var storage = FirebaseStorage.getInstance()
    private var task: UploadTask? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private fun startPlay(){
        messagecreateView.setText("再生します")
        recordFile = "filename.ogg"
        val recordPath = requireActivity().getExternalFilesDir("/")!!.absolutePath
        if (playerBtn.isChecked == true) {
            try {
                mp = MediaPlayer()
                mp.setDataSource(recordPath + "/" + recordFile)
                mp.prepare()
                mp.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }else {
            try {
                mp.stop()
                mp.prepare()
                mp.release()
            }catch (e: IllegalStateException){
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun choose(){
        if(recordingBtn.isChecked == true){  /*マイク使用許可とります*/
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
            } else {
                Timer.setBase(SystemClock.elapsedRealtime())
                Timer.start()
                messagecreateView.setText("メッセージを録音しています")
                recordFile = "filename.ogg"
                val recordPath = requireActivity().getExternalFilesDir("/")!!.absolutePath
                mediaRecorder = MediaRecorder()
                mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder!!.setOutputFile(recordPath + "/" + recordFile);
                mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    mediaRecorder!!.prepare()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                mediaRecorder!!.start()
            }
        } else{
            messagecreateView.setText("録音することができます")
            Timer.stop()
            try {
                mediaRecorder!!.stop()
                mediaRecorder!!.release()
                mediaRecorder = null
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendvoice() {
        val recordFile = "filename.ogg"
        val recordPath = requireActivity().getExternalFilesDir("/")!!.absolutePath
        val storageRef = recordPath + "/" + recordFile

        val baseUrl = "https://asia-northeast1-farmily-meal.cloudfunctions.net/familyvoice"
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val httpAsync = (baseUrl)
            .httpUpload()
            .add(
                    FileDataPart(
                        File(recordPath + "/" + recordFile),
                        contentType = "audio/3gpp2",
                        name = "file"
                    )
            )
            .responseString { request, response, result ->
                Log.d("hoge", result.toString())
                when (result) {
                    is Result.Success -> {
                        val data = result.get()
                        println(data)
                    }
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                    }
                }
            }
        httpAsync.join()
        openDialog()
    }

    public fun openDialog(){
        val myToast: Toast = Toast.makeText(getActivity(), "送信しました", Toast.LENGTH_LONG)
        myToast.show()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View =  inflater.inflate(R.layout.fragment_messagecreate, container, false)
        // Inflate the layout for this fragment
        val recordingBtn : ToggleButton = View.findViewById(R.id.recordingBtn)
        val messagecreateView : View = View.findViewById(R.id.messagecreateView)
        val playerBtn : Button = View.findViewById(R.id.playerBtn)
        val VoicesendBtn : Button = View.findViewById(R.id.VoisesendBtn)
        val Timer : Chronometer = View.findViewById(R.id.Timer)

        recordingBtn.setOnClickListener(){
            choose()
        }
        playerBtn.setOnClickListener(){
            startPlay()
        }
        VoicesendBtn.setOnClickListener(){
            sendvoice()
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
