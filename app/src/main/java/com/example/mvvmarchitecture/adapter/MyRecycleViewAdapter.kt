package com.example.mvvmarchitecture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.databinding.ListItemsBinding
import com.example.mvvmarchitecture.model.Person

class MyRecycleViewAdapter(
    private val personList: List<Person>,
    var selectedItem: (Person) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemsBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(personList[position], selectedItem)
    }

    override fun getItemCount(): Int {
        return personList.size
    }
}

class MyViewHolder(private val binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(person: Person, selectedItem: (Person) -> Unit) {
        binding.tvName.text = person.name
        binding.tvEmail.text = person.email
        binding.tvMobile.text = person.phone

        binding.rootItem.setOnClickListener {
            selectedItem(person)
        }
    }
}