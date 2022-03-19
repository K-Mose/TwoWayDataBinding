package com.example.twowaydatabinding.texting

import com.example.twowaydatabinding.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.twowaydatabinding.databinding.ActivityTextBinding

class TextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextBinding
    private lateinit var viewModel: TextActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_text)
        // old version :: ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel = ViewModelProvider(this).get(TextActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        /*
        Two way data binding - 양방향 데이터 바인딩
        (https://developer.android.com/topic/libraries/data-binding/two-way?hl=ko)
        @{data} 대신 @={data}를 사용하면 된다.
        여기서는 EditText와 TextView에 userName이라는 LiveData가 연결되어서
        userName이 변경될 때 리스너로 사용하기 위해 EditText를 @={}로 사용한다.
        '=' 기호가 포함된 @={} 표기법은 속성과 관련된 데이터 변경사항을 받는 동시에
        사용자 업데이트를 수신 대기한다.
         */
    }
}