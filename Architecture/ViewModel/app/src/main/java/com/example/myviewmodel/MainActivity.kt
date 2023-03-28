package com.example.myviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        displayResult()

        binding.btnCalculate.setOnClickListener {
            val inputLength = binding.edtLength.text.toString().trim()
            val inputWidth = binding.edtWidth.text.toString().trim()
            val inputHeight = binding.edtHeight.text.toString().trim()

            when {
                inputLength.isEmpty() -> binding.edtLength.error = "Field ini tidak boleh kosong"
                inputWidth.isEmpty() -> binding.edtWidth.error = "Field ini tidak boleh kosong"
                inputHeight.isEmpty() -> binding.edtHeight.error = "Field ini tidak boleh kosong"
                else -> {
                    viewModel.calculate(inputLength, inputWidth, inputHeight)
                    displayResult()
                }
            }
        }

    }

    private fun displayResult() {
        binding.tvResult.text = viewModel.result.toString()
    }
}