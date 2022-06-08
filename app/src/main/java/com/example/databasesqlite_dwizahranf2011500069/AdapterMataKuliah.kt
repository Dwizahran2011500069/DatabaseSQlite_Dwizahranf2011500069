package com.example.databasesqlite_dwizahranf2011500069

import android.app.Activity
import android.app.AlertDialog
import android.app.LauncherActivity.*
import android.content.*
import android.icu.text.LocaleDisplayNames
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdapterMataKuliah (
    private val getContext: Context,
    private val customListItem: ArrayList<MataKuliah>
        ):
        ArrayAdapter <MataKuliah>(getContext,0,customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var ListLayout = convertView
        val holder: ViewHolder
        if (ListLayout ==null) {
            val inflatelist = (getContext as Activity).layoutInflater
            ListLayout=inflatelist.inflate(R.layout.layout_matkul, parent,false )
            holder = ViewHolder()
            with(holder){
                tvNmMatkul = ListLayout.findViewById(R.id.tvNmMatkul)
                tvKdMatkul = ListLayout.findViewById(R.id.tvKdMatkul)
                btnEdit = ListLayout.findViewById(R.id.btnEdit)
                btnHapus = ListLayout.findViewById(R.id.btnHapus)
            }
            ListLayout.tag = holder
        }
        else
            holder = ListLayout.tag as ViewHolder
            val ListItem: MataKuliah = customListItem[position]
        holder.tvNmMatkul!!.setText(ListItem.nmMatkul)
        holder.tvKdMatkul!!.setText(ListItem.kdMatkul)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntryActivity::class.java)
            i.putExtra("kode", ListItem.kdMatkul)
            i.putExtra("nama", ListItem.nmMatkul)
            i.putExtra("sks", ListItem.sks)
            i.putExtra("sifat", ListItem.sifat)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener{
            val db = DBHelper(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvKdMatkul!!.text
            val nama = holder.tvNmMatkul!!.text
            with (alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""Apakah Anda Yakin Akan Menghapus Data Ini??
                    |
                    |$nama[$kode]
                    |
                """.trimMargin())
                setPositiveButton("Ya") {_, _->
                    if(db.hapus("$kode"))
                        Toast.makeText(
                            context,
                            "Data mata kuliah berhasil dihapus",
                            Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Mata Kuliah Gagal Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()


                }
                setNegativeButton("Tidak",null)
                create().show()
            }
        }

        return ListLayout!!
    }
        }
class ViewHolder {
    internal var tvNmMatkul: TextView? = null
    internal var tvKdMatkul: TextView? = null
    internal var btnEdit: TextView? = null
    internal var btnHapus: TextView? = null
}