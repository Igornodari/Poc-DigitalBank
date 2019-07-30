package com.example.EverBank.utils

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Keys() {
    var senhaSaque: Long = 0
    var senhaGerente: Long = 0
    var senhaContas: Long = 0
    var senhaInfo: Long = 0
    var senhaDeposito: Long = 0
    var senhaOutros: Long = 0
    // Get a reference to our posts
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "senhaSaque" to senhaSaque++
//            "senhaGerente" to senhaGerente++,
//            "senhaContas" to senhaContas++,
//            "senhaInfo" to senhaInfo++,
//            "senhaDeposito" to senhaDeposito,
//            "senhaOutros" to senhaOutros
        )
    }


}
