package com.oysi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.oysi.R
import com.oysi.model.Poll.CityPoll
import kotlinx.android.synthetic.main.activity_add_city_poll.*


class AddCityPollActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city_poll)

        btnAdd.setOnClickListener {
            val city = edtThanhPho.text.toString()
            val country = edtQuocGia.text.toString()
            Log.d("add",city + " " + country)

            addCityPoll(city,country)
        }
    }


    private fun addCityPoll(city : String,country:String) {
        val ref = FirebaseDatabase.getInstance().getReference("poll")
        val cityId =ref.push().key
        val cityPoll = CityPoll(city,country,0)
        ref.child(cityId.toString()).setValue(cityPoll).addOnCompleteListener {
            Toast.makeText(this,"add success", Toast.LENGTH_SHORT).show()
        }
        startActivity(Intent(this,MainActivity::class.java))
    }
}
