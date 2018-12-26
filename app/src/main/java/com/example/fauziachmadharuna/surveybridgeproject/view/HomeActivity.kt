package com.example.fauziachmadharuna.surveybridgeproject.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.fauziachmadharuna.surveybridgeproject.*
import com.example.fauziachmadharuna.surveybridgeproject.core.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        listSurvey.setOnClickListener{
            val intent= Intent(this, SurveyView::class.java)
            startActivity(intent)
        }
        cv_evaluation.setOnClickListener{
            val intent2=Intent(this, BridgeList::class.java)
            startActivity(intent2)
        }

        cv_listEngineer.setOnClickListener{
            val intent3=Intent(this, EngineerList::class.java)
            startActivity(intent3)
        }
        cv_planning.setOnClickListener{
            val intent4=Intent(this,PlanningList::class.java)
            startActivity(intent4)
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
}
