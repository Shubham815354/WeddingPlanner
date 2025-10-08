package com.example.weddingplanner.my_login_activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.FragmentSignUpBinding
import com.example.weddingplanner.my_auth_room_db.UserDatabase
import com.example.weddingplanner.my_auth_room_db.Users
import com.example.weddingplanner.myauthMvvm.UserRepository
import com.example.weddingplanner.myauthMvvm.UserViewModel
import com.example.weddingplanner.myauthMvvm.UserViewModelFactory

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    lateinit var viewModel : UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val repository = UserRepository(dao,requireActivity())
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUp.setOnClickListener {
            val first = binding.firstname.text.toString().trim()
            val last = binding.lastnme.text.toString().trim()
            val email = binding.emailSign.text.toString().trim()
            val pass = binding.passSign.text.toString().trim()
            val repass = binding.repassSign.text.toString().trim()

            when {
                !Validator.isValidName(first) -> {
                    Toast.makeText(requireContext(), "First name must be at least 3 characters", Toast.LENGTH_SHORT).show()
                }
                !Validator.isValidName(last) -> {
                    Toast.makeText(requireContext(), "Last name must be at least 3 characters", Toast.LENGTH_SHORT).show()
                }
                !Validator.isValidEmailStrict(email) -> {
                    Toast.makeText(requireContext(), "Enter a valid email", Toast.LENGTH_SHORT).show()
                }
                !Validator.isValidPassword(pass) -> {
                    Toast.makeText(requireContext(), "Password must contain uppercase, lowercase, digit, special char & min 8 chars", Toast.LENGTH_LONG).show()
                }
                pass != repass -> {
                    Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val user = Users(firstName = first, lastName = last, email = email, password = pass)
                    viewModel.registerUser(user)
                }
            }
        }



        // Observe registration
        viewModel.registerStatus.observe(viewLifecycleOwner) { success ->
            if(success){
                Toast.makeText(requireContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)

            } else {
                Toast.makeText(requireContext(), "Email already exists", Toast.LENGTH_SHORT).show()
            }
        }

        binding.log.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

}