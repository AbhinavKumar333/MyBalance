package com.example.abhinav.sample

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var lViewSMS = findViewById(R.id.list) as ListView
        var final_bal = findViewById(R.id.textView) as TextView
        var bank = findViewById(R.id.editText2) as EditText
        var ser = findViewById(R.id.button) as Button
        val uriSms = Uri.parse("content://sms/inbox")
        val cursor = getContentResolver().query(uriSms, arrayOf("_id", "address", "date", "body"), null, null, null)

        fun find_bal(result: String): String{
            cursor.moveToFirst()
            var avl_bal = String()
            while (cursor.moveToNext()) {
                val body = cursor.getString(3)
                val address = cursor.getString(1)
                println("====== Mobile number = " + address + "\n" + body)
                if (address.contains(result) && (body.contains("debited") || body.contains("credited"))){
                    avl_bal = body.substringAfter("Bal:").substringBefore(". Helpline")
                    return avl_bal
                }
            }
            return "Not Found"
        }

        fun fetchInbox(result: String): ArrayList<String> {
            cursor.moveToFirst()
            val sms = ArrayList<String>()
            while (cursor.moveToNext()) {
                val date = cursor.getLong(2)
                val body = cursor.getString(3)
                val address = cursor.getString(1)
                val formattedDate = SimpleDateFormat("MM/dd/yyyy").format(date)
//                println("====== Mobile number = " + address)
//                println("===== SMS Text = " + body)
                if (address.contains(result)) {
                    if (body.contains("debited")) {
                        var deb = body.substringAfter("INR ").substringBefore(" d")
                        var bal = body.substringAfter("Bal:").substringBefore(". Helpline")
                        sms.add(formattedDate + "\ndebited :-  " + deb + "       Avl Bal :- " + bal)
                    } else if (body.contains("credited")) {
                        val cre = body.substringAfter("INR ").substringBefore(" has")
                        var bal = body.substringAfter("Bal:").substringBefore(". Helpline")
                        sms.add(formattedDate + "\ncredited :- " + cre + "       Avl Bal :- " + bal)
                    }
                }
            }
            return sms
        }
        ser.setOnClickListener(
                View.OnClickListener {
                    var result = bank.getText().toString().toUpperCase()
                    final_bal.setText(find_bal(result))
                    lViewSMS.setAdapter(null)
                    if (fetchInbox(result) != null) {
                        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fetchInbox(result))
                        lViewSMS.setAdapter(adapter)
                    }
                    else{
                        lViewSMS.setAdapter(null)
                    }
                })
    }
}