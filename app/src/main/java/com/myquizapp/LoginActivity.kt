package com.myquizapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.myquizapp.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private var binding: ActivityLoginBinding? = null
    private lateinit var auth: FirebaseAuth

//    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)



        binding?.tvRegister?.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

        binding?.tvForgotPassword?.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }

        binding?.btnSignIn?.setOnClickListener {
            signUser()
        }

//        binding?.btnSignInWithGoogle?.setOnClickListener { signInWIthGoogle() }


    }

    private fun signUser() {
        val email = binding?.etSinInEmail?.text.toString()
        val password = binding?.etSinInPassword?.text.toString()

        if (validateForm(email,password)){
            showProgressBar()
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        showToast(this, "Login Successful")
                        startActivity(Intent(this,MainActivity::class.java))

                        finish()
                        hideProgressBar()
                    }
                    else{
                        showToast(this, "Can't login currently. Check your email or password and try after sometime")
                        hideProgressBar()
                    }

                }
        }
    }

//    private fun signInWIthGoogle(){
//        val signInIntent = googleSignInClient.signInIntent
//        launcher.launch(signInIntent)
//
//    }
//
//    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
//        if(result.resultCode == Activity.RESULT_OK){
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            handleResults(task)
//        }
//    }
//
//    private fun handleResults(task: Task<GoogleSignInAccount>) {
//        if(task.isSuccessful){
//            val account:GoogleSignInAccount? = task.result
//            if(account!=null){
//                updateUI(account)
//            }
//        }
//        else{
//            showToast(this, "SignIn Failed")
//        }
//    }
//
//    private fun updateUI(account: GoogleSignInAccount) {
//        showProgressBar()
//        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
//        auth.signInWithCredential(credential).addOnCompleteListener{
//            if (it.isSuccessful){
//                showToast(this, "Login Successful")
//                startActivity(Intent(this,MainActivity::class.java))
//
//                finish()
//                hideProgressBar()
//            }
//            else{
//                showToast(this, "Can't login currently. Check your email or password and try after sometime")
//                hideProgressBar()
//            }
//        }
//    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding?.tilEmail?.error = "Enter valid email address"
                false
            }

            TextUtils.isEmpty(password) -> {
                binding?.tilPassword?.error = "Enter password"
                false
            }

            else -> {
                true
            }
        }
    }
}


