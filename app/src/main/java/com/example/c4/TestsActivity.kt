package com.example.c4

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

const val CHANGE_STATUS_FOR_DONE = 1

class TestsActivity: Activity() {

    var db : DB? = null

    var testsList:List<Test> ?=null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==CHANGE_STATUS_FOR_DONE && resultCode== RESULT_OK && data!=null) {

            val test = data.extras?.get("TEST") as Test
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    db?.localDao()?.updateTestWhere(test.id!!,test.status)
                    getSavedData()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tests)


        db = Room.databaseBuilder(applicationContext,
            DB::class.java,"Tests").build()

        //saveData(testsList)

        getSavedData()

    }

    override fun onPause() {
        super.onPause()
        saveData(testsList!!)

    }

    private fun saveData(list: List<Test>) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                db?.localDao()?.deleteTest()
                if(list?.isNotEmpty()){
                    db?.localDao()?.insert(
                        list!!.map {
                            Test(
                                it.id,
                                it.status,
                                it.theme,
                                it.question,
                                it.answer,
                                it.type,
                                it.number,
                                it.inputSystem,
                                it.outputSystem
                            )
                        }
                    )
                }
            }
        }
    }

    private fun getSavedData() {
        CoroutineScope(Dispatchers.Main).launch {
            var listDB: List<Test>? = null
            withContext(Dispatchers.IO) {
                listDB = db?.localDao()?.getLastTest()
            /* if (db?.StudentDao()?.getAll() != null)
            recyclerViewStudents.adapter = StudentAdapter()db?.StudentDao()?.getAll()*/
            }
            if (listDB!=null && listDB!!.isNotEmpty())
                testsList = listDB!!
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerTests)
            recyclerView.adapter = RouteAdapter(listDB!!.map{
                Test(
                    it.id,
                    it.status,
                    it.theme,
                    it.question,
                    it.answer,
                    it.type,
                    it.number,
                    it.inputSystem,
                    it.outputSystem
                )
            },this@TestsActivity)
            recyclerView.layoutManager = LinearLayoutManager(this@TestsActivity)

        }
    }

}


class RouteAdapter(private val dataset:List<Test>,val activity: TestsActivity): RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    class RouteViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RouteAdapter.RouteViewHolder { // создать модель view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_title,parent,false)
        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RouteAdapter.RouteViewHolder,
        position: Int
    ) { // заполняем модель
        val numTestControl = holder.view.findViewById<TextView>(R.id.numTest)
        val themeTestControl = holder.view.findViewById<TextView>(R.id.theme)

        val color = holder.view.findViewById<CardView>(R.id.cardView)

        if (dataset[position].status=="DONE") color.setCardBackgroundColor(Color.GREEN)

        numTestControl.text = " Номер: " + dataset[position].id.toString()
        themeTestControl.text = " Тема: " + dataset[position].theme.toString()

        holder.view.setOnClickListener {

            when (dataset[position].type) {
                "testWriteAnswer" -> {
                    val intent = Intent(activity, TestActivity::class.java)
                    intent.putExtra("TEST", dataset[position] as Serializable)
                    activity.startActivityForResult(intent, CHANGE_STATUS_FOR_DONE)
                }
                "testSelectTheoreticalAnswer","testSelectCalculatingAnswer" -> {
                    val intent = Intent(activity, SelectTestActivity::class.java)
                    intent.putExtra("TEST", dataset[position] as Serializable)
                    activity.startActivityForResult(intent, CHANGE_STATUS_FOR_DONE)
                }
                "theory" -> {
                    val intent = Intent(activity, TheoryActivity::class.java)
                    intent.putExtra("TEST", dataset[position] as Serializable)
                    activity.startActivityForResult(intent, CHANGE_STATUS_FOR_DONE)
                }
            }
        }
    }

    override fun getItemCount(): Int = dataset.size

}