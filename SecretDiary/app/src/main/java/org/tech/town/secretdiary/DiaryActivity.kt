package org.tech.town.secretdiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    //handler -> main thread 와 연결할때 많이 사용
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))


        //thread 에 넣게 될 runnable interface 구현
        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit{
                putString("detail", diaryEditText.text.toString())
            }
        }


        //text 가 변경 될때마다 호출되는 람다
        diaryEditText.addTextChangedListener {
            //이전에 있는 runnable 있다면 지움
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }

    }
}