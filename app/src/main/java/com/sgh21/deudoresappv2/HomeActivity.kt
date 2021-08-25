package com.sgh21.deudoresappv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.sgh21.deudoresappv2.VerificationUtils.EMPTY
import com.sgh21.deudoresappv2.VerificationUtils.minimumLength
import com.sgh21.deudoresappv2.VerificationUtils.validateEmail
import com.sgh21.deudoresappv2.data.dao.UserDao
import com.sgh21.deudoresappv2.data.entities.User
import com.sgh21.deudoresappv2.databinding.ActivityHomeBinding
import com.sgh21.deudoresappv2.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var password: String = EMPTY
    private var check = arrayOf(false,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailLoginEditText.text.toString()
            password = binding.passwordLoginEditText.text.toString()
            val userDao: UserDao = DeudoresApp.database2.UserDao()
            val user: User = userDao.readUser(email)
            if(user != null){
                if (user.email == email && user.password == password) {
                    goToMainActivity()
                }else{
                    Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, getString(R.string.unregistered_user), Toast.LENGTH_SHORT).show()
            }
        }

        binding.emailLoginEditText.doAfterTextChanged {

            if(!validateEmail(binding.emailLoginEditText.text.toString())){
                check[0] = false
                binding.emailLoginTextInputLayout.error = getString(R.string.email_error)
            }else{
                binding.emailLoginTextInputLayout.error = null
                check[0] = true
            }
            enableButton()
        }

        binding.passwordLoginEditText.doAfterTextChanged {
            password = binding.passwordLoginEditText.text.toString()
            if (!minimumLength(password)){
                binding.passwordLoginTextInputLayout.error = getString(R.string.password_error)
                check[1] = false
            }else{
                binding.passwordLoginTextInputLayout.error = null
                check[1] = true
            }
            enableButton()
        }

        binding.signUpTextView.setOnClickListener {
            val  intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun enableButton() {
        binding.loginButton.isEnabled = check[0] && check[1]
    }
}