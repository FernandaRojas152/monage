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

        binding.progressFood.setProgress(50,false)
        binding.foodTxt.text = "${binding.progressFood.progress.toString()}${"%"}"
        binding.progressHygiene.setProgress(100,false)
        binding.hygieneTxt.text = "${binding.progressHygiene.progress.toString()}${"%"}"
        binding.progressFun.setProgress(10,false)
        binding.funTxt.text = "${binding.progressFun.progress.toString()}${"%"}"

        return binding.root
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