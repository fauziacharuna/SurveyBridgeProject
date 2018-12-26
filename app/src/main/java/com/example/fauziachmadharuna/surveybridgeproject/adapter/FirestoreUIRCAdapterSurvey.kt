package com.example.fauziachmadharuna.surveybridgeproject.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.fauziachmadharuna.surveybridgeproject.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import kotlinx.android.synthetic.main.item_layout.view.*

class FirestoreUIRCAdapterSurvey(view : View) : RecyclerView.ViewHolder(view)  {
    var textBridgeName : TextView
    var textBridgeLocation : TextView
    var textKerusakan : TextView

    var imgEdit : ImageView
    var imgDelete : ImageView

    init {
        textBridgeName=view.findViewById(R.id.tvBridgeName)
        textBridgeLocation=view.findViewById(R.id.tvBridgeLocation)
        textKerusakan=view.findViewById(R.id.tv_kerusakan)

        imgEdit=view.findViewById(R.id.ivEdit)
        imgDelete=view.findViewById(R.id.ivDelete)
    }

}