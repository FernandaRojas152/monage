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
import edu.icesi.monage.databinding.FragmentTransactionsBinding
import edu.icesi.monage.model.Invertion
import edu.icesi.monage.model.RvAdapter
import edu.icesi.monage.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter : RvAdapter
    private lateinit var invList : List<Invertion>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionsBinding.inflate(inflater,container,false)
        return binding.root

        loadInvertion()
        rvAdapter = RvAdapter(invList)
        binding.inverRecycler.adapter = rvAdapter

    }

    private fun loadInvertion() {
        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            withContext(Dispatchers.Main) {
                binding.energyInv.text = "${user.energy}/10"
                binding.moneyInv.text = "$${user.money}"
                binding.inverMoney.text = "${user.moneyInv}"
                binding.utilidad.text = "${user.utilidad}"
            }
        }
    }




    companion object {
        @JvmStatic
        fun newInstance() = TransactionsFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}