package com.example.mybalance

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this,
            arrayOf<String>(Manifest.permission.READ_SMS),
            1)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        var db: SQLiteDatabase? = openOrCreateDatabase("PIN",0,null)
        db?.execSQL("CREATE TABLE IF NOT EXISTS PASSCODE(PIN varchar(10), bank varchar(30));")
        val result = db?.rawQuery("Select * from PASSCODE", null)
        result?.moveToFirst()
        val bank = result?.getString(1).toString()
        if (grantResults.size > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            var n = ""
            if(bank.contains("SBI")){   n = "State Bank of India"   }
            else if(bank.contains("UCO")){  n = "UCO BANK"  }
            else if(bank.contains("SYN")){  n = "Syndicate BANK"  }
            else if(bank.contains("CAN")){  n = "Canara BANK"  }
            else if(bank.contains("PNB")){  n = "Punjab National BANK"  }
            else if(bank.contains("AXIS")){ n = "AXIS BANK"  }
            else if(bank.contains("ICICI")){ n = "ICICI BANK"  }
            else if(bank.contains("HDFC")){  n = "HDFC BANK"  }
            else if(bank.contains("PAYTM")){ n = "PAYTM"  }
            else if(bank.contains("AIRBNK")){ n = "Airtel Payments BANK"  }
            else if(bank.contains("PHONPE")){ n = "PhonePe"  }
            else if(bank.contains("BOB")){ n = "Bank of Baroda"  }
            else if(bank.contains("BOI")){ n = "Bank of India"  }
            else if(bank.contains("ALLAB")){ n = "Allahabad Bank"  }
            else if(bank.contains("IDBI")){ n = "IDBI Bank"  }
            else if(bank.contains("KAR")){ n = "Karnataka Bank"  }

            textView2.setText(n)


            var lViewSMS = findViewById(R.id.list) as ListView
            val uriSms = Uri.parse("content://sms/inbox")

            fun fetchInbox(result: String): ArrayList<String> {
                val cursor = getContentResolver().query(uriSms, arrayOf("_id", "address", "date","body"), null, null, null)
                cursor.moveToFirst()
                val sms = ArrayList<String>()
                while (cursor.moveToNext()) {
                    val date = cursor.getLong(2)
                    val body = cursor.getString(3)
                    val address = cursor.getString(1).toUpperCase()
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy").format(date)

                    if (address.contains(bank,false)) {
                            sms.add("\n\t" + formattedDate + "\nDescription :- " + body.toString())
                    }
                }
                return sms
            }

            lViewSMS.setAdapter(null)
            val r = fetchInbox(bank)
            if (r.toString().trim().length != 0) {
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, r.toArray())
//                Toast.makeText(this,(adapter==null).toString(),Toast.LENGTH_SHORT).show()
                lViewSMS.setAdapter(adapter)
            }
//            else {
//                Toast.makeText(this,"Account Not Found",Toast.LENGTH_SHORT).show()
//                lViewSMS.setAdapter(null)
//            }

        }
        else{
            Toast.makeText(this,"Please Grant SMS Permission and Service SMS Permission",Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
//        var intent = Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//        startActivity(intent);
//        finish();
//        System.exit(0);
            finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.mymenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.bank -> {
                val intent = Intent(this,BankActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.cpass -> {
                val intent = Intent(this,ResetPinActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.about -> {
                Toast.makeText(this,"Developed by Abhinav Kumar",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.exit -> {
                finishAffinity()
            }
        }
        return true
    }
}


