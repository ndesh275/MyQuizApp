package com.myquizapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import com.myquizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var quizModelList : MutableList<QuizModel>
    private lateinit var adapter: QuizListAdapter
    private lateinit var auth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setTheme(R.style.Base_Theme_MyQuizApp)
        setContentView(binding?.root)
        auth = Firebase.auth


        binding?.btnSignOut?.setOnClickListener{
            if(auth.currentUser!=null){
                auth.signOut()
                startActivity(Intent(this, SplashscreenActivity::class.java))
                finish()
            }
        }

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }

    private fun setupRecyclerView(){
        binding?.progressBar?.visibility = View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
        binding?.recyclerView?.adapter = adapter

    }

    private fun getDataFromFirebase(){
        binding?.progressBar?.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference


            .get()
            .addOnSuccessListener{ dataSnapshot->

                if(dataSnapshot.exists()){
                    for(snapshot in dataSnapshot.children){
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)

                        }

                    }
                }
                setupRecyclerView()


            }

    }


}