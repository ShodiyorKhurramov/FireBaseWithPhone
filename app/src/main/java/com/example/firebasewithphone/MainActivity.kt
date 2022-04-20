package com.example.firebasewithphone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebasewithphone.databinding.ActivityMainBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    private var verificationId =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
   firebaseAuth = FirebaseAuth.getInstance()
        val callback = object  : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.d("Main","veCompleted")
              signInWithCredential(p0)

            }

            override fun onVerificationFailed(p0: FirebaseException) {

                Log.d("Main","veFaild")

            }


            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                verificationId =p0

                Log.d("Main","veCodeSent")
            }
        }

        binding.enterBtn.setOnClickListener{
            sendVerificationCode(binding.phoneNumber.text.toString().trim(),callback)
        }

        binding.loginBtn.setOnClickListener{
            verifyCode(binding.smsCode.text.toString())
        }
    }

    private fun sendVerificationCode(
        number: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ){
        val option = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callback)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(option)


    }
    private fun verifyCode(code : String){
        val credetial =  PhoneAuthProvider.getCredential(verificationId,code)
        signInWithCredential(credetial)
    }

    private fun signInWithCredential(credential : PhoneAuthCredential){

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Susses" ,Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this,"Susses" ,Toast.LENGTH_SHORT).show()

                }
            }
     }
}