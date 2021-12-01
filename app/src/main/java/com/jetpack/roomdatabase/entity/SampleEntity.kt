package com.jetpack.roomdatabase.entity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Entity(tableName = "SampleData")
data class SampleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "desc")
    var desc: String,

    @ColumnInfo(name = "imgUrl")
    var imgUrl: String,

    @ColumnInfo(name = "date")
    var createdDate: String
)

/*
fun truncateTable(context: Context, openHelper: SupportSQLiteOpenHelper, tableName: String?) {
    val database = SQLiteDatabase.openOrCreateDatabase(
        context.getDatabasePath(openHelper.sample_database),
        null
    )
    if (database != null) {
        database.execSQL(String.format("DELETE FROM %s;", SampleEntity))
        database.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = ?;", arrayOf(tableName))
    }
}
*/
