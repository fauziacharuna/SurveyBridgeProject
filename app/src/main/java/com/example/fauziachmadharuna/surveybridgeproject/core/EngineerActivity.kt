package com.example.fauziachmadharuna.surveybridgeproject.core

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.fauziachmadharuna.surveybridgeproject.R
import com.example.fauziachmadharuna.surveybridgeproject.model.EngineerModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.engineer_activity.*

class EngineerActivity : AppCompatActivity() {
    private val TAG = "AddEngineerActivity"


    private var firestoreDB: FirebaseFirestore? = null

    internal var id: String = ""
    private var sb : Snackbar?=null

    private val fb : FloatingActionButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.engineer_activity)

        setSupportActionBar(findViewById(R.id.toolbar))
        //home navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        firestoreDB = FirebaseFirestore.getInstance()

        var bundle = intent.extras
        if (bundle!=null){
            et_engineerName.setText(bundle.getString("UpdateName"))
            et_engineerJobs.setText(bundle.getString("UpdateJobs"))

        }
        val clickListener = View.OnClickListener{ view ->
            val name= et_engineerName.text.toString()
            val jabatan=et_engineerJobs.text.toString()
            when(view.id){
                R.id.btn_Add_Engineer -> addEngineer(name,jabatan)
            }



        }
        val btnAdd = findViewById<Button>(R.id.btn_Add_Engineer)
        btnAdd.setOnClickListener(clickListener)


    }

    private fun addEngineer(name: String, jabatan: String) {
        var engineer = EngineerModel(name,jabatan).toMap()

        firestoreDB!!.collection("SurveyBridge").document("engineer").collection("listEngineer")
            .add(engineer)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG,"DocumentSnapshot writted with ID:  " + documentReference.id)
                Toast.makeText(this,"Berhasil menambahkan engineer",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                Log.e(TAG, "Error adding engineer document", e)
                Toast.makeText(applicationContext, "Engineer couldn't added!", Toast.LENGTH_SHORT).show()
            }

    }


}