package edu.icesi.monage.model

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.icesi.monage.R

class InversionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var nameInv: TextView = itemView.findViewById(R.id.nameInv)
    var costInv: TextView = itemView.findViewById(R.id.costInv)
    var buy : Button = itemView.findViewById(R.id.invBtn)
    var photoInv : ImageView = itemView.findViewById(R.id.photoInv)
}