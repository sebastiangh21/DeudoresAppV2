package com.sgh21.deudoresappv2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.sgh21.deudoresappv2.VerificationUtils.EMPTY
import com.sgh21.deudoresappv2.VerificationUtils.minimumLength
import com.sgh21.deudoresappv2.VerificationUtils.validateEmail
import com.sgh21.deudoresappv2.data.dao.UserDao
import com.sgh21.deudoresappv2.data.entities.User
import com.sgh21.deudoresappv2.databinding.ActivityRegisterBinding
import java.sql.Types

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var password: String = EMPTY
    private var repPassword: String = EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            saveButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val lastName = lastNameEditText.text.toString()
                val email = emailEditText.text.toString()

                if (name.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, getString(R.string.name_error), Toast.LENGTH_SHORT).show()
                } else if (lastName.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, getString(R.string.last_name_error), Toast.LENGTH_SHORT).show()
                } else if (!validateEmail(email)) {
                    Toast.makeText(this@RegisterActivity, getString(R.string.email_error), Toast.LENGTH_SHORT).show()
                } else {
                    if (password == repPassword && password != EMPTY) {
                        register(name, lastName, email, password)
                    } else {
                        Toast.makeText(this@RegisterActivity, getString(R.string.password_errors), Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        binding.emailEditText.doAfterTextChanged {
            binding.emailTextInputLayout.error = if (!validateEmail(binding.emailEditText.text.toString())) getString(R.string.email_error)  else null
        }

        binding.passwordEditText.doAfterTextChanged {
            password = binding.passwordEditText.text.toString()
            binding.passwordTextInputLayout.error = if (!minimumLength(password)) getString(R.string.password_error) else null
        }

        binding.repPasswordEditText.doAfterTextChanged {
            repPassword = binding.repPasswordEditText.text.toString()
            binding.repPasswordTextInputLayout.error = if (password != repPassword) getString(R.string.coincidence_error) else null
        }
    }

    private fun register(name: String, lastName: String, email: String, password: String) {
        val userDao: UserDao = DeudoresApp.database2.UserDao()
        val user: User = userDao.readUser(email)
        if(user != null){
            Toast.makeText(this, R.string.registered_used, Toast.LENGTH_SHORT).show()
        }else{
            val newuser = User(id = Types.NULL, name = name, lastName = lastName, email = email, password = password)
            userDao.createUser(newuser)
            goToLoginActivity()
        }
    }

    private fun goToLoginActivity() {
        val  intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}