package com.example.fauziachmadharuna.surveybridgeproject.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.fauziachmadharuna.surveybridgeproject.R
import com.example.fauziachmadharuna.surveybridgeproject.adapter.FirestoreUIRCAdapterSurvey
import com.example.fauziachmadharuna.surveybridgeproject.core.LoginActivity
import com.example.fauziachmadharuna.surveybridgeproject.core.SurveyActivity
import com.example.fauziachmadharuna.surveybridgeproject.model.SurveyModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fab.*
import kotlinx.android.synthetic.main.survey_list_activity.*
import kotlin.system.exitProcess

class SurveyView : AppCompatActivity(){
    private var TAG = "SurveyActivity"
    private var adapter : FirestoreRecyclerAdapter<SurveyModel,FirestoreUIRCAdapterSurvey>? =null

    private var firestoreDB : FirebaseFirestore?=null
    private var firestoreListener : ListenerRegistration? = null

    private var surveyList = mutableListOf<SurveyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_list_activity)

        firestoreDB= FirebaseFirestore.getInstance()

        val mLayoutManager=LinearLayoutManager(applicationContext)
        rvSurveyList.layoutManager=mLayoutManager
        rvSurveyList.itemAnimator=DefaultItemAnimator()

        setSupportActionBar(findViewById(R.id.toolbar))
        //home navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        fab.setOnClickListener{

            val intent = Intent(this, SurveyActivity::class.java)
            startActivity(intent)
        }

        loadSurveyList()

        firestoreListener=firestoreDB!!.collection("SurveyBridge")
            .addSnapshotListener(EventListener{
                documentSnapshots, e->
                if (e != null){
                    Log.e(TAG,"Listen Failed",e)
                    return@EventListener
                }
                surveyList= mutableListOf()

//                if (documentSnapshots != null) {
                    for (doc in documentSnapshots!!){
                        val survey = doc.toObject(SurveyModel::class.java)
                        survey.id=doc.id
                        surveyList.add(survey)
                    }
//                }
                adapter!!.notifyDataSetChanged()
                rvSurveyList.adapter=adapter
            })

    }

    private fun loadSurveyList() {
        val query=firestoreDB!!.collection("SurveyBridge").orderBy("BridgeName")
        val response = FirestoreRecyclerOptions.Builder<SurveyModel>()
            .setQuery(query,SurveyModel::class.java)
            .build()

        adapter=object : FirestoreRecyclerAdapter<SurveyModel, FirestoreUIRCAdapterSurvey>(response){
            override fun onBindViewHolder(holder: FirestoreUIRCAdapterSurvey, position: Int, model: SurveyModel) {
                val survey = surveyList[position]

                holder.textBridgeName.text=survey.bridgeName
                holder.textBridgeLocation.text=survey.bridgeLocation
                holder.textKerusakan.text=survey.kerusakan

                holder.imgEdit.setOnClickListener{editSurvey(survey)}
                holder.imgDelete.setOnClickListener{deleteSurvey(survey.id!!)}


            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirestoreUIRCAdapterSurvey {

                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout,parent,false)

                return FirestoreUIRCAdapterSurvey(view)
            }

            override fun onError(e: FirebaseFirestoreException) {
                Log.e(TAG,"Error",e)
            }
        }
        adapter!!.notifyDataSetChanged()
        rvSurveyList.adapter=adapter
    }

    private fun editSurvey(survey : SurveyModel){
        val intent = Intent(this,SurveyActivity::class.java )
        intent.putExtra("UpdataBridgeName", survey.bridgeName)
        intent.putExtra("UpdateBridgeLocation", survey.bridgeLocation)
        intent.putExtra("UpdateSurveyorName", survey.surveyorName)
        intent.putExtra("UpdateSistem", survey.sistem)
        intent.putExtra("UpdateKomponen", survey.komponen)
        intent.putExtra("UpdateSubKomponen", survey.subKomponen)
        intent.putExtra("UpdateBahan", survey.bahan)
        intent.putExtra("UpdateKerusakan", survey.kerusakan)
        startActivity(intent)
    }

    private fun deleteSurvey(id : String){
        firestoreDB!!.collection("SurveyBridge")
            .document(id)
            .delete()
            .addOnCompleteListener{
                Toast.makeText(applicationContext,"Survey Has been deleted!",Toast.LENGTH_SHORT).show()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.logout->{
                FirebaseAuth.getInstance().signOut()
                val intent =Intent(this, LoginActivity::class.java)
                startActivity(intent)

//
            }
            R.id.action_settings-> {

            }
            R.id.exit->{
                exitProcess(-1);
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    override fun onStart() {
        super.onStart()

        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter !=null) {
            adapter!!.stopListening()
        }
    }

}