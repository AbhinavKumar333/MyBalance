package com.example.mybalance

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val pass = findViewById(R.id.key) as EditText
        val btn = findViewById<Button>(R.id.enter)

        var db: SQLiteDatabase? = openOrCreateDatabase("PIN",0,null)
        db?.execSQL("CREATE TABLE IF NOT EXISTS PASSCODE(PIN varchar(10),bank varchar(30));")
        db?.execSQL("INSERT INTO PASSCODE VALUES(' ',' ');")
        val result = db?.rawQuery("Select * from PASSCODE", null)
        result?.moveToFirst()
        val bank = result?.getString(1)

        if(!bank.equals(" ")){
            val i = Intent(this,LoginActivity::class.java)
            startActivity(i)
        }
        btn.setOnClickListener(View.OnClickListener {
            val p = pass.getText().toString()
            if(p.trim().length == 4) {
                db?.execSQL("UPDATE PASSCODE SET PIN = '" + p + "';")
                val i = Intent(this, BankActivity::class.java)
                startActivity(i)
            }
            else    Toast.makeText(this,"Please Enter a Valid 4 digit Passcode",Toast.LENGTH_SHORT).show()
        })



    }
}
