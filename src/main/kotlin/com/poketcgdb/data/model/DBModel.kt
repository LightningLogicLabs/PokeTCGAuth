package com.poketcgdb.data.model

interface DBModel<DBM, DM> {

    fun mapToDomain(): DM
}