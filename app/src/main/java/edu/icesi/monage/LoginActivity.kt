package edu.icesi.monage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.icesi.monage.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Entra a la pantalla principal
        binding.loginBtn.setOnClickListener {
            if(binding.emailET.text.toString() != "" && binding.passwordET.editText!!.text.toString() != ""){
                Firebase.auth.signInWithEmailAndPassword(
                    binding.emailET.text.toString(),
                    binding.passwordET.editText!!.text.toString()
                ).addOnSuccessListener {
                    startActivity(Intent(this,NavigationActivity::class.java))
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this,it.message, Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Existen campos vacios", Toast.LENGTH_LONG).show()
            }

        }

        //Entra a la pantalla de registro
        binding.singupTxt.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        }
    }
}