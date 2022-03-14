package com.example.mytasks.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mytasks.R
import com.example.mytasks.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashFragment : Fragment() {

    //Recuperar os elementos da view
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    //Método que cria a view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Retornar o layout xml do fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método de quando a view já está criada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(this::checkAuth,3000)

    }


    private  fun checkAuth(){
        auth = Firebase.auth
        if (auth.currentUser == null){
            findNavController().navigate(R.id.action_splashFragment_to_authNavigation)
        }else{
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}