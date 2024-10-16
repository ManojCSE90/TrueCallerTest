package com.example.truecallertest.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.truecallertest.databinding.ActivityMainBinding
import com.example.truecallertest.models.NetworkResult
import com.example.truecallertest.utils.Util
import com.example.truecallertest.viewmodel.TrueCallerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class TrueCallerMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val trueCallerViewModel by viewModels<TrueCallerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tv2.isSelected = true
        binding.tv3.isSelected = true

        registerObserver()

        binding.btnLoadData.setOnClickListener {

            trueCallerViewModel.getTrueCallerWebsiteData()
        }
    }

    private fun registerObserver() {

        trueCallerViewModel.trueCallerWebsiteData.observe(this) {

            updateUI(it)

        }
    }

    private fun updateUI(networkResult: NetworkResult<String>) {

        when (networkResult) {
            is NetworkResult.Failure -> {
                binding.btnLoadData.visibility = View.VISIBLE
                binding.loader.visibility = View.GONE

                Toast.makeText(this, networkResult.errorMsg, Toast.LENGTH_SHORT).show()
            }

            is NetworkResult.Loading -> {
                showLoader(true)
            }

            is NetworkResult.Success -> {

                showLoader(false)

                CoroutineScope(Dispatchers.IO).launch {

                    //all these 3 jobs will execute in parallel
                    //this job will find first 15th character of the string and display it
                    launch {

                        networkResult.data?.let {
                            if (it.length >= 15) {
                                withContext(Dispatchers.Main) {
                                    binding.tv1.text = "${it[14]}"

                                }
                            }
                        }
                    }

                    //this job will find every 15th character of the string and display it
                    launch {
                        networkResult.data?.let {
                            if (it.length >= 15) {
                                val charactersAtMultiplesOf15 =
                                    Util.findCharactersAtMultiplesOf15(it)

                                withContext(Dispatchers.Main) {
                                    binding.tv2.text = "$charactersAtMultiplesOf15"
                                }
                            }
                        }
                    }

                    //this job will count the number of words in the string and display it
                    launch {
                        networkResult.data?.let {
                            val wordCountMap = Util.countWords(it)
                            withContext(Dispatchers.Main) {
                                binding.tv3.text = "$wordCountMap"
                            }
                        }
                    }

                }
            }
        }
    }

    private fun showLoader(show: Boolean) {

        if (show) {
            binding.loader.visibility = View.VISIBLE
            binding.level1.visibility = View.GONE
            binding.level2.visibility = View.GONE
            binding.level3.visibility = View.GONE
            binding.tv1.visibility = View.GONE
            binding.tv2.visibility = View.GONE
            binding.tv3.visibility = View.GONE
            binding.btnLoadData.visibility = View.GONE

        } else {

            binding.loader.visibility = View.GONE
            binding.level1.visibility = View.VISIBLE
            binding.level2.visibility = View.VISIBLE
            binding.level3.visibility = View.VISIBLE
            binding.tv1.visibility = View.VISIBLE
            binding.tv2.visibility = View.VISIBLE
            binding.tv3.visibility = View.VISIBLE
            binding.btnLoadData.visibility = View.VISIBLE
        }
    }


}