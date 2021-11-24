package com.jetpack.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.jetpack.roomdatabase.dao.SampleDao
import com.jetpack.roomdatabase.entity.SampleEntity

class SampleRepository(private val sampleDao: SampleDao) {
    val readAllData: LiveData<List<SampleEntity>> = sampleDao.getAllData()

    suspend fun addSample(item: List<SampleEntity>) {
        sampleDao.insert(item)
    }

    suspend fun viewAllSample(item: List<SampleEntity>) {
        sampleDao.getAllData()
    }

    suspend fun viewSample(item: SampleEntity) {
        sampleDao.getById(id = item.id)
    }

    suspend fun updateSample(item: SampleEntity) {
        sampleDao.update(item)
    }

    suspend fun deleteSample(item: SampleEntity) {
        sampleDao.delete(item)
    }

    suspend fun deleteAll() {
        sampleDao.deleteAllRecord()
    }
}