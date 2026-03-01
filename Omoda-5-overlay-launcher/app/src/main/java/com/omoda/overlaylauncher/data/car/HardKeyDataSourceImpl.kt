package com.omoda.overlaylauncher.data.car

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import com.omoda.overlaylauncher.util.AppLogger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HardKeyDataSourceImpl(
    private val context: Context
) : HardKeyDataSource {

    override val hardKeyEvents: Flow<String> = callbackFlow {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "com.saic.keyevent.hardkey.report") {
                    val extras = intent.extras
                    if (extras != null) {
                        val details = extras.keySet().joinToString { key -> "$key=${extras[key]}" }
                        AppLogger.d("HardKey Intent extras: $details")
                        
                        // Parse logic mapping based on reverse engineering logs
                        val keyCode = extras.get("keyCode")?.toString() ?: "Unknown"
                        val isDown = extras.getBoolean("keyDown", false)
                        val isLongPress = extras.getBoolean("longPress", false)
                        
                        val actionType = if (isDown) "DOWN" else "UP"
                        val eventType = if (isLongPress) "LONG" else "SHORT"
                        
                        trySend("Key: $keyCode | $actionType | $eventType")
                    } else {
                        trySend("Unknown HardKey received (no extras)")
                    }
                }
            }
        }

        val filter = IntentFilter("com.saic.keyevent.hardkey.report")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(receiver, filter)
        }

        awaitClose {
            context.unregisterReceiver(receiver)
        }
    }
}
