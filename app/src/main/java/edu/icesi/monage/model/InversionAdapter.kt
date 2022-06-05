package edu.icesi.monage.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.icesi.monage.R


class InversionAdapter :  RecyclerView.Adapter<InversionViewHolder>(){

    private var invertions = ArrayList<Invertion>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InversionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.invertionrow,parent,false)
        val invertioView = InversionViewHolder(row)
        return invertioView
    }

    override fun onBindViewHolder(holder: InversionViewHolder, position: Int) {

        val invertion = invertions[position]
        holder.nameInv.text = invertion.nameInv
        holder.costInv.text = invertion.costInv

    }

    fun addInvertion(publication:Invertion){
        invertions.add(publication)
    }

    override fun getItemCount(): Int {
        return invertions.size
    }
}