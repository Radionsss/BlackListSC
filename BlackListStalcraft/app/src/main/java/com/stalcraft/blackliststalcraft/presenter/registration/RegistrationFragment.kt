package com.stalcraft.blackliststalcraft.presenter.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.stalcraft.blackliststalcraft.R
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.databinding.FragmentRegistrationBinding
import com.stalcraft.blackliststalcraft.presenter.utils.ShowDialogHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null
    private val viewModel: RegistrationViewModel by viewModels()


    private val RC_SIGN_IN = 9001
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGoogleSignInClient()
        binding?.apply {
            btnSignInGoogle.setOnClickListener {
                if (edNickPlayer.text.toString().trim()==""){
                    Toast.makeText(requireContext(), getString(R.string.error_empty), Toast.LENGTH_SHORT).show()
                }else{
                    signInWithGoogle()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.loginResult.collect { result ->
                when (result) {
                    is MyResult.Loading -> {
                        binding?.dimView?.visibility = View.VISIBLE
                        ShowDialogHelper.showDialogLoad(requireContext())
                    }

                    is MyResult.Success -> {
                        binding?.dimView?.visibility = View.GONE
                        ShowDialogHelper.dismissDialogLoad()
                        val action = RegistrationFragmentDirections.actionRegistrationFragmentToMainScreenFragment()
                        binding?.root?.let { it1 -> Navigation.findNavController(it1).navigate(action) }

                    }

                    is MyResult.Failure -> {
                        ShowDialogHelper.showDialogError(requireContext(),getString(R.string.unknown_error_has_occurred))
                    }

                    null -> {}
                }
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed
                Log.e("onActivityResult", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    viewModel.createUser(binding?.edNickPlayer?.text.toString())
                } else {
                    Log.w("firebaseAuthWithGoogle", "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun setupGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

}