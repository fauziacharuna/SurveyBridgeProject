package com.example.fauziachmadharuna.surveybridgeproject

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.fauziachmadharuna.surveybridgeproject.adapter.SurveyAdapter
import com.example.fauziachmadharuna.surveybridgeproject.model.SurveyModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_main.*

class SurveyList : AppCompatActivity() {
    private val TAG = "SurveyListActivity"
    private var mAdapter: SurveyAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_list_activity)

        firestoreDB = FirebaseFirestore.getInstance()

        firestoreListener = firestoreDB!!.collection("SUrveyBridge")
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
                rvSurveyList.adapter = mAdapter
            })
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    private fun loadSurveyList() {
        firestoreDB!!.collection("SUrveyBridge")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val surveyList = mutableListOf<SurveyModel>()

                    for (doc in task.result) {
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
        if (item != null) {
            if (item.itemId == R.id.addSurvey) {
                val intent = Intent(this, SurveyActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}