package com.example.fauziachmadharuna.surveybridgeproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.fauziachmadharuna.surveybridgeproject.adapter.EngineerAdapter
import com.example.fauziachmadharuna.surveybridgeproject.adapter.SurveyAdapter
import com.example.fauziachmadharuna.surveybridgeproject.core.EngineerActivity
import com.example.fauziachmadharuna.surveybridgeproject.model.EngineerModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.engineer_list_activity.*

class EngineerList : AppCompatActivity() {
    private val TAG = "EngineerListActivity"
    private var mAdapter: EngineerAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    private var fabtombol : FloatingActionButton? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.engineer_list_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        firestoreDB = FirebaseFirestore.getInstance()
        fabtombol=findViewById(R.id.fab)
        fabtombol!!.setOnClickListener{view ->
            val intent = Intent(this, EngineerActivity::class.java)
            startActivity(intent)
        }

        loadEngineerList()

        firestoreListener = firestoreDB!!.collection("SurveyBridge").document("engineer").collection("listEngineer")
            .addSnapshotListener(EventListener { documentSnapshot, e ->
                if (e != null) {
                    Log.e(TAG, "Listen Failed!", e)
                    return@EventListener

                }
                val engineerList = mutableListOf<EngineerModel>()

                if (documentSnapshot != null) {
                    for (doc in documentSnapshot) {
                        val engineer = doc.toObject(EngineerModel::class.java)
                        engineer.id = doc.id
                        engineerList.add(engineer)
                    }
                }
                mAdapter = EngineerAdapter(engineerList, applicationContext, firestoreDB!!)
                mAdapter!!.notifyDataSetChanged()
                rv_enginerList.adapter = mAdapter
            })


    }

    private fun loadEngineerList() {
        firestoreDB!!.collection("SurveyBridge").document("engineer").collection("listEngineer")
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val engineerList= mutableListOf<EngineerModel>()

                    for (doc in task.result!!){
                        val eng = doc.toObject<EngineerModel>(EngineerModel::class.java)
                        eng.id=doc.id
                        engineerList.add(eng)
                    }
                    mAdapter= EngineerAdapter(engineerList,applicationContext,firestoreDB!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    rv_enginerList.layoutManager=mLayoutManager
                    rv_enginerList.itemAnimator=DefaultItemAnimator()
                    rv_enginerList.adapter=mAdapter

                }else{
                    Log.d(TAG,"Error getting document", task.exception)

                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }


}
