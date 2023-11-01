package com.example.taskcdp.ui.login

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.taskcdp.databinding.ActivityLoginBinding

import com.example.taskcdp.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        val savedLoginDetails = authViewModel.getLoginDetails()


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }

        binding.rememberMeCheckBox?.setOnCheckedChangeListener { _, isChecked ->
            authViewModel.onRememberMeChecked(isChecked)
        }

        savedLoginDetails?.let { (savedUsername, savedPassword) ->
            binding.username.setText(savedUsername)
            binding.password.setText(savedPassword)
        }
        binding.login.isEnabled = true

        binding.login.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()


            authViewModel.rememberMeChecked.observe(this) { isChecked ->
                if (isChecked) {
                    authViewModel.saveLoginDetails(username, password)
                } else {
                    authViewModel.clearLoginDetails()
                }
            }

            binding.loading.visibility = View.VISIBLE

            // Call your authentication API here
            // authenticateUser(username, password)
        }


    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}