package mi191324.example.myapplication

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

class voiceDialog : AppCompatDialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("")
        .setMessage("メッセージを送信しました")
            .setPositiveButton("Ok") { dialogInterface, i -> }
        return builder.create();
    }
}
