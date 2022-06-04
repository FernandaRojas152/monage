package edu.icesi.monage

import android.graphics.Color.blue
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
import edu.icesi.monage.model.State
import edu.icesi.monage.model.User
import edu.icesi.monage.model.UserGameState
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
        startGame()
        return binding.root
    }

    private fun calculateProgress(food:Boolean,hygiene: Boolean, funn: Boolean){
        var progress = 0
        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            if (food) {
                //metodo del usuario para calcular
                progress = user.food
                withContext(Dispatchers.Main) {
                    binding.progressFood.setProgress(progress)
                    binding.foodTxt.text = "${binding.progressFood.progress.toString()}${"%"}"
                }
            } else if (hygiene) {
                //metodo del usuario para calcular
                progress = user.hygiene
                withContext(Dispatchers.Main) {
                    binding.progressHygiene.setProgress(progress)
                    binding.hygieneTxt.text = "${binding.progressHygiene.progress.toString()}${"%"}"
                }
            } else {
                //metodo del usuario para calcular
                progress = user.funny
                withContext(Dispatchers.Main) {
                    binding.progressFun.setProgress(progress)
                    binding.funTxt.text = "${binding.progressFun.progress.toString()}${"%"}"
                }
            }
        }
    }

    private fun startGame(){
        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            val userState = UserGameState(user)
            val average = userState.totalCalculation()
            val state = State.calculateState(average)
            user.state = state
            withContext(Dispatchers.Main) {
                binding.username.text = user.username
                binding.energyTx.text = "${user.energy}/10"
                binding.moneyTx.text = "$${user.money}"
                binding.score.text = user.score.toString()
                when(user.state){
                    "FANTASTIC"->{
                        binding.imageState.setImageResource(R.drawable.fantastico)
                        //binding.imagebackground.setImageResource(R.drawable.backstatefantastic)
                    }
                    "HAPPY"->{
                        binding.imageState.setImageResource(R.drawable.felicidad)
                        //binding.imagebackground.setImageResource(R.drawable.backstatefeliz)
                    }
                    "NEUTRAL"->{
                        binding.imageState.setImageResource(R.drawable.neutral)
                        //binding.imagebackground.setImageResource(R.drawable.backstateneutro)

                    }
                    "SAD"->{
                        binding.imageState.setImageResource(R.drawable.triste)
                        //binding.imagebackground.setImageResource(R.drawable.backstatetriste)

                    }
                    "DYING"->{
                        binding.imageState.setImageResource(R.drawable.dead)
                        //binding.imagebackground.setImageResource(R.drawable.backstatemoribundo)


                    }
                }
            }
            Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).set(user)
            calculateProgress(true,false,false)
            calculateProgress(false,true,false)
            calculateProgress(false,false,true)
        }
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