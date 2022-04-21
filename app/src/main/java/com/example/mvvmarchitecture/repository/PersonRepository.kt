package com.example.mvvmarchitecture.repository

import com.example.mvvmarchitecture.model.Person
import com.example.mvvmarchitecture.roomdb.PersonDao

class PersonRepository(private val dao: PersonDao) {

    suspend fun insert(person: Person) {
        dao.insertPerson(person)
    }

    suspend fun update(person: Person) {
        dao.updatePerson(person)
    }

    suspend fun delete(person: Person) {
        dao.deletePerson(person)
    }

    suspend fun deleteAll() {
        dao.deleteAllPerson()
    }

    val person =dao.getUser()
}