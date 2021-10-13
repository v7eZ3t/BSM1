package com.example.bsm1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.bsm1.databinding.NotesFragmentBinding
import kotlinx.android.synthetic.main.notes_fragment.*


class NotesFragment : Fragment() {

    var passwordString = MutableLiveData<String>()
    var passwordButtonString = MutableLiveData<String>()
    var noteString = MutableLiveData<String>()


    private val sharedViewModel: MainViewModel by activityViewModels()
    private var _binding: NotesFragmentBinding? = null


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = NotesFragmentBinding.inflate(inflater, container, false)
        _binding = binding
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            notesFrag = this@NotesFragment
        }
        startFun()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun startFun(){
        passwordButtonString.value = "Change password"
        noteString.value = loadData("Note")!!
        editPasswordView.visibility = GONE
    }


    private val SHARED_PREFS = "sharedPreferences"

    fun checkPassword(){
        editPasswordView.visibility = VISIBLE;
        passwordButtonString.value = "Submit"
        val password = passwordString.value
        if (password != null) {
            Log.d("aaaa", password) }

        if (password.isNullOrBlank()){
            Log.d("Password", "empty")
        } else
            Log.d("Password", "not empty")

            if (password?.isNotEmpty() == true){
                if (password.length in 4..15){
                    saveData("Password", password)
                    passwordButtonString.value = "Change password"
                    makeToast("Password changed")
                } else
                    makeToast("Password must contain between 4 and 16 characters")
            }
    }

    fun saveNote(){
        val note = noteString.value
        if (note != null) {
            saveData("Note", note)
        }
        makeToast("Note saved")
    }

    fun loadData(KEY: String): String? {
        val sharedPreferences =
            context?.getSharedPreferences(SHARED_PREFS, AppCompatActivity.MODE_PRIVATE)

        return sharedPreferences?.getString(KEY, "")
    }

    fun saveData(KEY: String, TEXT: String) {
        val sharedPreferences = context?.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
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