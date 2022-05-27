package edu.icesi.monage.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.icesi.monage.databinding.InverItemBinding

class RvAdapter(
    var invList: List<Invertion>,
) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: InverItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = InverItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(invList[position]){
                binding.nameInv.text = this.nameInv
                binding.costInv.text = this.costInv
            }
        }
    }

    override fun getItemCount(): Int {
        return invList.size
    }
}