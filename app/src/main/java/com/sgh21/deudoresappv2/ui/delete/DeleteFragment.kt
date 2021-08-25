package com.sgh21.deudoresappv2.ui.delete

import android.app.AlertDialog
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
import com.sgh21.deudoresappv2.databinding.FragmentDeleteBinding

class DeleteFragment : Fragment() {


    private lateinit var viewModel: DeleteViewModel
    private var _binding : FragmentDeleteBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeleteBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.deleteButton.setOnClickListener {
            deleteDeudor(binding.nameEditText.text.toString())
        }

        return root
    }

    private fun deleteDeudor(name: String) {
        val deudorDao : DeudorDao = DeudoresApp.database.DeudorDao()
        val deudor: Deudor = deudorDao.readDeudor(name)

        if(deudor != null){
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setTitle(R.string.tittle_delete)
                    setMessage("Desea eliminar a " + deudor.name + " su deuda es: " + deudor.amount.toString() + "?")
                    setPositiveButton(R.string.accept){ dialog, id ->
                        deudorDao.deleteDeudor(deudor)
                        Toast.makeText(requireContext(), "Deudor Eliminado", Toast.LENGTH_SHORT).show()
                        binding.nameEditText.setText("")
                    }
                    setNegativeButton(R.string.cancel){ dialog, id ->
                    }
                }
                builder.create()
            }
            alertDialog?.show()
            deudorDao.deleteDeudor(deudor)
            Toast.makeText(requireContext(), "Deudor Eliminado", Toast.LENGTH_SHORT).show()
            binding.nameEditText.setText("")
        }else{
            Toast.makeText(requireContext(), "No existe", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DeleteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}