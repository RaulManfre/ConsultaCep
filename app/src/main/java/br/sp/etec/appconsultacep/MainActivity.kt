package br.sp.etec.appconsultacep

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.sp.etec.appconsultacep.api.ViaCepClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val edtCep = findViewById<EditText>(R.id.edtCep)
        val btnConsultar = findViewById<Button>(R.id.btnConsultar)
        val logradouro = findViewById<TextView>(R.id.txtLogradouro)
        btnConsultar.setOnClickListener {
            val cep = edtCep.text.toString()
            if (cep.length != 8){
                Toast.makeText(this,"CEP INVÁLIDO", Toast.LENGTH_LONG)

            }
            lifecycleScope.launch {
                val endereco = ViaCepClient.instance.buscarEndereco(cep)
                logradouro.setText(endereco.logradouro)
            }




        }

    }
}