package com.example.mvvmarchitecture.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmarchitecture.repository.PersonRepository
import com.example.mvvmarchitecture.viewmodel.PersonViewModel

class PersonDetailsViewModelFactory (private val personRepository: PersonRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonViewModel::class.java)){
            return PersonViewModel(personRepository) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}