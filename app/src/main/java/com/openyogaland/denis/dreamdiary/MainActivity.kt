package com.openyogaland.denis.dreamdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.openyogaland.denis.dreamdiary.view.fragment.DayFragment
import com.openyogaland.denis.dreamdiary.view.fragment.NightFragment
import com.splunk.mint.Mint
import kotlinx.android.synthetic.main.activity_main.*

public class
MainActivity : AppCompatActivity()
{
  override fun onCreate(savedInstanceState : Bundle?)
  {
    super
    .onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}
