package com.example.databasesqlite_dwizahranf2011500069

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry)

        val modeEdit = intent.hasExtra("kode") && intent.hasExtra("nama") &&
                intent.hasExtra("sks") && intent.hasExtra("sifat")

        title = if(modeEdit) "Edit Data Mata Kuliah" else "Entri Data Matakuliah"

        val etKdMatkul = findViewById<EditText>(R.id.etKdMatkul)
        val etNmMataKuliah = findViewById<EditText>(R.id.etNmMatkul)
        val spinsks = findViewById<Spinner>(R.id.spinsks)
        val rdWajib = findViewById<RadioButton>(R.id.rdwajib)
        val rdPilihan = findViewById<RadioButton>(R.id.rdpilihan)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val sks = arrayOf(2,3,4,6)
        val adpsks = ArrayAdapter(
            this@EntryActivity,
            android.R.layout.simple_spinner_dropdown_item, sks

        )
        spinsks.adapter= adpsks

        if(modeEdit) {
            val kode = intent.getStringExtra("kode")
            val nama = intent.getStringExtra("nama")
            val nilaiSks = intent.getIntExtra("sks",0 )
            val sifat = intent.getStringExtra("sifat")

            etKdMatkul.setText(kode)
            etNmMataKuliah.setText(nama)
            spinsks.setSelection(sks.indexOf(nilaiSks))
            if (sifat == "wajib") rdWajib.isChecked = true else rdPilihan.isChecked = true
        }
        etKdMatkul.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etKdMatkul.text}".isNotEmpty()&&"${etNmMataKuliah.text}".isNotEmpty() &&
                (rdWajib.isChecked || rdPilihan.isChecked )){
                  val db= DBHelper(this@EntryActivity)
                db.kdMatkul = "${etKdMatkul.text}"
                db.nmMatkul = "${etNmMataKuliah.text}"
                db.sks= (spinsks.selectedItem as Int).toString()
                db.sifat = if(rdWajib.isChecked) "wajib" else "Pilih"
                if (if (!modeEdit)db.simpan() else db.ubah("${etKdMatkul.text}")) {
                    Toast.makeText(
                         this@EntryActivity,
                        "Data Mata Kuliah Berhasil Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@EntryActivity,
                    "Data Nama Kuliah Gagal Disimpan",
                        Toast.LENGTH_SHORT

            ).show()


        }else
            Toast.makeText(
                this@EntryActivity,
                "Data Mata Kuliah Tidak Boleh Kosong",
                Toast.LENGTH_SHORT
            ).show()
            
    }
}}