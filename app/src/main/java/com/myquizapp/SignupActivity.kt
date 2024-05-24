package com.myquizapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.myquizapp.databinding.ActivitySignupBinding

class SignupActivity : BaseActivity() {

    private var binding: ActivitySignupBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.tvLoginPage?.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        binding?.btnSignUp?.setOnClickListener { registerUser() }

    }
    private fun registerUser(){
        val name = binding?.etSinUpName?.text.toString()
        val email = binding?.etSinUpEmail?.text.toString()
        val password = binding?.etSinUpPassword?.text.toString()

        if(validateForm(name,email,password)){
            showProgressBar()
            auth.createUserWithEmailAndPassword(email,password)

                .addOnCompleteListener{task->
                if (task.isSuccessful){
                    showToast(this, "User Id created successfully")
                    hideProgressBar()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }

                else{
                showToast(this, "User Id not created. Try again later")
                hideProgressBar()
            }

                }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name)->{
                binding?.tilName?.error = "Enter name"
                false
            }

            TextUtils.isEmpty(email)&&!Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmail?.error = "Enter valid email address"
                false
            }
            TextUtils.isEmpty(password)->{
                binding?.tilPassword?.error = "Enter password"
                false
            }
            else-> {true}
        }
    }

}