package edu.icesi.monage

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.icesi.monage.databinding.FragmentHomeBinding
import edu.icesi.monage.databinding.FragmentPaymentsBinding
import edu.icesi.monage.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class PaymentsFragment : Fragment() {
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentsBinding.inflate(inflater,container,false)
        binding.market.setOnClickListener {
            showDialogMarket()
        }
        binding.job.setOnClickListener {
            showDialogJob()
        }
        binding.spa.setOnClickListener {
            showDialogSpa()
        }
        binding.park.setOnClickListener {
            showDialogPark()
        }

        return binding.root
    }

    fun showDialogMarket(){
        //comprar frutas, comprar verduras, comprar carne
        val options= arrayOf("Comprar frutas", "Comprar verduras", "Comprar carne")
        val selectedList = ArrayList<Int>()
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Supermercado")
        dialogBuilder.setMessage("Selecciona que actividad deseas realizar en el supermercado: ")
        dialogBuilder.setMultiChoiceItems(options, null){
            dialog, which, isChecked ->
            if(isChecked){
                selectedList.add(which)
            }else if(selectedList.contains(which)){
                selectedList.remove(Integer.valueOf(which))
            }
        }
        dialogBuilder.setPositiveButton("Done",
            DialogInterface.OnClickListener { dialog, whichButton -> })
        val b = dialogBuilder.create()
        b.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            if(user.energy>3 && user.money > 300){
                user.energy -=3
                user.money -=300
                if(user.funny<20){
                    user.funny = 0
                }else{
                    user.funny -=20
                }
                if(user.food>70){
                  user.food = 100
                }else{
                    user.food +=30
                }
                binding.progressFoodP.setProgress(user.food)
                binding.progressFunP.setProgress(user.funny)
                binding.progressHygieneP.setProgress(user.hygiene)
                Firebase.firestore
                    .collection("users").document(Firebase.auth.currentUser!!.uid).set(user)
            }

        }
    }

    fun showDialogJob(){
        val options= arrayOf("Campaña de marketing", "Contabilidad", "Programación")
        val selectedList = ArrayList<Int>()
        val dialogBuilder = AlertDialog.Builder(context)
        //dialogBuilder.setTitle("Supermercado")
        dialogBuilder.setMessage("Selecciona que actividad deseas realizar en el supermercado: ")
        dialogBuilder.setMultiChoiceItems(options, null){
                dialog, which, isChecked ->
            if(isChecked){
                selectedList.add(which)
            }else if(selectedList.contains(which)){
                selectedList.remove(Integer.valueOf(which))
            }
        }
        dialogBuilder.setPositiveButton("DONE") { dialogInterface, i ->
            val selectedStrings = ArrayList<String>()

            for (j in selectedList.indices) {
                selectedStrings.add(options[selectedList[j]])
            }
        }

        dialogBuilder.show()

        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            if (user.energy > 4) {
                user.energy -= 4
                user.money += 700
                if(user.funny<30){
                    user.funny = 0
                }else{
                    user.funny -= 30
                }
                if(user.food<30){
                    user.food=0
                }else{
                    user.food -= 30
                }
                if(user.hygiene<30){
                    user.hygiene = 0
                }else{
                    user.hygiene -= 30
                }
                binding.progressFoodP.setProgress(user.food)
                binding.progressFunP.setProgress(user.funny)
                binding.progressHygieneP.setProgress(user.hygiene)
                Firebase.firestore
                    .collection("users").document(Firebase.auth.currentUser!!.uid).set(user)
            }
        }
    }

    fun showDialogPark(){
        //montarse a la noria, montar montaña rusa, montar carros chocones, comprar comida
        val options= arrayOf("montarse a la noria", "montar montaña rusa", "montar carros chocones", "comprar comida")
        val selectedList = ArrayList<Int>()
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Parque de diversiones")
        dialogBuilder.setMessage("Selecciona que actividad deseas realizar en el parque: ")
        dialogBuilder.setMultiChoiceItems(options, null){
                dialog, which, isChecked ->
            if(isChecked){
                selectedList.add(which)
            }else if(selectedList.contains(which)){
                selectedList.remove(Integer.valueOf(which))
            }
        }
        dialogBuilder.setPositiveButton("Done",
            DialogInterface.OnClickListener { dialog, whichButton -> })
        val b = dialogBuilder.create()
        b.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            if(user.energy>3 && user.money > 200){
                user.energy -=3
                user.money -=200
                if(user.funny>70){
                    user.funny = 100
                }else{
                    user.funny +=30
                }
                if(user.food>50){
                    user.food = 100
                }else{
                    user.food +=50
                }
                if(user.hygiene<20){
                    user.hygiene=0
                }else{
                    user.hygiene -=20
                }
                binding.progressFoodP.setProgress(user.food)
                binding.progressFunP.setProgress(user.funny)
                binding.progressHygieneP.setProgress(user.hygiene)
                Firebase.firestore
                    .collection("users").document(Firebase.auth.currentUser!!.uid).set(user)

            }
             }
    }

    fun showDialogSpa(){
        //dormir, descansar, masaje
        val options= arrayOf("dormir", "descansar", "masaje")
        val selectedList = ArrayList<Int>()
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Spa")
        dialogBuilder.setMessage("Selecciona que actividad deseas realizar en el spa: ")
        dialogBuilder.setMultiChoiceItems(options, null){
                dialog, which, isChecked ->
            if(isChecked){
                selectedList.add(which)
            }else if(selectedList.contains(which)){
                selectedList.remove(Integer.valueOf(which))
            }
        }
        dialogBuilder.setPositiveButton("Done",
            DialogInterface.OnClickListener { dialog, whichButton -> })
        val b = dialogBuilder.create()
        b.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            if(user.money > 1000){
                if(user.energy>8){
                    user.energy = 10
                }else{
                    user.energy +=2
                }
                user.money -=1000
                if(user.funny>85){
                    user.funny=100
                }else{
                    user.funny +=15
                }
                if(user.food>90){
                    user.food = 100
                }else{
                    user.food +=10
                }
                if(user.hygiene>70){
                    user.hygiene = 100
                }else{
                    user.hygiene +=30
                }
                binding.progressFoodP.setProgress(user.food)
                binding.progressFunP.setProgress(user.funny)
                binding.progressHygieneP.setProgress(user.hygiene)
                Firebase.firestore
                    .collection("users").document(Firebase.auth.currentUser!!.uid).set(user)

            }
           }
    }


    companion object {
        @JvmStatic
        fun newInstance() = PaymentsFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}