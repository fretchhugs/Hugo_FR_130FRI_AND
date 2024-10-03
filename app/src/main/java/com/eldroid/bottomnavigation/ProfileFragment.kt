package com.eldroid.bottomnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast


class ProfileFragment : Fragment() {
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var checkBoxReading: CheckBox
    private lateinit var checkBoxTravelling: CheckBox
    private lateinit var checkBoxMusic: CheckBox
    private lateinit var buttonSave: Button
    private lateinit var textViewProfileInfo: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        editTextName = view.findViewById(R.id.editTextName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        radioGroupGender = view.findViewById(R.id.radioGroupGender)
        checkBoxReading = view.findViewById(R.id.checkBoxReading)
        checkBoxTravelling = view.findViewById(R.id.checkBoxTraveling)
        checkBoxMusic = view.findViewById(R.id.checkBoxMusic)
        buttonSave = view.findViewById(R.id.buttonSave)
        textViewProfileInfo = view.findViewById(R.id.textViewProfileInfo)

        buttonSave.setOnClickListener {
            saveProfile()
        }

        return view
    }

    private fun saveProfile() {
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val selectedGenderId = radioGroupGender.checkedRadioButtonId
        val gender = if (selectedGenderId != -1) {
            val radioButton = view?.findViewById<RadioButton>(selectedGenderId)
            radioButton?.text.toString()
        } else {
            "Not specified"
        }

        val prefersRead = checkBoxReading.isChecked
        val prefersTrav = checkBoxTravelling.isChecked
        val prefersMusc = checkBoxMusic.isChecked

        textViewProfileInfo.text = "Profile Saved:\n\nName: $name\nEmail: $email\nGender: $gender\nReading: $prefersRead\nTravelling: $prefersTrav\nMusic: $prefersMusc"
        Toast.makeText(requireContext(), "Profile Saved:\nName: $name\nEmail: $email\nGender: $gender\nReading: $prefersRead\nTravelling: $prefersTrav\nMusic: $prefersMusc", Toast.LENGTH_LONG).show()
    }
}