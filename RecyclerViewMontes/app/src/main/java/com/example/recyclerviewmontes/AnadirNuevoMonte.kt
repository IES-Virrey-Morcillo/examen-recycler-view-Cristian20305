package com.example.recyclerviewmontes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclerviewmontes.databinding.ActivityAnadirNuevoMonteBinding

class AnadirNuevoMonte : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirNuevoMonteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAnadirNuevoMonteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_anadir_nuevo_monte)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnGuardar.setOnClickListener {


            val bundle = Bundle()
            bundle.putString("nombre", binding.edAnadirNombre.text.toString())
            bundle.putString("continente", binding.edAnadircontinente.text.toString())
            bundle.putString("altura", binding.edAnadirAltura.text.toString())
            bundle.putString("foto", binding.tvAnadirUrlImagen.text.toString())
            bundle.putString("urlinfo", binding.tvAnadirUrlInformacion.text.toString())


            val intent = Intent(this, MontesRecyclerView::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

}
