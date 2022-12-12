package com.lava.yogaapp.ui.components.yoga

import android.view.View
import androidx.navigation.fragment.findNavController
import com.lava.yogaapp.R
import com.lava.yogaapp.data.local.SharedPrefs
import com.lava.yogaapp.databinding.FragmentYogaBinding
import com.lava.yogaapp.ui.base.BaseFragment
import com.lava.yogaapp.util.JWT_AUTH_TOKEN
import com.lava.yogaapp.util.UID
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class YogaFragment : BaseFragment<FragmentYogaBinding>(R.layout.fragment_yoga) {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun initViewBinding(view: View): FragmentYogaBinding = FragmentYogaBinding.bind(view)

    override fun setupViews() {
        binding.btnSignOut.setOnClickListener {
            sharedPrefs.clear()
            navigateToAuthScreen()
        }
    }

    private fun navigateToAuthScreen() {
        this.findNavController()
            .navigate(YogaFragmentDirections.actionYogaFragmentToLoginFragment())
    }

    override fun observeViewModel() {

    }
}