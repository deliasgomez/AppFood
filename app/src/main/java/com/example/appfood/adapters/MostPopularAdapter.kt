package com.example.appfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appfood.R
import com.example.appfood.databinding.PopularItemsBinding
import com.example.appfood.pojo.MealsByCategory

class MostPopularAdapter: RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    private var mealList = ArrayList<MealsByCategory>()

    fun setMeals(mealList : ArrayList<MealsByCategory>){
        this.mealList = mealList
        notifyDataSetChanged()

    }

    class PopularMealViewHolder( view: View ): RecyclerView.ViewHolder(view){
        val binding = PopularItemsBinding.bind(view)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_items, parent, false)

        return PopularMealViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imagePopularItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealList[position])
        }

    }

    override fun getItemCount(): Int = mealList.size

}