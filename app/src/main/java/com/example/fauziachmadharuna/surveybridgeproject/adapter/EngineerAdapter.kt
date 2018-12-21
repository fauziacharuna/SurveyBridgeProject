package com.example.fauziachmadharuna.surveybridgeproject.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.fauziachmadharuna.surveybridgeproject.R
import com.example.fauziachmadharuna.surveybridgeproject.core.EngineerActivity
import com.example.fauziachmadharuna.surveybridgeproject.model.EngineerModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.engineer_list_activity.view.*

class EngineerAdapter(
    private val engineerList: MutableList<EngineerModel>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore)
    :RecyclerView.Adapter<EngineerAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.activity_engineer_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EngineerAdapter.ViewHolder, position: Int) {
        val engineer = engineerList[position]

        holder.nameText.text=engineer.name
        holder.jobText.text=engineer.jabatan

        holder.edit.setOnClickListener{updateEngineer(engineer)}
        holder.delete.setOnClickListener{deleteEngineer(engineer.id!!,position)}
    }

    private fun updateEngineer(engineer: EngineerModel) {
        val intent = Intent(context, EngineerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("id",engineer.id)
        intent.putExtra("NameEngineer",engineer.name)
        intent.putExtra("Jabatan",engineer.jabatan)
        context.startActivity(intent)

    }
    private fun deleteEngineer(id: String, position: Int){
        firestoreDB.collection("SurveyBridge").document("engineer").collection("engineerList")
            .document(id)
            .delete()
            .addOnCompleteListener{
                engineerList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,engineerList.size)

                Toast.makeText(context,"Engineer has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return engineerList.size
    }
    inner class ViewHolder internal constructor(view : View) : RecyclerView.ViewHolder(view){
        internal var nameText : TextView
        internal var jobText : TextView

        internal var edit : ImageView
        internal var delete : ImageView

        init {
            nameText=view.findViewById(R.id.et_engineerName)
            jobText=view.findViewById(R.id.et_engineerJobs)

            edit=view.findViewById(R.id.ivEdit)
            delete=view.findViewById(R.id.ivDelete)
        }
    }



}