package edu.icesi.monage.model

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        holder.costInv.text = ""+invertion.costInv
        Glide.with(holder.photoInv.context).load(invertions[position].photoInv).into(holder.photoInv)

        holder.buy.setOnClickListener {

            Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).addSnapshotListener { result, error ->

                    val user = result?.toObject(User::class.java)!!

                    if(user.money >= invertion.costInv){
                        user.moneyInv += invertion.costInv
                        user.money -= invertion.costInv
                    } else{
                    }

                    Firebase.firestore
                        .collection("users").document(Firebase.auth.currentUser!!.uid).set(user)
                }

        }


    }

    fun addInvertion(publication:Invertion){
        invertions.add(publication)
        notifyItemInserted(invertions.lastIndex)

    }

    fun clear() {
        val size: Int = invertions.size
        invertions.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun getItemCount(): Int {
        return invertions.size

    }



}

