package com.example.mvvmarchitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.model.Event
import com.example.mvvmarchitecture.model.Person
import com.example.mvvmarchitecture.repository.PersonRepository
import kotlinx.coroutines.launch

class PersonViewModel(private val personRepository: PersonRepository) : ViewModel() {

    var name = MutableLiveData<String?>()
    var email = MutableLiveData<String?>()
    var phone = MutableLiveData<String?>()

    var saveOrUpdate = MutableLiveData<String>()
    var clearAllOrDelete = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private lateinit var personToUpdateOrDelete: Person

    private var message = MutableLiveData<Event<String>>()
    val navigateToDetails: LiveData<Event<String>>
        get() = message

    init {
        saveOrUpdate.value = "Save"
        clearAllOrDelete.value = "Clear All"
    }

    fun saveOrUpdateClick() {

        if (isUpdateOrDelete) {
            personToUpdateOrDelete.name = name.value!!
            personToUpdateOrDelete.email = email.value!!
            personToUpdateOrDelete.phone = phone.value!!

            update(personToUpdateOrDelete)

            name.value = null
            email.value = null
            phone.value = null

            saveOrUpdate.value = "Save"
            clearAllOrDelete.value = "Clear All"
        } else {
            val fName = name.value!!
            val lName = email.value!!
            val mobNo = phone.value!!
            insert(Person(0, fName, lName, mobNo))
            name.value = null
            email.value = null
            phone.value = null
        }
    }

    fun updateOrDeleteClick(person: Person){
        name.value = person.name
        email.value = person.email
        phone.value= person.phone

        isUpdateOrDelete = true
        personToUpdateOrDelete = person

        saveOrUpdate.value = "Update"
        clearAllOrDelete.value = "Delete"

    }

    private fun clearAllOrDelete(){
        if (isUpdateOrDelete){
            delete(personToUpdateOrDelete)

            name.value = null
            email.value = null
            phone.value = null

            saveOrUpdate.value = "Save"
            clearAllOrDelete.value = "Clear All"
        }else{
            clearAllOrDelete()
        }
    }



    private fun insert(person: Person) {
        viewModelScope.launch {
            personRepository.insert(person)
        }
    }

    fun clearAll(){
        viewModelScope.launch {
            personRepository.deleteAll()
            message.value = Event("All data cleared")
        }
    }

    private fun update(person: Person){
        viewModelScope.launch {
            personRepository.update(person)
            message.value = Event("Data update SuccessFully")
        }
    }
    private fun delete(person: Person){
        viewModelScope.launch {
            personRepository.delete(person)
            message.value = Event("Data delete SuccessFully")
        }
    }


    fun clearOrDeleteAll() {
        viewModelScope.launch {
            personRepository.deleteAll()
        }
    }

    val personList = personRepository.person
}