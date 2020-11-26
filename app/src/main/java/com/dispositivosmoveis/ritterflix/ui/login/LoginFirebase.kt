package com.dispositivosmoveis.ritterflix.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dispositivosmoveis.ritterflix.R
import com.dispositivosmoveis.ritterflix.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFirebase : AppCompatActivity() {

    private var btn_logar: Button? = null
    private var btn_Cadastro: Button? = null
    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_firebase)

        mAuth = FirebaseAuth.getInstance()
        btn_logar = findViewById<View>(R.id.btn_logar) as Button
        btn_Cadastro = findViewById<View>(R.id.btn_cadastro) as Button
        edtEmail = findViewById<View>(R.id.edtEmail) as EditText
        edtPassword = findViewById<View>(R.id.edtPassword) as EditText
        btn_logar!!.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser(){
        if  (edtEmail!!.text.toString().isEmpty()){
            edtEmail!!.error = "Por favor insira o e-mail"
            edtEmail!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail!!.text.toString()).matches()){
            edtEmail!!.error ?: "Por favor coloque um e-mail válido"
            edtEmail!!.requestFocus()
            return
        }
        if (edtPassword!!.text.toString().isEmpty()){
            edtPassword!!.error = "Por favor insira o password"
            edtPassword!!.requestFocus()
            return
        }
        loginUser(email = edtEmail!!.text.toString(), password = edtPassword!!.text.toString())
    }

    private fun loginUser(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = mAuth!!.currentUser
                    Toast.makeText(applicationContext, "Login OK!", Toast.LENGTH_SHORT).show()
                    openHomeActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun userConnected(): Boolean {
        val currentUser = mAuth!!.currentUser
        return if (currentUser == null) {
            false
        } else {
            true
        }
    }

    override fun onStart() {
        super.onStart()
        if (userConnected()) {
            openHomeActivity()
        }
    }

    private fun openHomeActivity() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }




}