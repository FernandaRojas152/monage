package edu.icesi.monage.model

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.icesi.monage.NavigationActivity
import edu.icesi.monage.R
import edu.icesi.monage.TransactionsFragment
import kotlinx.coroutines.Dispatchers
import kotlin.math.roundToInt


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
            var context = it.context
            Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).addSnapshotListener { result, error ->

                    val user = result?.toObject(User::class.java)!!

                    if(user.money >= invertion.costInv){
                        user.moneyInv += invertion.costInv
                        user.money -= invertion.costInv
                        user.utilidad += (invertion.costInv*0.2).roundToInt()
                    } else{
                        val dialogBuilder = AlertDialog.Builder(context)
                        //val view= layoutInflater.inflate(R.layout.dialog_market,null)

                        dialogBuilder.setView(R.layout.dialog_no_invertion)
                        val dialog= dialogBuilder.create()
                        dialog.window?.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
                        dialog.window?.setBackgroundDrawableResource(android.R.color.white)
                        dialog.window?.setGravity(Gravity.CENTER)
                        dialog.setCancelable(true)
                        dialog.window?.attributes?.windowAnimations= R.style.DialogAnimation
                        dialog.show()

                        var accept: Button = dialog.findViewById(R.id.accept)
                        accept.setOnClickListener {
                            dialog.dismiss()
                        }

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

