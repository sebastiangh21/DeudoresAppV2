package com.sgh21.deudoresappv2.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sgh21.deudoresappv2.DeudoresApp
import com.sgh21.deudoresappv2.R
import com.sgh21.deudoresappv2.data.dao.DeudorDao
import com.sgh21.deudoresappv2.data.entities.Deudor
import com.sgh21.deudoresappv2.databinding.FragmentReadBinding

class ReadFragment : Fragment() {

    private lateinit var notificationsViewModel: ReadViewModel
    private var _binding: FragmentReadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(ReadViewModel::class.java)

        _binding = FragmentReadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
          //  textView.text = it
        })

        binding.readButton.setOnClickListener {
            readDeudores(binding.nameEditText.text.toString())
        }

        return root
    }

    private fun readDeudores(name: String) {
        val deudorDao: DeudorDao = DeudoresApp.database.DeudorDao()
        val deudor: Deudor = deudorDao.readDeudor(name)

        if(deudor != null){
            with(binding){
                phoneTextView.text = getString(R.string.phone_value, deudor.phone)
                amountTextView.text = getString(R.string.amount_value, deudor.amount.toString())
            }
        }else{
            Toast.makeText(requireContext(), R.string.nonexistent_user, Toast.LENGTH_SHORT).show()
            with(binding){
                phoneTextView.text = ""
                amountTextView.text = ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}