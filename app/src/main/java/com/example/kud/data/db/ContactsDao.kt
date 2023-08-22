package com.example.kud.data.db

import androidx.room.*

@Dao
interface  ContactsDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveContact(contactsEntity: ContactsEntity)
//
//    @Update
//    suspend fun updateContact(contactsEntity: ContactsEntity)
//
//    @Delete
//    suspend fun deleteContact(contactsEntity: ContactsEntity)
//
//    @Query("SELECT * FROM $CONTACTS_TABLE WHERE id ==:id")
//    fun getContact(id: Int): Flow<ContactsEntity>
//
//    @Query("SELECT * FROM $CONTACTS_TABLE")
//    fun getAllContacts(): Flow<MutableList<ContactsEntity>>
//
//    @Query("DELETE FROM $CONTACTS_TABLE")
//    fun deleteAllContacts()
//
//    @Query("SELECT * FROM $CONTACTS_TABLE ORDER BY name ASC")
//    fun sortedASC(): Flow<MutableList<ContactsEntity>>
//
//    @Query("SELECT * FROM $CONTACTS_TABLE ORDER BY name DESC")
//    fun sortedDESC(): Flow<MutableList<ContactsEntity>>
//
//    @Query("SELECT * FROM $CONTACTS_TABLE WHERE name LIKE '%' || :name || '%' ")
//    fun searchContact(name: String): Flow<MutableList<ContactsEntity>>
}