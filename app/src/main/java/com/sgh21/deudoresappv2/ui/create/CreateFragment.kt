package com.sgh21.deudoresappv2.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sgh21.deudoresappv2.DeudoresApp
import com.sgh21.deudoresappv2.data.dao.DeudorDao
import com.sgh21.deudoresappv2.data.entities.Deudor
import com.sgh21.deudoresappv2.databinding.FragmentCreateBinding
import java.sql.Types.NULL

class CreateFragment : Fragment() {

    private lateinit var dashboardViewModel: CreateViewModel
    private var _binding: FragmentCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(CreateViewModel::class.java)

        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        })

        with(binding){
            createButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val phone = phoneEditText.text.toString()
                val amount = amountEditText.text.toString().toLong()

                create(name, phone, amount)
            }
        }
        return root
    }

    private fun create(name: String, phone: String, amount: Long) {
        val deudor = Deudor(id = NULL, name = name, phone = phone, amount = amount)
        val deudorDao : DeudorDao = DeudoresApp.database.DeudorDao()
        deudorDao.createDeudor(deudor)
        cleanViews()
    }

    fun cleanViews() {
        binding.nameEditText.setText("")
        binding.phoneEditText.setText("")
        binding.amountEditText.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}