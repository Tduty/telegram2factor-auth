package info.tduty.android_example

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    companion object {
        const val TELEGRAM_PAGE_ID = "noble_goal_bot"
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            intentMessageTelegram("test")
        }
    }

    private fun intentMessageTelegram(msg: String) {
        val appName = "org.telegram.messenger"
        val isAppInstalled: Boolean = isAppAvailable(requireActivity().applicationContext, appName)
        if (isAppInstalled) {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=${TELEGRAM_PAGE_ID}"))
            requireActivity().startActivity(Intent.createChooser(myIntent, "Share with"))
        } else {
            Toast.makeText(requireActivity(), "Telegram not Installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isAppAvailable(context: Context, appName: String): Boolean {
        val pm: PackageManager = context.packageManager
        return try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
