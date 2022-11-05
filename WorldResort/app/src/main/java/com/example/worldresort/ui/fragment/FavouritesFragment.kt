package com.example.worldresort.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.worldresort.R
import com.example.worldresort.database.DataBaseManager
import com.example.worldresort.databinding.FragmentFavouritesBinding
import com.example.worldresort.model.BookingDataUser
import com.example.worldresort.model.Resort
import com.example.worldresort.model.User
import com.example.worldresort.preferences.PreferencesManager
import com.example.worldresort.ui.activity.AuthorizationActivity
import com.example.worldresort.ui.adapters.adapter.ResortFavouritesRecyclerViewAdapter
import com.example.worldresort.ui.dialog.Dialogs
import com.example.worldresort.ui.interfaces.OnItemClickListener
import com.example.worldresort.validation.Validator
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class FavouritesFragment : Fragment(), OnItemClickListener {
    lateinit var validator: Validator
    lateinit var preferences: PreferencesManager
    lateinit var binding: FragmentFavouritesBinding
    lateinit var dataBase: DataBaseManager
    lateinit var adapter: ResortFavouritesRecyclerViewAdapter
    lateinit var dialogs: Dialogs
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        dataBase = DataBaseManager(requireContext())
        adapter = ResortFavouritesRecyclerViewAdapter(this)
        preferences = PreferencesManager(requireContext())
        validator = Validator(requireContext())
        dialogs = Dialogs(requireContext(), validator, preferences, dataBase)
        binding.recyclerViewFavourites.adapter = adapter
        if (preferences.checkUser()){
            binding.recyclerViewFavourites.visibility = View.VISIBLE
            updateList()
        }
        else
            binding.buttonProfileSignInFavourites.visibility = View.VISIBLE
        binding.buttonProfileSignInFavourites.setOnClickListener{
            startActivity(Intent(requireContext(), AuthorizationActivity::class.java))
        }
        return binding.root
    }
    private fun updateList() {
        val list = mutableListOf<Resort>()
        dataBase.getResortsFavourites(preferences.getUser().accountId).forEach{
            list.add(dataBase.getResortId(it.idSpecial, it.idResort)!!)
        }
        if (list.isNotEmpty())
            binding.textViewEmptyFavourite.visibility = View.GONE
        else
            binding.textViewEmptyFavourite.visibility = View.VISIBLE
        adapter.submitList(list)
    }
    override fun onClickItem(resort: Resort) {
        dialogs.buildDialogBooking(resort)
    }

    override fun onClickImageItem(resort: Resort) {
        dataBase.deleteResortFavourites(resort.idSpecial!!)
        updateList()
    }
}