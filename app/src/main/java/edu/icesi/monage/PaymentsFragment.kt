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
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("The message here")
        dialogBuilder.setPositiveButton("Done",
            DialogInterface.OnClickListener { dialog, whichButton -> })
        val b = dialogBuilder.create()
        b.show()
    }

    fun showDialogJob(){
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("The message here")
        dialogBuilder.setPositiveButton("Done",
            DialogInterface.OnClickListener { dialog, whichButton -> })
        val b = dialogBuilder.create()
        b.show()
    }

    fun showDialogPark(){
        //montarse a la noria, montar montaña rusa, montar carros chocones, comprar comida
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("The message here")
        dialogBuilder.setPositiveButton("Done",
            DialogInterface.OnClickListener { dialog, whichButton -> })
        val b = dialogBuilder.create()
        b.show()
    }

    fun showDialogSpa(){
        //dormir, descansar, masaje
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("The message here")
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