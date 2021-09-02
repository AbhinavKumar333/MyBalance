package com.example.mybalance

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.MenuPopupWindow
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_bank.view.*
import java.text.SimpleDateFormat
import java.util.ArrayList

class BankActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)

        val btn = findViewById<Button>(R.id.next)
        var db: SQLiteDatabase? = openOrCreateDatabase("PIN",0,null)
        db?.execSQL("CREATE TABLE IF NOT EXISTS PASSCODE(PIN varchar(10), bank varchar(30));")

        var b = ""
        val dropdown = findViewById<Spinner>(R.id.spinner)
        val items = arrayOf("Choose Bank","SBI\n","UCO BANK\n","Syndicate Bank\n","Canara Bank\n","Punjab National Bank\n","AXIS BANK\n","ICICI BANK\n","HDFC BANK\n","PAYTM\n","AIRTEL PAYMENTS BANK\n","Phonepe\n","Bank of Baroda\n","BANK of India\n","Allahabad Bank","IDBI Bank\n","Karnataka Bank")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        dropdown.setAdapter(adapter)
        dropdown.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    0-> b = "null"
                    1-> b = "SBI"
                    2-> b = "UCO"
                    3-> b = "SYN"
                    4-> b = "CAN"
                    5-> b = "PNB"
                    6-> b = "AXIS"
                    7-> b = "ICICI"
                    8-> b = "HDFC"
                    9-> b = "PAYTM"
                    10-> b = "AIRBNK"
                    11-> b = "PHONPE"
                    12-> b = "BOB"
                    13-> b = "BOI"
                    14-> b = "ALLAB"
                    15-> b= "IDBI"
                    16-> b = "KAR"
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })
        btn.setOnClickListener{
            if(b.contains("null"))  Toast.makeText(applicationContext,"Please choose a Bank Account",Toast.LENGTH_SHORT).show()
            else {
                db?.execSQL("UPDATE PASSCODE SET bank = '" + b + "';")
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
            }
        }
    }
}