package com.example.worldresort.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.worldresort.R
import com.example.worldresort.database.DataBaseManager
import com.example.worldresort.databinding.FragmentWorldBinding
import com.example.worldresort.model.Resort
import com.example.worldresort.preferences.PreferencesManager
import com.example.worldresort.ui.adapters.adapter.ResortRecyclerViewAdapter
import com.example.worldresort.ui.dialog.Dialogs
import com.example.worldresort.ui.interfaces.OnItemClickListener
import com.example.worldresort.validation.Validator

class WorldFragment : Fragment(), OnItemClickListener {
    lateinit var validator: Validator
    lateinit var preferences: PreferencesManager
    lateinit var binding: FragmentWorldBinding
    lateinit var adapter: ResortRecyclerViewAdapter
    lateinit var dataBase: DataBaseManager
    lateinit var dialogs: Dialogs
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorldBinding.inflate(inflater, container, false)
        adapter = ResortRecyclerViewAdapter(this)
        dataBase = DataBaseManager(requireContext())
        preferences = PreferencesManager(requireContext())
        validator = Validator(requireContext())
        binding.recyclerViewResort.adapter = adapter
        updateListResorts(dataBase.getResorts())
        dialogs = Dialogs(requireContext(), validator, preferences, dataBase)
        binding.groupChips.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chipResortAfrika -> filterWorld("Африка")
                R.id.chipResortEvropa -> filterWorld("Европа")
                R.id.chipResortAsia -> filterWorld("Азия")
                R.id.chipResortAmerika -> filterWorld("Америка")
                else -> {
                    updateListResorts(dataBase.getResorts())
                }
            }
        }
        return binding.root
    }

    override fun onClickImageItem(resort: Resort) {
        if (preferences.checkUser())
            dataBase.insertResortFavourites(resort.id, preferences.getUser().accountId)
        else
            Toast.makeText(requireContext(), "Пользователь не авторизирован", Toast.LENGTH_SHORT).show()
    }

    override fun onClickItem(resort: Resort) {
        dialogs.buildDialogBooking(resort)
    }

    private fun filterWorld(world: String) {
        updateListResorts(dataBase.getResortbyCountry(world))
    }
    private fun updateListResorts(list: List<Resort>) {
        adapter.submitList(list)
    }
}