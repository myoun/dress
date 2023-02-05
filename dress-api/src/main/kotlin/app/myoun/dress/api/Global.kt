@file:JvmName("Global")
package app.myoun.dress.api

import app.myoun.dress.api.scheduler.minecraft
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val gson = Gson()
val scope: CoroutineScope = CoroutineScope(Dispatchers.minecraft)