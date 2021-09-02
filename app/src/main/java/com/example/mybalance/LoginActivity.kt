package com.example.mybalance

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var pass = findViewById(R.id.key) as EditText
        var enter = findViewById(R.id.enter) as Button
        var change = findViewById(R.id.btn) as Button
        change.getBackground().setAlpha(0) // 25% transparent

        var db: SQLiteDatabase? = openOrCreateDatabase("PIN",0,null)
        db?.execSQL("CREATE TABLE IF NOT EXISTS PASSCODE(PIN varchar(10),bank varchar(30));")
        db?.execSQL("INSERT INTO PASSCODE VALUES(5555,' ');")
        val result = db?.rawQuery("Select * from PASSCODE", null)
        result?.moveToFirst()
        val p = result?.getString(0)
        val b = result?.getString(1)
        enter.setOnClickListener( View.OnClickListener {
                val key = pass.getText().toString()
                if (key.equals(p)){
                    pass.setText(null)
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                }
                else{
                    pass.setText(null)
                    Toast.makeText(applicationContext, "Wrong Passcode",
                        Toast.LENGTH_LONG).show();
                }
            }
        )
        change.setOnClickListener(
            View.OnClickListener {
                val i = Intent(applicationContext,ResetPinActivity::class.java)
                startActivity(i)
            }
        )
    }

    override fun onBackPressed() {
        var intent = Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
//        finish();
//        System.exit(0);
    }

}
