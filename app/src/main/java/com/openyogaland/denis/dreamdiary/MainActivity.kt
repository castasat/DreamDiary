package com.openyogaland.denis.dreamdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.openyogaland.denis.dreamdiary.view.fragment.DayFragment

public class
MainActivity : AppCompatActivity()
{
  private var dayFragment : DayFragment? = null
  
  override fun onCreate(savedInstanceState : Bundle?)
  {
    super
    .onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    dayFragment = DayFragment()
    loadFragment(dayFragment)
  }
  
  private fun loadFragment(fragmentToLoad : Fragment?)
  {
    val fragmentManager : FragmentManager = supportFragmentManager
    clearFragmentBackStack(fragmentManager)
    
    val transaction : FragmentTransaction = fragmentManager.beginTransaction()
    
    if((fragmentToLoad != null) && (!fragmentToLoad.isAdded))
    {
      when(fragmentToLoad)
      {
        is DayFragment ->
          transaction.add(R.id.main_activity_content, fragmentToLoad)
      }
    }
  
    transaction.commit()
    transaction.addToBackStack(null)
  }
  
  private fun clearFragmentBackStack(fragmentManager : FragmentManager)
  {
    val backStackEntryCount : Int = fragmentManager.backStackEntryCount
    
    for(count in backStackEntryCount downTo 1)
    {
      fragmentManager.popBackStack()
    }
  }
}
