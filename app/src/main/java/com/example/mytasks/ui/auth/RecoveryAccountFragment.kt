package com.example.mytasks.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.mytasks.R
import com.example.mytasks.databinding.FragmentLoginBinding
import com.example.mytasks.databinding.FragmentRecoveryAccountBinding
import com.example.mytasks.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoveryAccountFragment : Fragment() {

    private var _binding: FragmentRecoveryAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoveryAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Inicia o serviço com o firebase
        auth = Firebase.auth
        initClick()
    }

    private fun initClick() {
        binding.btnRecovery.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            binding.progressIndicatorRecovery.isVisible = true
            recoveryAccountUser(email)
        } else {
            Toast.makeText(requireContext(), "Campo Senha Obrigotório", Toast.LENGTH_SHORT).show()

        }
    }

    private fun recoveryAccountUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Enviamos um link para resetar sua senha no seu E-mail",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""), Toast.LENGTH_LONG
                    ).show()

                }
                binding.progressIndicatorRecovery.isVisible = false
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}