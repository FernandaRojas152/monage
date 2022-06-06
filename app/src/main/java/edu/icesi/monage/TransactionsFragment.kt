package edu.icesi.monage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.icesi.monage.databinding.FragmentTransactionsBinding
import edu.icesi.monage.model.InversionAdapter
import edu.icesi.monage.model.Invertion
import edu.icesi.monage.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!


    private var adapter = InversionAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionsBinding.inflate(inflater,container,false)
        loadInvertion()
        val invertionRecycler = binding.inverRecycler
        invertionRecycler.setHasFixedSize(true)
        invertionRecycler.layoutManager = LinearLayoutManager(activity)
        invertionRecycler.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("invertions").addSnapshotListener { result, error ->
                for (invertion in result!!.documents) {
                    val obj = invertion.toObject(Invertion::class.java)!!
                    adapter.addInvertion(obj)
                }
            }
        }


        return binding.root
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

        lifecycleScope.launch(Dispatchers.IO) {
            adapter.clear()
        }

    }
}