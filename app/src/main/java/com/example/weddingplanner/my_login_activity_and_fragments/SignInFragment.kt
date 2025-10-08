package com.example.weddingplanner.my_login_activity_and_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weddingplanner.my_activity_2_And_fragments.MainActivity2
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.FragmentSignInBinding
import com.example.weddingplanner.databinding.FragmentSignUpBinding
import com.example.weddingplanner.my_auth_room_db.UserDatabase
import com.example.weddingplanner.myauthMvvm.UserRepository
import com.example.weddingplanner.myauthMvvm.UserViewModel
import com.example.weddingplanner.myauthMvvm.UserViewModelFactory

class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    lateinit var viewModel : UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val repository = UserRepository(dao,requireActivity())
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val pass = binding.pass.text.toString().trim()

            when {
                !Validator.isValidEmailStrict(email) -> {
                    Toast.makeText(requireContext(), "Enter a valid email", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.loginUser(email,pass)
                }
            }

        }


        viewModel.loginStatus.observe(viewLifecycleOwner){ success ->
            if(success){
                Toast.makeText(requireContext(), "Logged In", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireActivity(), MainActivity2::class.java)
                startActivity(intent)
                requireActivity().finish()
            }else{
                Toast.makeText(requireContext(), "Enter a valid email or Password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.sign.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }


}