package com.sgh21.deudoresappv2.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sgh21.deudoresappv2.DeudoresApp
import com.sgh21.deudoresappv2.data.dao.DeudorDao
import com.sgh21.deudoresappv2.data.entities.Deudor
import com.sgh21.deudoresappv2.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private var _binding: FragmentListBinding? = null
    private lateinit var DeudoresAdapter: deudoresAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
        listViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        })

        DeudoresAdapter = deudoresAdapter(onItemClicked = { onDeudorItemClicked(it)})
        binding.deudorRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = DeudoresAdapter
            setHasFixedSize(false)
        }

        val deudorDao : DeudorDao = DeudoresApp.database.DeudorDao()
        val listDeudores : MutableList<Deudor> = deudorDao.getDeudores()
        DeudoresAdapter.appendItems(listDeudores)

        return root
    }

    private fun onDeudorItemClicked(deudor: Deudor) {
        findNavController().navigate(ListFragmentDirections.actionNavigationListToDetailFragment(debtor = deudor))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}