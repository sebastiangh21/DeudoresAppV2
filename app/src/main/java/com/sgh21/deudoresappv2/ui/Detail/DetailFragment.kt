package com.sgh21.deudoresappv2.ui.Detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.sgh21.deudoresappv2.R
import com.sgh21.deudoresappv2.data.entities.Deudor
import com.sgh21.deudoresappv2.databinding.FragmentDetailBinding
import com.sgh21.deudoresappv2.databinding.FragmentReadBinding
import com.sgh21.deudoresappv2.databinding.FragmentUpdateBinding

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var deudor: Deudor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return  root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        deudor = args.debtor
        actualizacion(deudor)
        Toast.makeText(
            requireContext(),
            deudor.name + " me debe " + deudor.amount.toString() + ", telefono: " + deudor.phone,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun actualizacion(deudor: Deudor) {
        binding.nameDetailsTextView.text = deudor.name
        binding.phoneDetailsTextView.text = deudor.phone
        binding.amountDetailsTextView.text = deudor.amount.toString()
    }

}