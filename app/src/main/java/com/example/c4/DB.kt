package com.example.c4

import androidx.room.*
import androidx.room.Database
import java.io.Serializable
import java.text.FieldPosition

@Entity
data class Test(
    @PrimaryKey(autoGenerate = true) var id : Int? = null,
    @ColumnInfo(name = "status") var status : String,
    @ColumnInfo(name = "theme") val theme : String,
    @ColumnInfo(name = "question") val question : String,
    @ColumnInfo(name = "answer") val answer : String,
    @ColumnInfo(name = "type") val type : String,
    @ColumnInfo(name = "number") val number : String,
    @ColumnInfo(name = "inputSystem") val inputSystem : Int,
    @ColumnInfo(name = "outputSystem") val outputSystem : Int
):Serializable {
}

@Dao
interface MultiplierDao {
    @Insert
    fun insert(list : List<Test>)

    @Query(
        "SELECT * FROM Test"
    )
    fun getLastTest() : List<Test>

    @Query(
        "DELETE FROM Test"
    )
    fun deleteTest()


    @Query(
        "UPDATE Test SET status=(:newStatus) WHERE id=(:id)"
    )
    fun updateTestWhere(id: Int,newStatus: String)
}

@Database(entities = [Test::class], version = 3)
abstract class DB : RoomDatabase() {
    abstract fun localDao() : MultiplierDao
}