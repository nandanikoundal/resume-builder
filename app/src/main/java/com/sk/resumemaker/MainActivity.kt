package com.sk.resumemaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.sk.resumemaker.databinding.ActivityMainBinding
import com.sk.resumemaker.room.ResumeDatabase

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
   private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding:ActivityMainBinding
    lateinit var resumeDatabase: ResumeDatabase
    var personalEntity= "personalEntity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.navHost1)
        appBarConfiguration= AppBarConfiguration(setOf(
            R.id.createviewfragment,R.id.personalnfoFragment,R.id.qualification,R.id.experienceFragment,R.id.CurricularActivity,R.id.skills,R.id.referenceFragment,
        ))
        setupActionBarWithNavController(navController,appBarConfiguration)
        resumeDatabase = ResumeDatabase.getDatabase(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||super.onSupportNavigateUp()
    }
}