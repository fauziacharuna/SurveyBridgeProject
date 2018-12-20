package com.example.fauziachmadharuna.surveybridgeproject

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.fauziachmadharuna.surveybridgeproject.model.SurveyModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_survey.*
import kotlinx.android.synthetic.main.item_layout.*

class SurveyActivity : AppCompatActivity() {
    private val TAG = "AddSurveyActivity"

    private var firestoreDB: FirebaseFirestore? = null

    internal var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        firestoreDB = FirebaseFirestore.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
//            id = bundle.getString("Update SurveyID")

            et_bridgeName.setText(bundle.getString("UpdateBridgeName"))
            et_bridgeLocation.setText(bundle.getString("UpdateBridgeLocation"))
            et_surveyorName.setText(bundle.getString("UpdateSurveyorName"))
            et_sistem.setText(bundle.getString("UpdateSistem"))
            et_komponen.setText(bundle.getString("UpdateKomponen"))
            et_subKomponen.setText(bundle.getString("UpdateSubKomponen"))
            et_bahan.setText(bundle.getString("UpdateBahan"))
            et_kerusakan.setText(bundle.getString("UpdateKerusakan"))
            et_tags.setText(bundle.getString("UpdateTags"))

        }
            val clickListener = View.OnClickListener { view ->
                val bridgeName = et_bridgeName.text.toString()
                val bridgeLocation = et_bridgeLocation.text.toString()
                val surveyorName = et_surveyorName.text.toString()
                val sistem = et_sistem.text.toString()
                val komponen = et_komponen.text.toString()
                val subKomponen = et_subKomponen.text.toString()
                val bahan = et_bahan.text.toString()
                val kerusakan = et_kerusakan.text.toString()
                val tags=et_tags.text.toString()
                val tagList: List<String> = tags.split("\\s*,\\s*")
                tagList.toMutableList()

                when (view.id) {

                    R.id.btn_Add -> addSurvey(
                        bridgeName,
                        bridgeLocation,
                        surveyorName,
                        sistem,
                        komponen,
                        subKomponen,
                        bahan,
                        kerusakan,tagList
                    )
                    R.id.btn_Update -> updateSurvey(
                        id,
                        bridgeName,
                        bridgeLocation,
                        surveyorName,
                        sistem,
                        komponen,
                        subKomponen,
                        bahan,
                        kerusakan,tagList
                    )
                }
            }

            val btnUpdate = findViewById<Button>(R.id.btn_Update);
            btnUpdate.setOnClickListener(clickListener);

            val btnAdd = findViewById<Button>(R.id.btn_Add)
            btnAdd.setOnClickListener(clickListener)



    }


    private fun updateSurvey(
        id: String,
        bridgeName: String,
        bridgeLocation: String,
        surveyorName: String,
        sistem: String,
        komponen: String,
        subKomponen: String,
        bahan: String,
        kerusakan: String,tagList: List<String>
    ) {
        val survey = SurveyModel(
            id,
            bridgeName,
            bridgeLocation,
            surveyorName,
            sistem,
            komponen,
            subKomponen,
            bahan,
            kerusakan,tagList
        ).toMap()

        firestoreDB!!.collection("SUrveyBridge")
            .document(id)
            .set(survey)
            .addOnSuccessListener {
                Log.e(TAG, "Survey Document update Successfull!")
                Toast.makeText(applicationContext, "Survey has been updated!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding Survey Document!", e)
                Toast.makeText(applicationContext, "Survey couldn't updated!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addSurvey(
        bridgeName: String,
        bridgeLocation: String,
        surveyorName: String,
        sistem: String,
        komponen: String,
        subKomponen: String,
        bahan: String,
        kerusakan: String, tagList: List<String>
    ) {
        val survey = SurveyModel(
            bridgeName,
            bridgeLocation,
            surveyorName,
            sistem,
            komponen,
            subKomponen,
            bahan,
            kerusakan,tagList
        ).toMap()
        var data : String = ""
        
        firestoreDB!!.collection("SUrveyBridge")

            .add(survey)
            .addOnSuccessListener { documentReference ->
                Log.e(TAG, "DocumentSnapshot writted with ID : " + documentReference.id)
                Toast.makeText(applicationContext, "Survey been added! ", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding survey document", e)
                Toast.makeText(applicationContext, "Survey couldn't added!", Toast.LENGTH_SHORT).show()
            }
    }
}