package com.example.appfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appfood.R
import com.example.appfood.databinding.CategoryItemBinding
import com.example.appfood.pojo.Category
import com.example.appfood.pojo.MealsByCategory

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categoriesList = ArrayList<Category>()

    fun setCategory(categoriesList : ArrayList<Category>){
        this.categoriesList = categoriesList
        notifyDataSetChanged()

    }

    class CategoriesViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = CategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)

        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.binding.tvCategoryMeal.text = categoriesList[position].strCategory
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imageCategory)
    }

    override fun getItemCount(): Int = categoriesList.size
}