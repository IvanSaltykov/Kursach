package com.example.worldresort.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.worldresort.database.DataBaseManager
import com.example.worldresort.model.BookingDataUser
import com.example.worldresort.model.DataUser
import com.example.worldresort.model.Resort
import com.example.worldresort.model.User
import com.example.worldresort.preferences.PreferencesManager
import com.example.worldresort.R
import com.example.worldresort.validation.Validator
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class Dialogs(
    private val context: Context,
    private val validator: Validator,
    private val preferences: PreferencesManager,
    private val dataBase: DataBaseManager
) {
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun buildDialogBooking(resort: Resort) {
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar)
        dialog.setContentView(R.layout.dialog_resort)
        dialog.window?.setBackgroundDrawable(ColorDrawable(R.color.black))
        dialog.setCancelable(false)
        val imageView = dialog.findViewById<ImageView>(com.example.worldresort.R.id.imageView)
        val textWorld = dialog.findViewById<TextView>(R.id.textViewWorld)
        val textCountry = dialog.findViewById<TextView>(R.id.textViewCountry)
        val textCity = dialog.findViewById<TextView>(R.id.textViewCity)
        val textHotel = dialog.findViewById<TextView>(R.id.textViewHotel)
        val buttonBook = dialog.findViewById<MaterialButton>(R.id.buttonBookDialog)
        val textPrice = dialog.findViewById<TextView>(R.id.textViewPrice)
        val textDescription = dialog.findViewById<TextView>(R.id.textViewDescription)
        val imageViewResort = dialog.findViewById<ImageView>(R.id.imageViewResort)
        val imageViewHotel = dialog.findViewById<ImageView>(R.id.imageViewHotel)
        if (!preferences.checkUser())
            buttonBook.setEnabled(false)
        imageViewResort.setImageResource(resort.imageResort)
        imageViewHotel.setImageResource(resort.imageHotel)
        imageView.setOnClickListener {
            dialog.cancel()
        }
        buttonBook.setOnClickListener {
            buildDialogBook(preferences.getUser(), resort)
            dialog.cancel()
        }
        textWorld.text = context.getString(R.string.Part_World, resort.world)
        textCountry.text = context.getString(R.string.Country, resort.country)
        textCity.text = context.getString(R.string.City, resort.city)
        textHotel.text = context.getString(R.string.Hotel, resort.hotel)
        textPrice.text = context.getString(R.string.Price, resort.price)
        textDescription.text = resort.description
        dialog.create()
        dialog.show()
    }

    @SuppressLint("ResourceAsColor")
    private fun buildDialogBook(user: User, resort: Resort) {
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar)
        dialog.setContentView(R.layout.dialog_resort_booking)
        dialog.window?.setBackgroundDrawable(ColorDrawable(R.color.black))
        dialog.setCancelable(false)
        dialog.apply {
            val imageView = findViewById<ImageView>(R.id.imageViewDialogBook)
            val textWorld = findViewById<TextView>(R.id.textViewWorldDialogBook)
            val textCountry = findViewById<TextView>(R.id.textViewCountryDialogBook)
            val textCity = findViewById<TextView>(R.id.textViewCityDialogBook)
            val textHotel = findViewById<TextView>(R.id.textViewHotelDialogBook)
            val textPrice = findViewById<TextView>(R.id.textViewPriceDialogBook)
            val buttonBook = findViewById<MaterialButton>(R.id.buttonBookDialogBook)
            val editTextName = findViewById<EditText>(R.id.editTextNameDialogBook)
            val editTextSurname = findViewById<EditText>(R.id.editTextSurnameDialogBook)
            val editTextPatronymic = findViewById<EditText>(R.id.editTextPatronymicDialogBook)
            val editTextAge = findViewById<EditText>(R.id.editTextAgeDialogBook)
            val editTextPassportNumber = findViewById<EditText>(R.id.editTextPassportNumberDialogBook)
            val editTextPassportSeries = findViewById<EditText>(R.id.editTextPassportSeriesDialogBook)
            val inputLayoutName = findViewById<TextInputLayout>(R.id.inputLayoutNameDialogBook)
            val inputLayoutSurname = findViewById<TextInputLayout>(R.id.inputLayoutSurnameDialogBook)
            val inputLayoutPatronymic = findViewById<TextInputLayout>(R.id.inputLayoutPatronymicDialogBook)
            val inputLayoutAge = findViewById<TextInputLayout>(R.id.inputLayoutAgeDialogBook)
            val inputLayoutPassportNumber = findViewById<TextInputLayout>(R.id.inputLayoutPassportNumberDialogBook)
            val inputLayoutPassportSeries = findViewById<TextInputLayout>(R.id.inputLayoutPassportSeriesDialogBook)
            val dataUser = dataBase.getDataUser(user.accountId)
            textWorld.text = context.getString(R.string.Part_World)
            textWorld.text = context.getString(R.string.Part_World, resort.world)
            textCountry.text = context.getString(R.string.Country, resort.country)
            textCity.text = context.getString(R.string.City, resort.city)
            textHotel.text = context.getString(R.string.Hotel, resort.hotel)
            textPrice.text = context.getString(R.string.Price, resort.price)
            editTextName.setText(user.name)
            editTextSurname.setText(user.surname)
            editTextPatronymic.setText(dataUser?.patronymic)
            editTextAge.setText(if (dataUser?.age.toString() == "null") "" else dataUser?.age.toString())
            editTextPassportNumber.setText(if (dataUser?.passportNumber.toString() == "null") "" else dataUser?.passportNumber.toString())
            editTextPassportSeries.setText(if (dataUser?.passportSeries.toString() == "null") "" else dataUser?.passportSeries.toString())
            imageView.setOnClickListener {
                cancel()
            }
            buttonBook.setOnClickListener {
                inputLayoutName.error = validator.checkEditText(editTextName.text.toString())
                inputLayoutSurname.error = validator.checkEditText(editTextSurname.text.toString())
                inputLayoutPatronymic.error = validator.checkEditText(editTextPatronymic.text.toString())
                inputLayoutAge.error = validator.checkEditText(editTextAge.text.toString())
                inputLayoutPassportNumber.error = validator.checkEditText(editTextPassportNumber.text.toString())
                inputLayoutPassportSeries.error = validator.checkEditText(editTextPassportSeries.text.toString())
                inputLayoutAge.error = validator.checkAge(editTextAge.text.toString())
                inputLayoutPassportNumber.error = validator.checkPassportNumber(editTextPassportNumber.text.toString())
                inputLayoutPassportSeries.error = validator.checkPassportSeries(editTextPassportSeries.text.toString())
                if (inputLayoutName.error == null &&
                    inputLayoutSurname.error == null &&
                    inputLayoutPatronymic.error == null &&
                    inputLayoutAge.error == null &&
                    inputLayoutPassportNumber.error == null &&
                    inputLayoutPassportSeries.error == null) {
                    dataBase.insertResortBook(
                        resort.id,
                        preferences.getUser().accountId,
                        BookingDataUser(
                            editTextName.text.toString().trim(),
                            editTextSurname.text.toString().trim(),
                            editTextPatronymic.text.toString().trim(),
                            Integer.parseInt(editTextAge.text.toString()),
                            Integer.parseInt(editTextPassportNumber.text.toString()),
                            Integer.parseInt(editTextPassportSeries.text.toString())
                        )
                    )
                    cancel()
                }
            }
            create()
            show()
        }
    }
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun buildDialogPersonalData(user: User) {
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar)
        dialog.setContentView(R.layout.dialog_personal_data)
        dialog.window?.setBackgroundDrawable(ColorDrawable(R.color.black))
        dialog.setCancelable(false)
        dialog.apply {
            val textViewSave = findViewById<TextView>(R.id.textViewSaveDialog)
            val editTextName = findViewById<EditText>(R.id.editTextNameDialog)
            val editTextSurname = findViewById<EditText>(R.id.editTextSurnameDialog)
            val editTextPatronymic = findViewById<EditText>(R.id.editTextPatronymicDialog)
            val editTextAge = findViewById<EditText>(R.id.editTextAgeDialog)
            val editTextPassportNumber = findViewById<EditText>(R.id.editTextPassportNumberDialog)
            val editTextPassportSeries = findViewById<EditText>(R.id.editTextPassportSeriesDialog)
            val inputLayoutName = findViewById<TextInputLayout>(R.id.inputLayoutNameDialog)
            val inputLayoutSurname = findViewById<TextInputLayout>(R.id.inputLayoutSurnameDialog)
            val inputLayoutPatronymic = findViewById<TextInputLayout>(R.id.inputLayoutPatronymicDialog)
            val inputLayoutAge = findViewById<TextInputLayout>(R.id.inputLayoutAgeDialog)
            val inputLayoutPassportNumber = findViewById<TextInputLayout>(R.id.inputLayoutPassportNumberDialog)
            val inputLayoutPassportSeries = findViewById<TextInputLayout>(R.id.inputLayoutPassportSeriesDialog)
            val dataUser = dataBase.getDataUser(user.accountId)
            textViewSave.setOnClickListener {
                inputLayoutName.error = validator.checkEditText(editTextName.text.toString())
                inputLayoutSurname.error = validator.checkEditText(editTextSurname.text.toString())
                inputLayoutPatronymic.error = validator.checkEditText(editTextPatronymic.text.toString())
                inputLayoutAge.error = validator.checkAge(editTextAge.text.toString())
                inputLayoutPassportNumber.error = validator.checkPassportNumber(editTextPassportNumber.text.toString())
                inputLayoutPassportSeries.error = validator.checkPassportSeries(editTextPassportSeries.text.toString())
                if (inputLayoutName.error == null &&
                    inputLayoutSurname.error == null &&
                    inputLayoutPatronymic.error == null &&
                    inputLayoutAge.error == null &&
                    inputLayoutPassportNumber.error == null &&
                    inputLayoutPassportSeries.error == null) {
                    if (dataBase.getDataUser(user.accountId) != null)
                        dataBase.deleteDataUser(dataUser!!.accountId)
                    dataBase.insertDataUser(
                        DataUser(
                            user.accountId,
                            editTextPatronymic.text.toString(),
                            Integer.parseInt(editTextAge.text.toString()),
                            Integer.parseInt(editTextPassportNumber.text.toString()),
                            Integer.parseInt(editTextPassportSeries.text.toString())
                        )
                    )
                    cancel()
                }
            }
            editTextName.setText(user.name)
            editTextSurname.setText(user.surname)
            editTextPatronymic.setText(dataUser?.patronymic)
            editTextAge.setText(if (dataUser?.age.toString() == "null") "" else dataUser?.age.toString())
            editTextPassportNumber.setText(if (dataUser?.passportNumber.toString() == "null") "" else dataUser?.passportNumber.toString())
            editTextPassportSeries.setText(if (dataUser?.passportSeries.toString() == "null") "" else dataUser?.passportSeries.toString())
            create()
            show()
        }
    }
}