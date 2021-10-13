package com.example.bsm1

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.bsm1.databinding.StartFragmentBinding
import kotlinx.android.synthetic.main.activity_main.*


class FragmentStart : Fragment() {

    var passwordString = MutableLiveData<String>()
    var infoString = MutableLiveData<String>()


    private val sharedViewModel: MainViewModel by activityViewModels()
    private var _binding: StartFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = StartFragmentBinding.inflate(inflater, container, false)
        _binding = binding
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            startFrag = this@FragmentStart
        }
        infoString.value = "Enter your password"

        if (loadData() == "npw"){

            infoString.value = "Define your password"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkPassword(){
        val password = passwordString.value
        if (password != null) {Log.d("aaaa", password) }

        if (password.isNullOrBlank()){
            Log.d("Password", "empty")
        } else
            Log.d("Password", "not empty")
        if (loadData() == "npw" ){
            if (password?.isNotEmpty() == true){
                if (password.length in 4..15){
                    saveData(password)
                    infoString.value = "Enter your password"
                } else
                    infoString.value = "Password must contain between 4 and 16 characters"
            } else
                infoString.value = "Define your password"
        }
        if (password?.isNotEmpty() == true && (password.length < 4 || password.length > 16)) {
            infoString.value = "Password must contain between 4 and 16 characters"
        }
        if (password?.isNotEmpty() == true && password != loadData() && password.length >= 4 && password.length <= 16) {
            infoString.value = "Enter password"
            makeToast("Wrong password")
        }
        if (loadData() == password){
            goToNotes()
        }
    }

    private fun goToNotes(){
        findNavController().navigate(R.id.action_fragmentStart_to_notesFragment)
    }

    private val SHARED_PREFS = "sharedPreferences"
    private val KEY = "Password"


    fun loadData(): String? {
        val sharedPreferences =
            context?.getSharedPreferences(SHARED_PREFS, AppCompatActivity.MODE_PRIVATE)
        if(sharedPreferences?.contains(KEY) == false){
            return "npw"
        }

        return sharedPreferences?.getString(KEY, "")
    }

    fun saveData(TEXT: String) {
        val sharedPreferences = context?.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString(KEY, TEXT)
        editor?.apply()
    }

    fun makeToast(text: String){
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(activity, text, duration)
        toast.show()
    }


}