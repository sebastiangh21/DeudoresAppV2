package com.sgh21.deudoresappv2.ui.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sgh21.deudoresappv2.DeudoresApp
import com.sgh21.deudoresappv2.R
import com.sgh21.deudoresappv2.data.dao.DeudorDao
import com.sgh21.deudoresappv2.data.entities.Deudor
import com.sgh21.deudoresappv2.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateFragment()
    }

    private lateinit var viewModel: UpdateViewModel
    private var _binding: FragmentUpdateBinding? = null

    private val binding get() = _binding!!
    private var isSearching = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var idDeudor = 0
        binding.updateButton.setOnClickListener {
            var deudorDao: DeudorDao = DeudoresApp.database.DeudorDao()
            var name = binding.nameEditText.text.toString()

            if(isSearching){
                var deudor: Deudor = deudorDao.readDeudor(name)
                if (deudor != null){
                    idDeudor = deudor.id
                    binding.amountEditText.setText(deudor.amount.toString())
                    binding.phoneEditText.setText(deudor.phone)
                    binding.updateButton.text = getString(R.string.tittle_update)
                    isSearching = false
                }else{
                    Toast.makeText(requireContext(), "No existe", Toast.LENGTH_SHORT).show()
                    cleanWidgets()
                }
            }else{
                val deudor: Deudor = Deudor(
                    id = idDeudor,
                    name = binding.nameEditText.text.toString(),
                    amount = binding.amountEditText.text.toString().toLong(),
                    phone = binding.phoneEditText.text.toString()
                )
                deudorDao.updateDeudor(deudor)
                binding.updateButton.text = getString(R.string.tittle_read)
                isSearching = true
                Toast.makeText(requireContext(), "Deudor actualizado", Toast.LENGTH_SHORT).show()
                cleanWidgets()
            }
        }

        return root
    }

    private fun cleanWidgets() {
        with(binding){
            nameEditText.setText("")
            phoneEditText.setText("")
            amountEditText.setText("")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}