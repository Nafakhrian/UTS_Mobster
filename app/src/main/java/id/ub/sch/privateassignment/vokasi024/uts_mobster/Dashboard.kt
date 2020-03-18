package id.ub.sch.privateassignment.vokasi024.uts_mobster

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.json.JSONArray
import org.json.JSONObject

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btn_logout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("STATUS", "0")
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btn_add.setOnClickListener {
            startActivity(Intent(this, Berita::class.java))
        }
        getServer()
    }

    @SuppressLint("WrongConstant")
    private fun getServer() {
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val slides = ArrayList<BeritaData>()
        AndroidNetworking.get("http://$ip/UTS_Mobster/berita.php")
            .setPriority(Priority.MEDIUM).build().getAsJSONObject(object :
                JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("kotlinResponse", response.toString())
                    val jsonArray: JSONArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        var isi1 = jsonObject.optString("judul_berita").toString()
                        var isi2 = jsonObject.optString("waktu_berita").toString()
                        var isi3 = jsonObject.optString("penulis_berita").toString()
                        var isi4 = jsonObject.optString("isi_berita").toString()
                        slides.add(BeritaData("$isi1", "$isi2", "$isi3", "$isi4"))
                    }
                    val adapter = BeritaAdapter(slides)
                    recyclerView.adapter = adapter
                }
                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}
