package com.example.appfood.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appfood.view.activities.MealActivity
import com.example.appfood.adapters.CategoriesAdapter
import com.example.appfood.adapters.MostPopularAdapter
import com.example.appfood.databinding.FragmentHomeBinding
import com.example.appfood.pojo.Category
import com.example.appfood.pojo.MealsByCategory
import com.example.appfood.pojo.Meal
import com.example.appfood.viewModel.HomeViewModel



class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.appfood.view.fragments.idMeal"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularItemsAdapter = MostPopularAdapter()
        categoryAdapter = CategoriesAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getRandomMeal()
        homeViewModel.randomMealLiveData.observe(viewLifecycleOwner,{meal->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imageRandomMeal)

            this.randomMeal = meal

        })
        onClickRandomMeal()

        prepareItemsRecyclerview()

        homeViewModel.getPopularItems()
        homeViewModel.popularItems.observe(viewLifecycleOwner, { mealList->
            popularItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)

        })

        onPopularItemClick()

        prepareRecyclerviewCategories()
        homeViewModel.getCategories()
        homeViewModel.categoryLiveData.observe(viewLifecycleOwner,{categoryList->
            categoryAdapter.setCategory(categoriesList = categoryList as ArrayList<Category>)

        })

    }

    private fun prepareRecyclerviewCategories() {
        binding.recyclerviewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter


        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            startActivity(intent)
        }
    }

    private fun prepareItemsRecyclerview() {
        binding.HomeRecyclerPopularItems.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }

    }

    private fun onClickRandomMeal() {
        binding.homeCardView.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}