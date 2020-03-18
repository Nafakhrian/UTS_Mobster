package id.ub.sch.privateassignment.vokasi024.uts_mobster

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

const val ip = "192.168.1.10"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val stat = sharedPreferences.getString("STATUS", "")
        if (stat == "1") {
            startActivity(Intent(this@MainActivity, Dashboard::class.java))
            finish()
        } else {
            btnLogin.setOnClickListener {
                var username = editTextUsername.text.toString()
                var password = editTextPassword.text.toString()
                postkerserver(username, password)
            }
        }
    }

    private fun postkerserver(data1: String, data2: String) {
        AndroidNetworking.post("http://$ip/UTS_Mobster/cekLogin.php")
            .addBodyParameter("username", data1)
            .addBodyParameter("password", data2)
            .setPriority(Priority.MEDIUM).build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("status"))
                        var statuslogin = jsonObject.optString("status")
                        txt1.setText(statuslogin)
                        if (statuslogin == "1") {
                            val sharedPreferences =
                                getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("STATUS", statuslogin)
                            editor.apply()
                            startActivity(Intent(this@MainActivity, Dashboard::class.java))
                            finish()
                        }
                    }
                }

                override fun onError(error: ANError) { // handle error
                }
            })

    }
}
