package edu.icesi.monage

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import edu.icesi.monage.databinding.FragmentHomeBinding
import edu.icesi.monage.databinding.FragmentPaymentsBinding

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
    }

    fun showDialogJob(){
        val options= arrayOf("Campaña de marketing", "Contabilidad", "Programación")
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