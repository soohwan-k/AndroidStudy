package org.tech.town.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val firstNumberPicker : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.firstNumberPicker)
            .apply{
                minValue = 0
                maxValue = 9
            }
    }

    private val secondNumberPicker : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.secondNumberPicker)
            .apply{
                minValue = 0
                maxValue = 9
            }
    }

    private val thirdNumberPicker : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.thirdNumberPicker)
            .apply{
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy{
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy{
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //NumberPicker 호출 lazy하게 init
        firstNumberPicker
        secondNumberPicker
        thirdNumberPicker

        openButton.setOnClickListener{

            if (changePasswordMode){
                Toast.makeText(this,"비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)){
                intent = Intent(this, DiaryActivity::class.java)
                startActivity(intent)
            }else{
                showErrorAlertDialog()
            }

        }

        changePasswordButton.setOnClickListener{
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"
            if (changePasswordMode){
                //번호 저장하는 기능
                passwordPreferences.edit(true){
                    putString("password", passwordFromUser)
                }
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

            }else{
                //changePasswordMode가 활성화 :: 비밀번호가 맞는지를 체크
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)){
                    changePasswordMode = true
                    Toast.makeText(this,"변경할 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    changePasswordButton.setBackgroundColor(Color.RED)

                }else{
                    showErrorAlertDialog()
                }

            }
        }

    }
    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다.")
            //확인 후 Dialog만 닫히고 다른 동작 안해서 람다식 처리 안함
            .setPositiveButton("확인"){ _, _ -> }
            .create()
            .show()
    }
}