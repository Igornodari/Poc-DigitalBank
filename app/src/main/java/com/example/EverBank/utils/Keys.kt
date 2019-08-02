package com.example.EverBank.utils

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Keys() {
    var senhaSaque: Long = 0
    var senhaGerente: Long = 0
    var senhaContas: Long = 0
    var senhaInfo: Long = 0
    var senhaDeposito: Long = 0
    var senhaOutros: Long = 0
}
