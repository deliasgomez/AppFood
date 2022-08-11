 package com.example.appfood.view.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.appfood.databinding.ActivityMealBinding
import com.example.appfood.view.fragments.HomeFragment
import com.example.appfood.viewModel.MealViewModel

 class MealActivity : AppCompatActivity() {
     private lateinit var mealId : String
     private lateinit var mealYoutubeLink : String
     private lateinit var binding : ActivityMealBinding
     private val mealViewModel : MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingCase()
        getMealInformationFromIntent()
        mealViewModel.getMealDetails(mealId)
        mealViewModel.mealDetailsLiveData.observe(this, Observer {meal->
            onResponseCase()
            mealYoutubeLink = meal.strYoutube
            binding.categoryMeal.text = "Category : ${meal.strCategory}"
            binding.collapsingToolbar.title = meal.strMeal
            binding.instructionsDescription.text = meal.strInstructions
            binding.areaMeal.text = "Area : ${meal.strArea}"
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imageMealDetail)

        })

        onYoutubeImageClick()

    }

     private fun onYoutubeImageClick() {
         binding.imageVideoMeal.setOnClickListener {
             val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealYoutubeLink))
             startActivity(intent)
         }
     }


     private fun getMealInformationFromIntent() {
         val intent  = intent
         mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!

     }
     private fun loadingCase(){
         binding.progressBar.visibility = View.VISIBLE
         binding.fabFavorite.visibility = View.INVISIBLE
         binding.instructionsTittle.visibility = View.INVISIBLE
         binding.areaMeal.visibility = View.INVISIBLE
         binding.categoryMeal.visibility = View.INVISIBLE
         binding.imageVideoMeal.visibility = View.INVISIBLE

     }

     private fun onResponseCase(){
         binding.progressBar.visibility = View.INVISIBLE
         binding.fabFavorite.visibility = View.VISIBLE
         binding.instructionsTittle.visibility = View.VISIBLE
         binding.areaMeal.visibility = View.VISIBLE
         binding.categoryMeal.visibility = View.VISIBLE
         binding.imageVideoMeal.visibility = View.VISIBLE

     }
 }