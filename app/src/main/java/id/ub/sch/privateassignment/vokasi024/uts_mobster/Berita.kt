package id.ub.sch.privateassignment.vokasi024.uts_mobster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_berita.*
import org.json.JSONArray

class Berita : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita)

        btn_save.setOnClickListener{
            val judul = editTextJudul.text.toString()
            val waktu = editTextWaktu.text.toString()
            val penulis = editTextPenulis.text.toString()
            val isi = editTextIsi.text.toString()
            postServer(judul, waktu, penulis, isi)
            Log.i("astaghfirullah",judul+waktu+penulis+isi)
            startActivity(Intent(this, Dashboard::class.java))
        }
    }

    private fun postServer(data1: String, data2: String, data3: String, data4: String) {
        Log.i("astaghfirullah",data1+data2+data3)
        AndroidNetworking.post("http://$ip/UTS_Mobster/insert.php")
            .addBodyParameter("judul_berita", data1)
            .addBodyParameter("waktu_berita", data2)
            .addBodyParameter("penulis_berita", data3)
            .addBodyParameter("isi_berita", data4)
            .setPriority(Priority.MEDIUM).build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                }
                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}
