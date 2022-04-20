package com.example.firebasewithphone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebasewithphone.databinding.ActivityFireStoreBinding

import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class FireStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFireStoreBinding
    private lateinit var fireStore: FirebaseFirestore
    private var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFireStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val liveData = LiveData()

        loadFCMToken()



        binding.apply {
            liveData.count.observe(this@FireStoreActivity, {
                tvCount.text


            })
            bPlus.setOnClickListener {
                liveData.setCount(count++)
            }

            bMinus.setOnClickListener {
                liveData.setCount(count--)


            }
        }

        fireStore = FirebaseFirestore.getInstance()

        var post = Post("Pdp", "B-12")


        fireStore.collection("Post").get().addOnCompleteListener {
            Log.d("QureySnap", it.result.toString())

            it.result.forEach { document ->

                Log.d("DocumentSnap", document.data.toString())


            }

        }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        binding.bStore.setOnClickListener {
            fireStore.collection("Post")
                .add(post)
                .addOnCompleteListener {
                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }


    }

    private fun loadFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("TAG", "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            // Save it in locally to use later
            val token = task.result
            Log.d("@@@", token.toString())
        })
    }

}
