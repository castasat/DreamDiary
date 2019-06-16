package com.openyogaland.denis.dreamdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.openyogaland.denis.dreamdiary.view.fragment.DayFragment
import com.openyogaland.denis.dreamdiary.view.fragment.NightFragment

public class
MainActivity : AppCompatActivity()
{
  // fields
  private var dayFragment : DayFragment? = null
  private var nightFragment : NightFragment? = null
  
  override fun onCreate(savedInstanceState : Bundle?)
  {
    super
    .onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    nightFragment = NightFragment()
    loadFragment(nightFragment)
  }
  
  private fun loadFragment(fragmentToLoad : Fragment?)
  {
    val fragmentManager : FragmentManager = supportFragmentManager
    
    val transaction : FragmentTransaction =
      fragmentManager
      .beginTransaction()
    
    if(fragmentToLoad != null)
    {
      if(!fragmentToLoad.isAdded)
      {
        transaction.add(R.id.main_activity_content, fragmentToLoad)
      }
      else
      {
        transaction.replace(R.id.main_activity_content, fragmentToLoad)
      }
    }
    
    transaction
    .commit()
  }
  
  
}
