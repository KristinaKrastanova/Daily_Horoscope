package com.example.f102348.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.f102348.domain.model.Sign


class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "horoscope"
        private const val TABLE_NAME = "dhoroscope"
        private const val UID = "id"
        private const val COLUMN_DATE_RANGE = "drange"
        private const val COLUMN_CURRENT_DATE = "cdate"
        private const val COLUMN_PREDICTION = "prediction"
        private const val COLUMN_COMPATIBILITY = "compatibility"
        private const val COLUMN_MOOD = "mood"
        private const val COLUMN_COLOR = "color"
        private const val COLUMN_LUCKY_NUMBER = "lnumber"
        private const val COLUMN_LUCKY_TIME = "ltime"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            ("CREATE TABLE " + TABLE_NAME + "("
                    + UID + " INT PRIMARY KEY," +
                    COLUMN_DATE_RANGE + " TEXT," + COLUMN_CURRENT_DATE + " TEXT," +
                    COLUMN_PREDICTION + " TEXT," + COLUMN_COMPATIBILITY + " TEXT," +
                    COLUMN_MOOD + " TEXT," + COLUMN_COLOR + " TEXT," +
                    COLUMN_LUCKY_NUMBER + " INT," + COLUMN_LUCKY_TIME + " TEXT" + ")")
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addSign(sign: Sign): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(UID, sign.sign)
        contentValues.put(COLUMN_DATE_RANGE, sign.dateRange)
        contentValues.put(COLUMN_CURRENT_DATE, sign.currentDate)
        contentValues.put(COLUMN_PREDICTION, sign.prediction)
        contentValues.put(COLUMN_COMPATIBILITY, sign.compatibility)
        contentValues.put(COLUMN_MOOD, sign.mood)
        contentValues.put(COLUMN_COLOR, sign.color)
        contentValues.put(COLUMN_LUCKY_NUMBER, sign.luckyNumber)
        contentValues.put(COLUMN_LUCKY_TIME, sign.luckyTime)
        val success = db.insertWithOnConflict(
            TABLE_NAME,
            null,
            contentValues,
            SQLiteDatabase.CONFLICT_REPLACE
        )
        db.close()
        return success
    }

    fun getSign(signId: Int): Sign {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT  * FROM $TABLE_NAME WHERE $UID=$signId", null)
        cursor.moveToFirst()
        return Sign(
            sign = cursor.getInt(cursor.getColumnIndexOrThrow(UID)),
            dateRange = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_RANGE)),
            currentDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CURRENT_DATE)),
            prediction = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PREDICTION)),
            compatibility = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPATIBILITY)),
            mood = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOOD)),
            color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR)),
            luckyNumber = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LUCKY_NUMBER)),
            luckyTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LUCKY_TIME))
        )
    }
}
