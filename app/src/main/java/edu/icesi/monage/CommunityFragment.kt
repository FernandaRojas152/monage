package edu.icesi.monage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.icesi.monage.databinding.FragmentCommunityBinding
import edu.icesi.monage.databinding.FragmentHomeBinding

class CommunityFragment : Fragment() {

    private var  _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommunityBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CommunityFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}