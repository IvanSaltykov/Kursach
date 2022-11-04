package com.example.worldresort.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.worldresort.model.*

class DataBaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "World_Resort"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME_USERS = "USERS"
        private const val TABLE_NAME_RESORTS = "RESORTS"
        private const val TABLE_NAME_FAVOURITES = "FAVOURITES"
        private const val TABLE_NAME_BOOK = "BOOK"
        private const val TABLE_NAME_PERSONAL_DATA = "PERSONAL_DATA"
        private const val TABLE_NAME_ACCOUNT = "LOGIN_AND_PASSWORD"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME_RESORTS(id INTEGER PRIMARY KEY AUTOINCREMENT, WORLD TEXT, COUNTRY TEXT, CITY TEXT, HOTEL TEXT, IMAGE INTEGER, PRICE INTEGER)")

        db?.execSQL("CREATE TABLE $TABLE_NAME_ACCOUNT(id INTEGER PRIMARY KEY, LOGIN TEXT, PASSWORD TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_NAME_USERS(ACCOUNT_ID INTEGER, FIRSTNAME TEXT, LASTNAME TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_NAME_PERSONAL_DATA(ACCOUNT_ID INTEGER, PATRONYMIC TEXT, AGE INTEGER, PASSPORT_NUMBER INTEGER, PASSPORT_SERIES INTEGER)")

        db?.execSQL("CREATE TABLE $TABLE_NAME_FAVOURITES(id INTEGER PRIMARY KEY AUTOINCREMENT, id_RESORT INTEGER, ACCOUNT_ID INTEGER)")

        db?.execSQL("CREATE TABLE $TABLE_NAME_BOOK(id INTEGER PRIMARY KEY AUTOINCREMENT, id_RESORT INTEGER, ACCOUNT_ID INTEGER, FIRSTNAME TEXT, LASTNAME TEXT, PATRONYMIC TEXT, AGE INTEGER, PASSPORT_NUMBER INTEGER, PASSPORT_SERIES INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun insertResort(resort: InsertResort) {
        writableDatabase.insert(
            TABLE_NAME_RESORTS,
            null,
            ContentValues().apply {
                put("WORLD", resort.world)
                put("COUNTRY", resort.country)
                put("CITY", resort.city)
                put("HOTEL", resort.hotel)
                put("IMAGE", resort.image)
                put("PRICE", resort.price)
            }
        )
    }
    fun getResortId(id: Int, idResort: Int): Resort? {
        val cursor = readableDatabase.query(TABLE_NAME_RESORTS,
            arrayOf("id", "WORLD", "COUNTRY", "CITY", "HOTEL", "IMAGE", "PRICE"), "id = ?", arrayOf(idResort.toString()), null, null, null)
        var item: Resort? = null
        with(cursor) {
            if (moveToFirst()) {
                item = Resort(
                    id = getInt(getColumnIndexOrThrow("id")),
                    world = getString(getColumnIndexOrThrow("WORLD")),
                    country = getString(getColumnIndexOrThrow("COUNTRY")),
                    city = getString(getColumnIndexOrThrow("CITY")),
                    hotel = getString(getColumnIndexOrThrow("HOTEL")),
                    image = getInt(getColumnIndexOrThrow("IMAGE")),
                    idSpecial = id,
                    price = getInt(getColumnIndexOrThrow("PRICE"))
                )
            }
            close()
        }
        close()
        return item
    }
    fun getResorts(): List<Resort> {
        val cursor = readableDatabase.query(TABLE_NAME_RESORTS,
            arrayOf("id", "WORLD", "COUNTRY", "CITY", "HOTEL", "IMAGE", "PRICE"), null, null, null, null, null)
        val items = mutableListOf<Resort>()
        with(cursor) {
            if (moveToFirst()) {
                do {
                    items.add(
                        Resort(
                            id = getInt(getColumnIndexOrThrow("id")),
                            world = getString(getColumnIndexOrThrow("WORLD")),
                            country = getString(getColumnIndexOrThrow("COUNTRY")),
                            city = getString(getColumnIndexOrThrow("CITY")),
                            hotel = getString(getColumnIndexOrThrow("HOTEL")),
                            image = getInt(getColumnIndexOrThrow("IMAGE")),
                            price = getInt(getColumnIndexOrThrow("PRICE"))
                        )
                    )
                } while (moveToNext())
            }
            close()
        }
        close()
        return items
    }
    fun getResortbyCountry(world: String): List<Resort> {
        val cursor = readableDatabase.query(TABLE_NAME_RESORTS,
            arrayOf("id", "WORLD", "COUNTRY", "CITY", "HOTEL", "IMAGE", "PRICE"), "WORLD = ?", arrayOf(world), null, null, null)
        val items = mutableListOf<Resort>()
        with(cursor) {
            if (moveToFirst()) {
                do {
                    items.add(
                        Resort(
                            id = getInt(getColumnIndexOrThrow("id")),
                            world = getString(getColumnIndexOrThrow("WORLD")),
                            country = getString(getColumnIndexOrThrow("COUNTRY")),
                            city = getString(getColumnIndexOrThrow("CITY")),
                            hotel = getString(getColumnIndexOrThrow("HOTEL")),
                            image = getInt(getColumnIndexOrThrow("IMAGE")),
                            price = getInt(getColumnIndexOrThrow("PRICE"))
                        )
                    )
                } while (moveToNext())
            }
            close()
        }
        close()
        return items
    }
    fun insertResortFavourites(idResort: Int, accountId: Int) {
        writableDatabase.insert(
            TABLE_NAME_FAVOURITES,
            null,
            ContentValues().apply {
                put("id_RESORT", idResort)
                put("ACCOUNT_ID", accountId)
            }
        )
    }
    fun getResortsFavourites(userId: Int): List<ResortSpecial> {
        val cursor = readableDatabase.query(TABLE_NAME_FAVOURITES,
            arrayOf("id", "id_RESORT", "ACCOUNT_ID"), "ACCOUNT_ID = ?", arrayOf(userId.toString()), null, null, null)
        val items = mutableListOf<ResortSpecial>()
        with(cursor) {
            if (moveToFirst()) {
                do {
                    items.add(
                        ResortSpecial(
                            getInt(getColumnIndexOrThrow("id")),
                            getInt(getColumnIndexOrThrow("id_RESORT")),
                            getInt(getColumnIndexOrThrow("ACCOUNT_ID"))
                        )
                    )
                } while (moveToNext())
            }
            close()
        }
        close()
        return items
    }
    fun deleteResortFavourites(id: Int) {
        writableDatabase.delete(
            TABLE_NAME_FAVOURITES,
            "id = ?",
            arrayOf(id.toString())
        )
    }
    fun insertResortBook(idResort: Int, accountId: Int, dataUser: BookingDataUser) {
        writableDatabase.insert(
            TABLE_NAME_BOOK,
            null,
            ContentValues().apply {
                put("id_RESORT", idResort)
                put("ACCOUNT_ID", accountId)
                put("FIRSTNAME", dataUser.name)
                put("LASTNAME", dataUser.surname)
                put("PATRONYMIC", dataUser.patronymic)
                put("AGE", dataUser.age)
                put("PASSPORT_NUMBER", dataUser.passportNumber)
                put("PASSPORT_SERIES", dataUser.passportSeries)
            }
        )
    }
    fun getResortsBook(accountId: Int): List<ResortSpecial> {
        val cursor = readableDatabase.query(TABLE_NAME_BOOK,
            arrayOf("id", "id_RESORT", "ACCOUNT_ID"), "ACCOUNT_ID = ?", arrayOf(accountId.toString()), null, null, null)
        val items = mutableListOf<ResortSpecial>()
        with(cursor) {
            if (moveToFirst()) {
                do {
                    items.add(
                        ResortSpecial(
                            getInt(getColumnIndexOrThrow("id")),
                            getInt(getColumnIndexOrThrow("id_RESORT")),
                            getInt(getColumnIndexOrThrow("ACCOUNT_ID"))
                        )
                    )
                } while (moveToNext())
            }
            close()
        }
        close()
        return items
    }
    fun getResortsBooking(accountId: Int): List<BookingDataUser> {
        val cursor = readableDatabase.query(TABLE_NAME_BOOK,
            arrayOf("id", "id_RESORT", "ACCOUNT_ID", "FIRSTNAME", "LASTNAME", "PATRONYMIC", "AGE", "PASSPORT_NUMBER", "PASSPORT_SERIES"), "ACCOUNT_ID = ?", arrayOf(accountId.toString()), null, null, null)
        var books = mutableListOf<BookingDataUser>()
        with(cursor) {
            if (moveToFirst()) {
                do {
                    books.add(
                        BookingDataUser(
                            name = getString(getColumnIndexOrThrow("FIRSTNAME")),
                            surname = getString(getColumnIndexOrThrow("LASTNAME")),
                            patronymic = getString(getColumnIndexOrThrow("PATRONYMIC")),
                            age = getInt(getColumnIndexOrThrow("AGE")),
                            passportNumber = getInt(getColumnIndexOrThrow("PASSPORT_NUMBER")),
                            passportSeries = getInt(getColumnIndexOrThrow("PASSPORT_SERIES")),
                        )
                    )
                } while (moveToNext())
            }
            close()
        }
        close()
        return books
    }
    fun deleteResortBook(id: Int) {
        writableDatabase.delete(
            TABLE_NAME_BOOK,
            "id = ?",
            arrayOf(id.toString())
        )
    }
    fun insertAccount(account: Account) {
        writableDatabase.insert(
            TABLE_NAME_ACCOUNT,
            null,
            ContentValues().apply {
                put("id", account.id)
                put("LOGIN", account.login)
                put("PASSWORD", account.password)
            }
        )
    }
    fun getAccountAuth(email: String): Account? {
        val cursor = readableDatabase.query(TABLE_NAME_ACCOUNT,
            arrayOf("id", "LOGIN", "PASSWORD"), "LOGIN = ?", arrayOf(email), null, null, null)
        var user: Account? = null
        with(cursor) {
            if (moveToFirst()) {
                user = Account(
                    id = getInt(getColumnIndexOrThrow("id")),
                    login = getString(getColumnIndexOrThrow("LOGIN")),
                    password = getString(getColumnIndexOrThrow("PASSWORD"))
                )
            }
            close()
        }
        close()
        return user
    }
    fun insertUser(user: User) {
        writableDatabase.insert(
            TABLE_NAME_USERS,
            null,
            ContentValues().apply {
                put("ACCOUNT_ID", user.accountId)
                put("FIRSTNAME", user.name)
                put("LASTNAME", user.surname)
            }
        )
    }
    fun getUser(accountId: Int): User? {
        val curcor = readableDatabase.query(
            TABLE_NAME_USERS,
            arrayOf("ACCOUNT_ID", "FIRSTNAME", "LASTNAME"), "ACCOUNT_ID = ?" , arrayOf(accountId.toString()), null, null, null
        )
        var user: User? = null
        with(curcor) {
            if (moveToFirst()) {
                user = User(
                    accountId = getInt(getColumnIndexOrThrow("ACCOUNT_ID")),
                    name = getString(getColumnIndexOrThrow("FIRSTNAME")),
                    surname = getString(getColumnIndexOrThrow("LASTNAME"))
                )
            }
            close()
        }
        close()
        return user
    }
    fun insertDataUser(dataUser: DataUser) {
        writableDatabase.insert(
            TABLE_NAME_PERSONAL_DATA,
            null,
            ContentValues().apply {
                put("ACCOUNT_ID", dataUser.accountId)
                put("PATRONYMIC", dataUser.patronymic)
                put("AGE", dataUser.age)
                put("PASSPORT_NUMBER", dataUser.passportNumber)
                put("PASSPORT_SERIES", dataUser.passportSeries)
            }
        )
    }
    fun getDataUser(id: Int): DataUser? {
        val cursor = readableDatabase.query(TABLE_NAME_PERSONAL_DATA,
            arrayOf("ACCOUNT_ID", "PATRONYMIC", "AGE", "PASSPORT_NUMBER", "PASSPORT_SERIES"), "ACCOUNT_ID = ?", arrayOf(id.toString()), null, null, null)
        var dataUser: DataUser? = null
        with(cursor) {
            if (moveToFirst()) {
                dataUser = DataUser(
                    accountId = getInt(getColumnIndexOrThrow("ACCOUNT_ID")),
                    patronymic = getString(getColumnIndexOrThrow("PATRONYMIC")),
                    age = getInt(getColumnIndexOrThrow("AGE")),
                    passportNumber = getInt(getColumnIndexOrThrow("PASSPORT_NUMBER")),
                    passportSeries = getInt(getColumnIndexOrThrow("PASSPORT_SERIES"))
                )
            }
            close()
        }
        close()
        return dataUser
    }
    fun deleteDataUser(id: Int) {
        writableDatabase.delete(
            TABLE_NAME_PERSONAL_DATA,
            "ACCOUNT_ID = ?",
            arrayOf(id.toString())
        )
    }
}