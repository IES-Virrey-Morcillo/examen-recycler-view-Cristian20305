package com.example.recyclerviewmontes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewmontes.Adapter.MontesAdapter
import com.example.recyclerviewmontes.databinding.ActivityRecyclerViewBinding
import com.google.gson.Gson
import java.io.InputStream
import java.nio.charset.Charset

class MontesRecyclerView : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding
    private lateinit var copyList: MutableList<Montes>
    private lateinit var adapter: MontesAdapter
    private var montesMutableList: MutableList<Montes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración del SearchView

        binding.svMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    // Si el texto está vacío, restaurar la lista completa
                    adapter.filterByName(copyList.toMutableList())
                } else {
                    // Filtrar los ítems por el texto ingresado
                    val filteredList: MutableList<Montes> = copyList.filter {
                        it.nombre.lowercase().contains(newText.lowercase())
                    }.toMutableList()
                    adapter.filterByName(filteredList)
                }
                return false
            }
        })

        montesMutableList = getListFromJson()
        initRecyclerView()
        retrieveMontes()
    }

    private fun initRecyclerView() {
        adapter = MontesAdapter(
            montesList = montesMutableList,
            onClickListener = { superMontes -> onItemSelected(superMontes) },
            onClickDelete = { position -> onDeleteItem(position) }
        )

        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)

        binding.rvMontes.addItemDecoration(decoration)
        binding.rvMontes.layoutManager = manager
        binding.rvMontes.adapter = adapter

        binding.btnAnadirMonte.setOnClickListener{crearMonte()}

    }

    private fun crearMonte() {

        val intent = Intent(this, AnadirNuevoMonte::class.java)
        startActivity(intent)
    }

    private fun onDeleteItem(position: Int) {
        val removedMontes = montesMutableList[position]
        montesMutableList.removeAt(position)
        copyList.remove(removedMontes)
        adapter.notifyItemRemoved(position)
    }

    private fun onItemSelected(superMontes: Montes) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(superMontes.urlinfo)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent) // Abre la URL en el navegador
        } else {
            Toast.makeText(this, "No se puede abrir la URL", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getListFromJson(): MutableList<Montes> {
        val json: String? = getJsonFromAssets(this, "montes.json")
        return if (json != null) {
            val montesList = Gson().fromJson(json, Array<Montes>::class.java).toMutableList()
            copyList = montesList
            montesList
        } else {
            mutableListOf()
        }
    }

    private fun getJsonFromAssets(context: Context, file: String): String? {
        return try {
            val stream: InputStream = context.assets.open(file)
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            String(buffer, Charset.defaultCharset())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun retrieveMontes() {
        val bundle = intent.extras
        if (bundle != null) {
            val nombre = bundle.getString("nombre")
            val continente = bundle.getString("continente")
            val altura = bundle.getString("altura")
            val foto = bundle.getString("foto")
            val urlinfo = bundle.getString("urlinfo")

            val peliculaAnadir = Montes(
                0,
                nombre.toString(),
                continente.toString(),
                altura.toString(),
                foto.toString(),
                urlinfo.toString()
            )
            montesMutableList.add(peliculaAnadir)
            adapter.notifyItemInserted(montesMutableList.size - 1)
        }
    }
}
