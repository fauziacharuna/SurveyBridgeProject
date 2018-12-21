package com.example.fauziachmadharuna.surveybridgeproject.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fauziachmadharuna.surveybridgeproject.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_layout.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        listSurvey.setOnClickListener{
            val intent= Intent(this, SurveyList::class.java)
            startActivity(intent)
        }
        cv_evaluation.setOnClickListener{
            val intent2=Intent(this, BridgeList::class.java)
            startActivity(intent2)
        }

        cv_listEngineer.setOnClickListener{
            val intent3=Intent(this,EngineerList::class.java)
            startActivity(intent3)
        }
        cv_planning.setOnClickListener{
            val intent4=Intent(this,PlanningList::class.java)
            startActivity(intent4)
        }



    }
}
