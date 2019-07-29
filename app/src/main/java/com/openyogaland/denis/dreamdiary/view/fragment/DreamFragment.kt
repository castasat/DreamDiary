package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.model.Dream

public class
DreamFragment : Fragment()
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
      inflater.inflate(R.layout.dream_fragment, container, false)
    
    return view
  }
  
  override fun onActivityCreated(savedInstanceState : Bundle?)
  {
    super.onActivityCreated(savedInstanceState)
  }
}
