package com.example.mvvmarchitecture.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.adapter.MyRecycleViewAdapter
import com.example.mvvmarchitecture.databinding.ActivityPersonBinding
import com.example.mvvmarchitecture.factory.PersonDetailsViewModelFactory
import com.example.mvvmarchitecture.model.Person
import com.example.mvvmarchitecture.repository.PersonRepository
import com.example.mvvmarchitecture.roomdb.PersonDatabase
import com.example.mvvmarchitecture.viewmodel.PersonViewModel

class PersonActivity : BaseActivity() {
    private lateinit var binding: ActivityPersonBinding
    private lateinit var personViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person)
//        binding = ActivityPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.personRecyclerview.layoutManager = LinearLayoutManager(this)

        val dao = PersonDatabase.getInstance(this).dao
        val personRepository = PersonRepository(dao)
        val factory = PersonDetailsViewModelFactory(personRepository)
        personViewModel = ViewModelProvider(this, factory)[PersonViewModel::class.java]

        binding.personViewModel = personViewModel
        binding.lifecycleOwner = this

        displayPersonsList()

        personViewModel.navigateToDetails.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayPersonsList(){
        personViewModel.personList.observe(this, Observer {
            binding.personRecyclerview.adapter = MyRecycleViewAdapter(it, {selectedItem: Person -> onItemClickListener(selectedItem)})
        })
    }

    private fun onItemClickListener(person: Person) {
        Toast.makeText(this, "Selected name is ${person.name}", Toast.LENGTH_SHORT).show()
        personViewModel.saveOrUpdateClick()
    }
}