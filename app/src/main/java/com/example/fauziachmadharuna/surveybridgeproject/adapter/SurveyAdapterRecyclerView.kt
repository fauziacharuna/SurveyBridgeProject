package com.example.fauziachmadharuna.surveybridgeproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.example.fauziachmadharuna.surveybridgeproject.R
import com.example.fauziachmadharuna.surveybridgeproject.model.SurveyModel
import com.google.firebase.firestore.FirebaseFirestore


class SurveyAdapterRecyclerView(
    private val surveyList: MutableList<SurveyModel>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore){
//) : RecyclerView.Adapter<SurveyAdapterRecyclerView.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_layout,parent,false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: SurveyAdapterRecyclerView.ViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
}