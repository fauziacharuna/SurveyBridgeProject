package com.example.fauziachmadharuna.surveybridgeproject.model

class EngineerModel {
    var id : String?=null
    var name : String?=null
    var jabatan : String?=null

    constructor(name: String?, jabatan: String?) {
        this.name = name
        this.jabatan = jabatan
    }

    constructor(id: String?, name: String?, jabatan: String?) {
        this.id = id
        this.name = name
        this.jabatan = jabatan
    }

    constructor()

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
//        result.put("Id", id!!)
        result.put("NameEngineer",name!!)
        result.put("Jabatan",jabatan!!)

        return result
    }
}