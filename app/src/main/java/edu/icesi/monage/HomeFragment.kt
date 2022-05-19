package edu.icesi.monage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.icesi.monage.databinding.FragmentHomeBinding
import edu.icesi.monage.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.progressFood.setProgress(calculateProgress(true,false,false),true)
        binding.foodTxt.text = "${binding.progressFood.progress.toString()}${"%"}"
        binding.progressHygiene.setProgress(calculateProgress(false,true,false),true)
        binding.hygieneTxt.text = "${binding.progressHygiene.progress.toString()}${"%"}"
        binding.progressFun.setProgress(calculateProgress(false,false,true),true)
        binding.funTxt.text = "${binding.progressFun.progress.toString()}${"%"}"

        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!


            withContext(Dispatchers.Main) {
                binding.username.text = user.username
                binding.energyTx.text = "${user.energy}/10"
                binding.moneyTx.text = "$${user.money}"
                when(user.state){
                    "FANTASTIC"->{
                        binding.imageState.setImageResource(R.drawable.fantastico)
                    }
                    "HAPPY"->{
                        binding.imageState.setImageResource(R.drawable.felicidad)
                    }
                    "NEUTRAL"->{
                        binding.imageState.setImageResource(R.drawable.neutral)
                    }
                    "SAD"->{
                        binding.imageState.setImageResource(R.drawable.triste)
                    }
                    "DYING"->{
                        binding.imageState.setImageResource(R.drawable.dead)

                    }
                }
            }
        }


            return binding.root
    }

    private fun calculateProgress(food:Boolean,hygiene: Boolean, funn: Boolean): Int{
        var progress = 0
        if (food){
           //metodo del usuario para calcular
            progress = 60
        }else if (hygiene){
            //metodo del usuario para calcular
            progress = 100
        }else{
            //metodo del usuario para calcular
            progress = 10
        }
        return progress
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}