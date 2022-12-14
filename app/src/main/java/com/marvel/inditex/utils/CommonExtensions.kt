package com.marvel.inditex.utils

import java.math.BigInteger
import java.security.MessageDigest


fun getTimeStamp(): String {
    val tsLong = System.currentTimeMillis() / 1000
    return tsLong.toString()
}


fun md5(ts: String, privateKey: String, publicKey: String): String {
    val stringToMD5 = ts + privateKey + publicKey
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(stringToMD5.toByteArray())).toString(16).padStart(32, '0')
}