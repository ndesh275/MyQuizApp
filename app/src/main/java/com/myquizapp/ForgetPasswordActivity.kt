package com.myquizapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.myquizapp.databinding.ActivityForgetPasswordBinding
import com.myquizapp.databinding.ActivityLoginBinding

class ForgetPasswordActivity : BaseActivity() {
    private var binding:ActivityForgetPasswordBinding? = null
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.btnForgotPasswordSubmit?.setOnClickListener { resetPassword() }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validateForm(email: String): Boolean {
        return when{
            TextUtils.isEmpty(email)&&!Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmailForgetPassword?.error = "Enter valid email address"
                false
            }
            else ->true
        }

    }

    private fun resetPassword(){
        val email = binding?.etForgotPasswordEmail?.text.toString()

        if (validateForm(email)){
            showProgressBar()
            auth.sendPasswordResetEmail(email).addOnCompleteListener{ task->
                if(task.isSuccessful){
                    hideProgressBar()
                    binding?.tilEmailForgetPassword?.visibility = View.GONE
                    binding?.btnForgotPasswordSubmit?.visibility = View.VISIBLE
                    binding?.btnForgotPasswordSubmit?.visibility = View.GONE
                }
                else{
                    hideProgressBar()
                    showToast(this, "Cannot reset your password. Enter a valid email address and try after sometime")
                }
            }
        }
    }

    override fun onBackPressed(){
        super.onBackPressed()
    }
}