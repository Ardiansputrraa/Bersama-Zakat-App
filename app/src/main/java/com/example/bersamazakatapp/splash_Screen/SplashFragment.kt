package com.example.bersamazakatapp.splash_Screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentSplashBinding
import com.example.bersamazakatapp.databinding.FragmentZakatEmasBinding

class SplashFragment : Fragment() {

    private lateinit var topAnimation: Animation
    private lateinit var bottomAnimation: Animation
    private lateinit var rightAnimation: Animation

    private lateinit var imageView: ImageView
    private lateinit var textTeknik: TextView
    private lateinit var textZakat: TextView


    private var _splashBinding : FragmentSplashBinding? = null
    private val splashBinding get() = _splashBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _splashBinding = FragmentSplashBinding.inflate(inflater,container,false)
        val view = splashBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation)
        rightAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.right_animation)

        splashBinding.imageLogo.animation = topAnimation
        splashBinding.textTeknik.animation = bottomAnimation
        splashBinding.textZakat.animation = rightAnimation


        Handler().postDelayed({
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_splashFragment_to_splashSecondFragment)
        }, 3000)
    }
}