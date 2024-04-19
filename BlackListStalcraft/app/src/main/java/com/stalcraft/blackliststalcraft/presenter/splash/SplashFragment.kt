package com.stalcraft.blackliststalcraft.presenter.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.stalcraft.blackliststalcraft.R
import com.stalcraft.blackliststalcraft.databinding.FragmentRegistrationBinding
import com.stalcraft.blackliststalcraft.databinding.FragmentSplashBinding
import com.stalcraft.blackliststalcraft.presenter.registration.RegistrationFragmentDirections
import com.stalcraft.blackliststalcraft.presenter.registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            if (viewModel.isUserAuthenticated()){
                val action = SplashFragmentDirections.actionSplashFragmentToMainScreenFragment()
                binding?.root?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
            }else{
                val action = SplashFragmentDirections.actionSplashFragmentToRegistrationFragment()
                binding?.root?.let { it1 -> Navigation.findNavController(it1).navigate(action) }

            }
        }
    }
}