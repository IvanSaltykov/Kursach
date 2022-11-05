package com.example.worldresort.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.worldresort.R
import com.example.worldresort.database.DataBaseManager
import com.example.worldresort.databinding.FragmentProfileBinding
import com.example.worldresort.model.BookingDataUser
import com.example.worldresort.model.Resort
import com.example.worldresort.model.User
import com.example.worldresort.preferences.PreferencesManager
import com.example.worldresort.ui.activity.AuthorizationActivity
import com.example.worldresort.ui.adapters.adapter.ResortBookRecyclerViewAdapter
import com.example.worldresort.ui.dialog.Dialogs
import com.example.worldresort.validation.Validator
import com.google.android.material.button.MaterialButton

class ProfileFragment : Fragment(), ResortBookRecyclerViewAdapter.onClickListener {
    lateinit var validator: Validator
    lateinit var binding: FragmentProfileBinding
    lateinit var dataBase: DataBaseManager
    lateinit var preferences: PreferencesManager
    lateinit var adapter: ResortBookRecyclerViewAdapter
    lateinit var dialogs: Dialogs
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        preferences = PreferencesManager(requireContext())
        dataBase = DataBaseManager(requireContext())
        validator = Validator(requireContext())
        adapter = ResortBookRecyclerViewAdapter(this)
        dialogs = Dialogs(requireContext(), validator, preferences, dataBase)
        binding.recyclerViewBook.adapter = adapter
        if (preferences.checkUser()) {
            binding.profile.visibility = View.VISIBLE
            profileUser(preferences.getUser())
            updateList()
        }
        else
            binding.buttonProfileSignIn.visibility = View.VISIBLE
        binding.buttonProfileSignIn.setOnClickListener {
            startActivity(Intent(requireContext(), AuthorizationActivity::class.java))
        }
        binding.buttonSignOutProfile.setOnClickListener {
            preferences.clearPreferences()
            binding.profile.visibility = View.GONE
            binding.buttonProfileSignIn.visibility = View.VISIBLE
        }
        binding.buttonPersonalDataProfile.setOnClickListener {
            dialogs.buildDialogPersonalData(preferences.getUser())
        }
        return binding.root
    }
    private fun profileUser(user: User) {
        binding.textViewNameUserProfile.text = user.name
        binding.textViewSurnameUserProfile.text = user.surname
    }
    private fun updateList() {
        val list = mutableListOf<Resort>()
        dataBase.getResortsBook(preferences.getUser().accountId).forEach{
            list.add(dataBase.getResortId(it.idSpecial, it.idResort)!!)
        }
        if (list.isNotEmpty())
            binding.textViewEmptyProfile.visibility = View.GONE
        else
            binding.textViewEmptyProfile.visibility = View.VISIBLE
        adapter.submitList(list, dataBase.getResortsBooking(preferences.getUser().accountId))
    }
    @SuppressLint("ResourceAsColor")
    override fun onClickItem(resort: Resort, dataUser: BookingDataUser) {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar)
        dialog.setContentView(R.layout.dialog_resort_book)
        dialog.window?.setBackgroundDrawable(ColorDrawable(R.color.black))
        dialog.setCancelable(false)
        val imageView = dialog.findViewById<ImageView>(R.id.imageViewDialogBookProfile)
        val textWorld = dialog.findViewById<TextView>(R.id.textViewCountryDialogBookProfile)
        val textCountry = dialog.findViewById<TextView>(R.id.textViewWorldDialogBookProfile)
        val textCity = dialog.findViewById<TextView>(R.id.textViewCityDialogBookProfile)
        val textHotel = dialog.findViewById<TextView>(R.id.textViewHotelDialogBookProfile)
        val textName = dialog.findViewById<TextView>(R.id.textViewNameDialogBookProfile)
        val textSurname = dialog.findViewById<TextView>(R.id.textViewSurnameDialogBookProfile)
        val textPatronymic = dialog.findViewById<TextView>(R.id.textViewPatronymicDialogBookProfile)
        val textAge = dialog.findViewById<TextView>(R.id.textViewAgeDialogBookProfile)
        val textPassport = dialog.findViewById<TextView>(R.id.textViewPassportDialogBookProfile)
        val textPrice = dialog.findViewById<TextView>(R.id.textViewPriceDialogBookProfile)
        val buttonBook = dialog.findViewById<MaterialButton>(R.id.buttonBookDialogBookProfile)
        val imageResort = dialog.findViewById<ImageView>(R.id.imageViewResortDialogBookProfile)
        imageView.setOnClickListener {
            dialog.cancel()
        }
        buttonBook.setOnClickListener {
            val dialogDelete = AlertDialog.Builder(requireContext())
                .setTitle("Вы действительно хотите удалить бронь?")
                .setNegativeButton("Нет", null)
                .setPositiveButton("Да") { DialogInterface, i ->
                    dataBase.deleteResortBook(resort.idSpecial!!)
                    updateList()
                    dialog.cancel()
                }
                .create()
            dialogDelete.show()
        }
        imageResort.setImageResource(resort.image)
        textWorld.text = getString(R.string.Part_World, resort.world)
        textCountry.text = getString(R.string.Country, resort.country)
        textCity.text = getString(R.string.City, resort.city)
        textHotel.text = getString(R.string.Hotel, resort.hotel)
        textPrice.text = getString(R.string.Price, resort.price)
        textName.text = "Имя: ${dataUser.name}"
        textSurname.text = "Фамилия: ${dataUser.surname}"
        textPatronymic.text = "Отчество: ${dataUser.patronymic}"
        textAge.text = "Возраст: ${dataUser.age.toString()}"
        textPassport.text = "Паспорт: ${dataUser.passportNumber} ${dataUser.passportSeries}"
        dialog.create()
        dialog.show()
    }
}