package edu.icesi.monage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.icesi.monage.databinding.FragmentHomeBinding

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