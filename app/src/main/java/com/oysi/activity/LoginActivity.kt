package com.oysi.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.oysi.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var callbackManager = CallbackManager.Factory.create()
    private lateinit var mAuth: FirebaseAuth
    override fun onStart() {
        super.onStart()
        moveNextPage()
    }
    override fun onResume() {
        super.onResume()
        moveNextPage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        handleFacebookAccessToken(result)

                        val sharedPreferences = getSharedPreferences("facebook", Context.MODE_PRIVATE)
                        val edit = sharedPreferences.edit()
                        edit.putString("userid", result!!.accessToken.userId)
                        edit.apply()

                    }

                    override fun onCancel() {
                    }

                    override fun onError(error: FacebookException?) {
                    }
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: LoginResult?) {
        val credential = FacebookAuthProvider.getCredential(token!!.accessToken.token)
        mAuth.signInWithCredential(credential)
            .addOnFailureListener {
                    e-> Log.d("ErrorLogin",e.message)
            }
            .addOnCompleteListener {
                result ->
                if (result.isSuccessful){
                    moveNextPage()
                    Toast.makeText(this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show()

                }
            }

    }

    fun moveNextPage(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null){
            val intent =Intent(this,MainActivity::class.java)
            val sharedPreferences = getSharedPreferences("facebook", Context.MODE_PRIVATE)
            val edit = sharedPreferences.edit()
            edit.putString("email",currentUser.email)
            edit.putString("displayName",currentUser.displayName)
            edit.apply()
            startActivity(intent)
        }
    }


}
