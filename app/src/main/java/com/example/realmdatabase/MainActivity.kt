package com.example.realmdatabase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var realm: Realm? = null
    private val dataModel = DataModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        btn_add.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        btn_update.setOnClickListener(this)
        btn_delete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> addRecord()
            R.id.btn_save -> saveRecord()
            R.id.btn_update -> updateRecord()
            R.id.btn_delete -> deleteRecord()
        }
    }

    private fun deleteRecord() {
        try {
            val id: Long = ed_id_text.text.toString().toLong()
            val dataModel = realm!!.where(DataModel::class.java).equalTo("ID", id).findFirst()
            realm!!.executeTransaction {
                dataModel?.deleteFromRealm()
            }
            Log.d("Status", "Data is deleted Suceessfully")
            clearFields()
        } catch (e: Exception) {
            Log.d("Status", "Something went Wrong")
        }
    }

    private fun updateRecord() {
        try {
            val id: Long = ed_id_text.text.toString().toLong()
            val dataModel = realm!!.where(DataModel::class.java).equalTo("ID", id).findFirst()
            ed_name_text.setText(dataModel?.name)
            ed_email_text.setText(dataModel?.email)
            Log.d("Status", "Data is Updated")
        } catch (e: Exception) {
            Log.d("Status", "Something Went Wrong")
        }
    }


    private fun saveRecord() {
        try {
            val dataModel: List<DataModel> = realm!!.where(DataModel::class.java).findAll()
            var response = ""
            dataModel.forEach {
                response += "ID : ${it.ID}, Name : ${it.name} \n , Email : ${it.email} \n "
            }
            txt_db_response.text = response
            Log.i("Status", "Data Fetched !!!")
        } catch (e: Exception) {
            Log.i("Status", "Something Went Wrong")
        }
    }

    private fun addRecord() {
        try {
            dataModel.ID = ed_id_text.text.toString().toInt()
            dataModel.name = ed_name_text.text.toString()
            dataModel.email = ed_email_text.text.toString()
            realm!!.executeTransaction {
                it.copyToRealm(dataModel)
            }
            clearFields()
            Log.d("Status", "Data Inserted Successfully")
            showToast("Data Inserted Successfully")
        } catch (e: Exception) {
            Log.d("Status", "Something went Wrong")
        }
    }

    private fun Context.showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun clearFields() {
        ed_id_text.setText("")
        ed_name_text.setText("")
        ed_email_text.setText("")
    }

}

