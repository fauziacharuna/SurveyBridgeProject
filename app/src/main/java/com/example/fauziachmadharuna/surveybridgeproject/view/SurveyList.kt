package com.example.fauziachmadharuna.surveybridgeproject.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.fauziachmadharuna.surveybridgeproject.R
import com.example.fauziachmadharuna.surveybridgeproject.core.SurveyActivity
import com.example.fauziachmadharuna.surveybridgeproject.adapter.SurveyAdapter
import com.example.fauziachmadharuna.surveybridgeproject.model.SurveyModel
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fab.*
import kotlinx.android.synthetic.main.survey_list_activity.*

class SurveyList : AppCompatActivity() {
    private val TAG = "SurveyListActivity"
    private var mAdapter: SurveyAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    private var fabAdd : FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_list_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        //home navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
       fab.setOnClickListener{

           val intent = Intent(this, SurveyActivity::class.java)
           startActivity(intent)
       }

        firestoreDB = FirebaseFirestore.getInstance()

        loadSurveyList()

        firestoreListener = firestoreDB!!.collection("SurveyBridge")
            .addSnapshotListener(EventListener { documentSnapshot, e ->
                if (e != null) {
                    Log.e(TAG, "Listen Failed!", e)
                    return@EventListener

                }
                val surveyList = mutableListOf<SurveyModel>()

                if (documentSnapshot != null) {
                    for (doc in documentSnapshot) {
                        val survey = doc.toObject(SurveyModel::class.java)
                        survey.id = doc.id
                        surveyList.add(survey)
                    }
                }
                mAdapter = SurveyAdapter(surveyList, applicationContext, firestoreDB!!)
                mAdapter!!.notifyDataSetChanged()
                rvSurveyList.adapter = mAdapter
            })
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    private fun loadSurveyList() {
        firestoreDB!!.collection("SurveyBridge")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val surveyList = mutableListOf<SurveyModel>()

                    for (doc in task.result!!) {
                        val survey = doc.toObject<SurveyModel>(SurveyModel::class.java)
                        survey.id = doc.id
                        surveyList.add(survey)
                    }

                    mAdapter = SurveyAdapter(surveyList, applicationContext, firestoreDB!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    rvSurveyList.layoutManager = mLayoutManager
                    rvSurveyList.itemAnimator = DefaultItemAnimator()
                    rvSurveyList.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting document!", task.exception)
                }


            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        if (item != null) {
//            if (item.itemId == R.id.addSurvey) {
//                val intent = Intent(this, SurveyActivity::class.java)
//                startActivity(intent)
//            }
//        }

        when (item!!.itemId){
//            R.id.logOut->{
//
//            }
            R.id.action_settings ->{

            }
        }
        return super.onOptionsItemSelected(item)
    }
}