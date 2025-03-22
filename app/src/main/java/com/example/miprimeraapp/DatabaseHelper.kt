package com.example.miprimeraapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dispositivos.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "dispositivos"
        const val COLUMN_ID = "id"
        const val COLUMN_TIPO = "tipo_dispositivo"
        const val COLUMN_MARCA = "marca"
        const val COLUMN_MODELO = "modelo"
        const val COLUMN_CODIGO = "codigo"
        const val COLUMN_NOMBRE = "nombre_encargado"
        const val COLUMN_CARGO = "cargo"
        const val COLUMN_AREA = "area"
        const val COLUMN_TIPO_MANTENIMIENTO = "tipo_mantenimiento"
        const val COLUMN_ESTADO = "estado_mantenimiento"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TIPO TEXT,
                $COLUMN_MARCA TEXT,
                $COLUMN_MODELO TEXT,
                $COLUMN_CODIGO TEXT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_CARGO TEXT,
                $COLUMN_AREA TEXT,
                $COLUMN_TIPO_MANTENIMIENTO TEXT,
                $COLUMN_ESTADO TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertarDispositivo(tipo: String, marca: String, modelo: String, codigo: String,
                            nombre: String, cargo: String, area: String,
                            tipoMantenimiento: String, estado: String): Long {

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TIPO, tipo)
            put(COLUMN_MARCA, marca)
            put(COLUMN_MODELO, modelo)
            put(COLUMN_CODIGO, codigo)
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_CARGO, cargo)
            put(COLUMN_AREA, area)
            put(COLUMN_TIPO_MANTENIMIENTO, tipoMantenimiento)
            put(COLUMN_ESTADO, estado)
        }

        val resultado = db.insert(TABLE_NAME, null, values)
        db.close()
        return resultado
    }

    fun obtenerTodosLosDispositivos(): ArrayList<String> {
        val lista = ArrayList<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM dispositivos", null)

        if (cursor.moveToFirst()) {
            do {
                val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo_dispositivo"))
                val marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"))
                val modelo = cursor.getString(cursor.getColumnIndexOrThrow("modelo"))
                val codigo = cursor.getString(cursor.getColumnIndexOrThrow("codigo"))
                lista.add("$tipo - $marca - $modelo - Código: $codigo")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

}