package com.oysi.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.oysi.R
import com.oysi.activity.MainActivity
import com.oysi.base.BaseFragment
import com.oysi.model.Poll.CityPoll
import kotlinx.android.synthetic.main.fragment_add_city.*

class FragmentAddCity : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_city, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        eventOnclick()
    }
    /*-------------- Event Onclick-----------*/

    private fun eventOnclick() {
        btnAdd.setOnClickListener {
            checkValidate()
        }
    }

    private fun checkValidate() {
        val state = edtState.text.toString()
        val city = edtCity.text.toString()
        val country = edtCountry.text.toString()
        when {
            state == "" -> {
                Toast.makeText(
                    activity!!.applicationContext,
                    "Không được để trống Thành phố",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            city == "" -> {
                Toast.makeText(
                    activity!!.applicationContext,
                    "Không được để trống Quận huyện",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            country == "" -> {
                Toast.makeText(
                    activity!!.applicationContext,
                    "Không được để trống Quốc gia",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            else -> {
                addCityPoll(city, state, country)
            }
        }


    }
    /*-------------- Event Lisnter-----------*/

    private fun addCityPoll(city: String, state: String, country: String) {
        val ref = FirebaseDatabase.getInstance().getReference("poll")

        val cityPoll = CityPoll(city, state, country, 0)
        ref.child(cityPoll.city.toString()).setValue(cityPoll).addOnCompleteListener {
            Toast.makeText(activity!!.applicationContext, "Thêm thành công", Toast.LENGTH_SHORT)
                .show()
            edtState.setText("")
            edtCity.setText("")
            edtCountry.setText("")
            dismiss()
        }
    }
}



