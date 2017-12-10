package org.amoustakos.utils.android

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

/**
 * Created by antonis on 10/12/2017.
 */

object ComponentUtils {


    fun toggleComponent(context: Context, componentClass: Class<*>, enable: Boolean) {
        val componentName = ComponentName(context, componentClass)
        val pm = context.packageManager
        pm.setComponentEnabledSetting(componentName,
                if (enable)
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                else
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)
    }


    //TODO: check android O compatibility
    @Throws(NullPointerException::class)
    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        manager.getRunningServices(Integer.MAX_VALUE).forEach { service ->
            if (serviceClass.name == service.service.className)
                return true
        }
        return false
    }


}
