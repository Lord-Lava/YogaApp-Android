package com.lava.yogaapp.ui.components

import com.lava.yogaapp.databinding.ActivityMainBinding
import com.lava.yogaapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {

    }

    override fun observeViewModel() {

    }
}