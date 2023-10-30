package com.example.bersamazakatapp.splash_Screen

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.databinding.FragmentSplashBinding
import com.example.bersamazakatapp.databinding.FragmentSplashSecondBinding

class SplashSecondFragment : Fragment() {

    private var _splashSecondBinding : FragmentSplashSecondBinding? = null
    private val splashSecondBindingBinding get() = _splashSecondBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _splashSecondBinding = FragmentSplashSecondBinding.inflate(inflater,container,false)
        val view = splashSecondBindingBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_splashSecondFragment_to_zakatEmasFragment)
        }, 2000)
    }

}