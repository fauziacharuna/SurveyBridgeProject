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
import com.example.fauziachmadharuna.surveybridgeproject.core.SurveyActivity
import com.example.fauziachmadharuna.surveybridgeproject.model.SurveyModel
import com.google.firebase.firestore.FirebaseFirestore


class SurveyAdapter(
    private val surveyList: MutableList<SurveyModel>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore
)

    : RecyclerView.Adapter<SurveyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: SurveyAdapter.ViewHolder, position: Int) {
        val survey = surveyList[position]


        holder!!.bridgeNameText.text = survey.bridgeName
        holder!!.bridgeLocationText.text = survey.bridgeLocation
        holder!!.kerusakanText.text = survey.kerusakan

        holder!!.edit.setOnClickListener { updateSurvey(survey) }
        holder!!.delete.setOnClickListener { deleteSurvey(survey.id!!, position) }

    }


    override fun getItemCount(): Int {
        return surveyList.size
    }

    inner class ViewHolder internal constructor(View: View) : RecyclerView.ViewHolder(View) {
        internal var bridgeNameText: TextView
        internal var bridgeLocationText: TextView
        internal var kerusakanText: TextView
        internal var edit: ImageView
        internal var delete: ImageView

        init {
            bridgeNameText = View.findViewById(R.id.tvBridgeName)
            bridgeLocationText = View.findViewById(R.id.tvBridgeLocation)
            kerusakanText = View.findViewById(R.id.tv_kerusakan)

            edit = View.findViewById(R.id.ivEdit)
            delete = View.findViewById(R.id.ivDelete)

        }

    }

    private fun updateSurvey(survey: SurveyModel) {
        val intent = Intent(context, SurveyActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.putExtra("UpdateSurveyID", survey.id)
        intent.putExtra("UpdataBridgeName", survey.bridgeName)
        intent.putExtra("UpdateBridgeLocation", survey.bridgeLocation)
        intent.putExtra("UpdateSurveyorName", survey.surveyorName)
        intent.putExtra("UpdateSistem", survey.sistem)
        intent.putExtra("UpdateKomponen", survey.komponen)
        intent.putExtra("UpdateSubKomponen", survey.subKomponen)
        intent.putExtra("UpdateBahan", survey.bahan)
        intent.putExtra("UpdateKerusakan", survey.kerusakan)
        context.startActivity(intent)
    }

    private fun deleteSurvey(id: String, position: Int) {
        firestoreDB.collection("SurveyBridge")
            .document(id)
            .delete()
            .addOnCompleteListener {
                surveyList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, surveyList.size)
                Toast.makeText(context, "Survey has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}