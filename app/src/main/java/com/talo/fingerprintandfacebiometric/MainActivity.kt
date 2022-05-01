package com.talo.fingerprintandfacebiometric


import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import com.talo.fingerprintandfacebiometric.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var cancellationSignal: CancellationSignal? = null
    private val authPromptCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    authenticateMsg("AuthenticateFail : $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    authenticateMsg("Authenticate Successful")
                    startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                }
//            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
//                super.onAuthenticationSucceeded(result)
//                authenticateMsg("Authenticate Successful")
//                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
//            }
//
//            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
//                super.onAuthenticationError(errorCode, errString)
//                authenticateMsg("AuthenticateFail : $errString")
//            }

            }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkBiometric()
        binding.btnAuth.setOnClickListener {
            TODO("API 29+")
//            val biometricPrompt: BiometricPrompt = BiometricPrompt.Builder(this)
//                .setTitle("Prompt")
//                .setSubtitle("Authenticate")
//                .setDescription("Text")
//                .setNegativeButton("Cancel", this.mainExecutor) { _, _ ->
//                    authenticateMsg("Authenticate cancel")
//                }.build()
//            biometricPrompt.authenticate(getCancelSignal(), mainExecutor, authPromptCallback)
        }
    }

    private fun authenticateMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkBiometric(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {
            authenticateMsg("Fingerprint authentication has been enable in Setting")
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            authenticateMsg("Fingerprint authentication permission is enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    private fun getCancelSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            authenticateMsg("Authentication was cancel by the user")
        }
        return cancellationSignal as CancellationSignal
    }
}