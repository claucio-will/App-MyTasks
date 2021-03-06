package com.example.mytasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.hellodev.task.model.Task
import com.example.mytasks.R
import com.example.mytasks.databinding.FragmentFormTaskBinding
import com.example.mytasks.helper.FirebaseHelper

class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newTask: Boolean = true
    private var statusTask: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener { validateData() }
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            statusTask = when (id) {
                R.id.rbTodo -> 0
                R.id.rbDoing -> 1
                else -> 2
            }
        }
    }

    private fun validateData() {
        val description = binding.edtDescription.text.toString().trim()
        if (description.isNotEmpty()) {
            binding.progressBar.isVisible = true

            //Criando uma nova tarefa
            if (newTask) task = Task()
            task.description = description
            task.status = statusTask
            saveTask()
        } else {
            Toast.makeText(requireContext(), "Informe a descrição da tarefa", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun saveTask() {
        FirebaseHelper.getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (newTask) { //Nova tarefa
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Tarefa salva", Toast.LENGTH_LONG).show()
                    }else{//Editando tarefa
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), "Tarefa atualizada ", Toast.LENGTH_LONG).show()

                    }

                } else {
                    Toast.makeText(requireContext(), "Erro ao salvar tarefa", Toast.LENGTH_LONG)
                        .show()

                }

            }.addOnFailureListener {
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Erro ao salvar tarefa", Toast.LENGTH_LONG).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}