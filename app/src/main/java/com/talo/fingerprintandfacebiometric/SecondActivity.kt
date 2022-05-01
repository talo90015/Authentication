package com.talo.fingerprintandfacebiometric

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.talo.fingerprintandfacebiometric.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}