package edu.icesi.monage

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.icesi.monage.databinding.FragmentPaymentsBinding
import edu.icesi.monage.model.State
import edu.icesi.monage.model.User
import edu.icesi.monage.model.UserGameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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

        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            val userState = UserGameState(user)
            val average = userState.totalCalculation()
            val state = State.calculateState(average)
            user.state = state
            withContext(Dispatchers.Main) {

                binding.energeyT.text = "${user.energy}/10"
                binding.moneyT.text = "$${user.money}"
                val food = user.food
                binding.progressFoodP.setProgress(food)

                val hygiene = user.hygiene
                binding.progressHygieneP.setProgress(hygiene)

                val funn = user.funny
                binding.progressFunP.setProgress(funn)

            }
        }


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
        //R.layout.dialog_market
        //val appContext = requireContext().applicationContext
        //val view= View.inflate(context, R.layout.dialog_market,null)
        val dialogBuilder = AlertDialog.Builder(context)
        //val view= layoutInflater.inflate(R.layout.dialog_market,null)

        dialogBuilder.setView(R.layout.dialog_market)
        val dialog= dialogBuilder.create()
        dialog.window?.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        dialog.window?.attributes?.windowAnimations= R.style.DialogAnimation

        dialog.show()


        //comprar frutas, comprar verduras, comprar carne
        /**val options= arrayOf("Comprar frutas", "Comprar verduras", "Comprar carne")
        val selectedList = ArrayList<Int>()
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Supermercado")
        //dialogBuilder.setMessage("Selecciona que actividad deseas realizar en el supermercado: ")
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

            Toast.makeText(context, "Items selected are: " + Arrays.toString(selectedStrings.toTypedArray()), Toast.LENGTH_SHORT).show()
        }
        */

      // dialogBuilder.show()
        var accept: Button = dialog.findViewById(R.id.acceptSpa)
        accept.setOnClickListener {
            dialog.dismiss()
        }

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
                if(user.energy <= 0){
                    if(user.days == 30) {



                        //Aqui va que pasa cuando llegue a los 30 dias


                        val dialogBuilder = AlertDialog.Builder(context)
                        dialogBuilder.setTitle("Congratulations")
                        dialogBuilder.setMessage("Felicidades, terminaste el juego con un score : "
                                + user.score).setPositiveButton("Reintentar",{
                            dialog,which ->
                            user.score = 0
                            user.money = 1500
                            user.energy = 10
                            user.food = 60
                            user.days = 0
                            user.state = "NEUTRAL"
                            }).setNegativeButton("Gracias,estoy bien con mi score",{
                              dialog,which -> dialog.dismiss()
                        })

                        dialogBuilder.show()


                        }



                    }else{
                        user.days += 1
                        user.money += 1500
                        user.energy += 10
                        user.score += 5*(user.food) +2*(user.hygiene) +user.funny
                    }
                }
                binding.progressFoodP.setProgress(user.food)
                binding.progressFunP.setProgress(user.funny)
                binding.progressHygieneP.setProgress(user.hygiene)
                Firebase.firestore
                    .collection("users").document(Firebase.auth.currentUser!!.uid).set(user)
            }


    }

    fun showDialogJob(){
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setView(R.layout.dialog_job)
        val dialog= dialogBuilder.create()
        dialog.window?.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        dialog.window?.attributes?.windowAnimations= R.style.DialogAnimation

        dialog.show()
        /**val options= arrayOf("Campaña de marketing", "Contabilidad", "Programación")
        val selectedList = ArrayList<Int>()
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Trabajo")
        //dialogBuilder.setMessage("Selecciona que actividad deseas realizar en el trabajo: ")
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

            Toast.makeText(context, "Items selected are: " + Arrays.toString(selectedStrings.toTypedArray()), Toast.LENGTH_SHORT).show()
        }

        dialogBuilder.show()*/

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

                if(user.energy <= 0) {
                    if (user.days == 30) {
                        val dialogBuilder = AlertDialog.Builder(context)
                        dialogBuilder.setTitle("Congratulations")
                        dialogBuilder.setMessage("Felicidades, terminaste el juego con un score : "
                                + user.score).setPositiveButton("Reintentar",{
                                dialog,which ->
                            user.score = 0
                            user.money = 1500
                            user.energy = 10
                            user.food = 60
                            user.days = 0
                            user.state = "NEUTRAL"
                        }).setNegativeButton("Gracias,estoy bien con mi score",{
                                dialog,which -> dialog.dismiss()
                        })

                        dialogBuilder.show()


                        //Aqui va que pasa cuando llegue a los 30 dias


                    } else {
                        user.days += 1
                        user.money += 1500
                        user.energy += 10
                        user.score += 5*(user.food) +2*(user.hygiene) +user.funny
                    }
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
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setView(R.layout.dialog_park)
        val dialog= dialogBuilder.create()
        dialog.window?.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        dialog.window?.attributes?.windowAnimations= R.style.DialogAnimation
        /**
        //montarse a la noria, montar montaña rusa, montar carros chocones, comprar comida
        val options= arrayOf("Montarse a la noria", "Montar montaña rusa", "Montar carros chocones", "Comprar comida")
        val selectedList = ArrayList<Int>()
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Parque de diversiones")
        //dialogBuilder.setMessage("Selecciona que actividad deseas realizar en el parque de diversiones: ")
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

            Toast.makeText(context, "Items selected are: " + Arrays.toString(selectedStrings.toTypedArray()), Toast.LENGTH_SHORT).show()
        }

        dialogBuilder.show()
        */
        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            //Hay la plata no deberia ser 0 -----------------
            if(user.energy>=1 && user.money >= 0){
                user.energy -=1
                //user.money -=200
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
                if(user.energy <= 0) {
                    if (user.days == 30) {
                        val dialogBuilder = AlertDialog.Builder(context)
                        dialogBuilder.setTitle("Congratulations")
                        dialogBuilder.setMessage("Felicidades, terminaste el juego con un score : "
                                + user.score).setPositiveButton("Reintentar",{
                                dialog,which ->
                            user.score = 0
                            user.money = 1500
                            user.energy = 10
                            user.food = 60
                            user.days = 0
                            user.state = "NEUTRAL"
                        }).setNegativeButton("Gracias,estoy bien con mi score",{
                                dialog,which -> dialog.dismiss()
                        })

                        dialogBuilder.show()


                        //Aqui va que pasa cuando llegue a los 30 dias


                    } else {
                        user.days += 1
                        user.money += 1500
                        user.energy += 10
                        user.score += 5*(user.food) +2*(user.hygiene) +user.funny
                    }
                }
                binding.progressFoodP.setProgress(user.food)
                binding.progressFunP.setProgress(user.funny)
                binding.progressHygieneP.setProgress(user.hygiene)
                Firebase.firestore
                    .collection("users").document(Firebase.auth.currentUser!!.uid).set(user)

            }else{
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Energia Insuficiente").setNegativeButton("Aceptar",{
                        dialog,which -> dialog.dismiss()
                })
            }
        }
    }

    fun showDialogSpa(){
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setView(R.layout.dialog_spa)
        val dialog= dialogBuilder.create()
        dialog.window?.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        dialog.window?.attributes?.windowAnimations= R.style.DialogAnimation

        /* dormir, descansar, masaje
        val options= arrayOf("Dormir", "Descansar", "Masaje")
        val selectedList = ArrayList<Int>()
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Spa")
        //dialogBuilder.setMessage("Selecciona que actividad deseas realizar en el spa: ")
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

            Toast.makeText(context, "Items selected are: " + Arrays.toString(selectedStrings.toTypedArray()), Toast.LENGTH_SHORT).show()
        }

        dialogBuilder.show()
        */
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
                if(user.energy <= 0){
                    if(user.days == 30) {
                        val dialogBuilder = AlertDialog.Builder(context)
                        dialogBuilder.setTitle("Congratulations")
                        dialogBuilder.setMessage("Felicidades, terminaste el juego con un score : "
                                + user.score).setPositiveButton("Reintentar",{
                                dialog,which ->
                            user.score = 0
                            user.money = 1500
                            user.energy = 10
                            user.food = 60
                            user.days = 0
                            user.state = "NEUTRAL"
                        }).setNegativeButton("Gracias,estoy bien con mi score",{
                                dialog,which -> dialog.dismiss()
                        })

                        dialogBuilder.show()


                        //Aqui va que pasa cuando llegue a los 30 dias


                    }else{
                        user.days += 1
                        user.money += 1500
                        user.energy += 10
                        user.score += 5*(user.food) +2*(user.hygiene) +user.funny
                    }
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