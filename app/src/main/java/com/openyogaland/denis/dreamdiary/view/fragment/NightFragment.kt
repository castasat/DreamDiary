package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.model.Dream
import kotlinx.android.synthetic.main.activity_main.*

public class
NightFragment : Fragment()
{
  companion object
  {
    private const val DREAM = "dream"
    
    fun bundleArgs(dream : Dream) : Bundle
    {
      return Bundle()
      .apply{
        this.putParcelable(DREAM, dream)
      }
    }
  }
  
  override fun onCreateView(inflater : LayoutInflater,
                            container : ViewGroup?,
                            savedInstanceState : Bundle?) : View?
  {
    val view : View =
      inflater.inflate(R.layout.night_fragment,
                       container,
                       false)
    
    return view
  }

  override fun
  onViewCreated(view : View,
                savedInstanceState : Bundle?)
  {
    super
    .onViewCreated(view,
                   savedInstanceState)
  
    val navigationController =
      Navigation
      .findNavController(requireActivity(),
                         R.id.navigationHost)
  
    bottomNavigation
    .setupWithNavController(navigationController)
  }
}
