package edu.icesi.monage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.icesi.monage.databinding.ActivityRegistrationBinding
import edu.icesi.monage.model.User

class RegistrationActivity : AppCompatActivity() {

    private val binding:ActivityRegistrationBinding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Crea cuenta y va a la pantalla de registro
        binding.createBtn.setOnClickListener {
            val email = binding.correoET.text.toString()
            val pass = binding.passwordET.text.toString()
            Log.e("<<<<<", binding.correoET.text.toString())
            if (binding.nameET.text.toString() != "" && binding.correoET.text.toString() != "" && binding.passwordET.text.toString() != ""){
                Firebase.auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
                    Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_LONG).show()
                    registerUserData()
                }.addOnFailureListener {
                    Toast.makeText(this, "Algo fallo: ${it.message}", Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_LONG).show()
            }
        }

        //Entra a la pantalla de login
        binding.singinTxt.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    private fun registerUserData() {
        val uid = Firebase.auth.currentUser?.uid
        Log.e(
            "<--<<<","${uid}"
        )
        uid?.let {
            val user = User(
                it,
                binding.nameET.text.toString(),
                binding.correoET.text.toString()
            )
            Firebase.firestore.collection("users").document(it).set(user).addOnSuccessListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}