package com.interactive.buddy.ui.navigation.ui.notifications

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.interactive.buddy.R
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.databinding.FragmentNotificationsBinding
import com.interactive.buddy.services.ChatService
import com.interactive.buddy.services.RequestService
import kotlinx.android.synthetic.main.fragment_request.*

class NotificationsFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_notifications, container, false)
  }

  /**
   * Finding views by id.
   */
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val textView: TextView = view.findViewById(R.id.text_notifications);
    textView.text =
      "You currently have a karma balance of " + SharedPrefManager.getInstance(requireContext()).karma + " karma points\n" +
      "\nYou gain karma points when sending text messages."
  }
}