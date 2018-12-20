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
import com.example.fauziachmadharuna.surveybridgeproject.SurveyActivity
import com.example.fauziachmadharuna.surveybridgeproject.model.SurveyModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_survey.view.*


class SurveyAdapter(
    private val surveyList: MutableList<SurveyModel>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore)

 : RecyclerView.Adapter<SurveyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurveyAdapter.ViewHolder, position: Int) {
        val survey = surveyList[position]
        holder!!.bridgeName.text=survey.bridgeName
        holder.bridgeLocation.text=survey.bridgeLocation
        holder.kerusakan.text=survey.kerusakan

    }

    override fun getItemCount(): Int {
        return surveyList.size
    }
    inner class ViewHolder internal constructor(View : View) : RecyclerView.ViewHolder(View){
        internal  var bridgeName: TextView
        internal var bridgeLocation : TextView
        internal var kerusakan : TextView
        internal var edit : ImageView
        internal var delete : ImageView
        init {
            bridgeName=View.findViewById(R.id.tvBridgeName)
            bridgeLocation=View.findViewById(R.id.tvBridgeLocation)
            kerusakan=View.findViewById(R.id.tv_kerusakan)

            edit=View.findViewById(R.id.ivEdit)
            delete=View.findViewById(R.id.ivDelete)
        }

    }

    private fun updateSurvey(survey : SurveyModel){
        val intent= Intent(context, SurveyActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("UpdateSurveyID", survey.id)
        intent.putExtra("UpdateBridgeName", survey.bridgeName)
        intent.putExtra("UpdateBridgeLocation",survey.bridgeLocation)
        intent.putExtra("UpdateSurveyorName",survey.surveyorName)
        intent.putExtra("UpdateSistem",survey.sistem)
        intent.putExtra("UpdateKomponen",survey.komponen)
        intent.putExtra("updateSubKomponen",survey.subKomponen)
        intent.putExtra("UpdateBahan",survey.bahan)
        intent.putExtra("UpdateKerusakan",survey.kerusakan)
        context.startActivity(intent)
    }
    private fun deleteSurvey(id:String, position: Int){
        firestoreDB.collection("SUrveyBridge")
            .document(id)
            .delete()
            .addOnCompleteListener{
                surveyList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,surveyList.size)
                Toast.makeText(context,"Survey has been deleted!",Toast.LENGTH_SHORT).show()
            }
    }

}