package com.example.mybalance

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ResetPinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pin)

        var db: SQLiteDatabase? = openOrCreateDatabase("PIN",0,null)
        db?.execSQL("CREATE TABLE IF NOT EXISTS PASSCODE(PIN varchar(10), bank varchar(30));")
//        db?.execSQL("INSERT INTO PASSCODE VALUES(3693);")
        val current = findViewById(R.id.currentkey) as EditText
        val new = findViewById(R.id.nextkey) as EditText
        val change = findViewById(R.id.change) as Button
        val result = db?.rawQuery("Select * from PASSCODE", null)
        result?.moveToFirst()
        val pass = result?.getString(0)
        val bank = result?.getString(1)
        change.setOnClickListener(
            View.OnClickListener {
                val c = current.getText().toString()
                val n = new.getText().toString()
                if(n.trim().length == 4) {
                    println(pass)
                    if (pass.equals(c)) {
                        current?.setText(null)
                        new?.setText(null)
                        db?.execSQL("UPDATE PASSCODE SET PIN = '" + n + "';")
                        Toast.makeText(
                            applicationContext, "Passcode Changed",
                            Toast.LENGTH_LONG
                        ).show()
                        val i = Intent(applicationContext, MainActivity::class.java)
                        finish()
                        startActivity(i)
                    } else {
                        current?.setText(null)
                        new?.setText(null)
                        Toast.makeText(
                            applicationContext, "Wrong Passcode", Toast.LENGTH_LONG).show();
                    }
                }
                else    Toast.makeText(this,"Please Enter a Valid 4 digit Passcode",Toast.LENGTH_SHORT).show()
            }
        )
    }
}