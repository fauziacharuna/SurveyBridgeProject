package com.example.fauziachmadharuna.surveybridgeproject.model

class SurveyModel {
    var id: String? = null
    var bridgeName: String? = null
    var bridgeLocation: String? = null
    var surveyorName: String? = null
    var sistem : String?=null
    var komponen : String?=null
    var subKomponen : String?=null
    var bahan : String?=null
    var kerusakan : String?=null
    var tags : List<String>? =null


    constructor() {}

    constructor(id: String, bridgeName: String, bridgeLocation: String, surveyorName: String, sistem: String, komponen : String, subKomponen : String, bahan : String, kerusakan : String,tags:List<String>) {
        this.id = id
        this.bridgeName = bridgeName
        this.bridgeLocation = bridgeLocation
        this.surveyorName = surveyorName
        this.sistem = sistem
        this.komponen=komponen
        this.subKomponen=subKomponen
        this.bahan=bahan
        this.kerusakan=kerusakan
        this.tags=tags
    }

    constructor(bridgeName: String, bridgeLocation: String, surveyorName: String, sistem: String,komponen : String, subKomponen : String, bahan : String, kerusakan : String, tags: List<String>) {

        this.bridgeName = bridgeName
        this.bridgeLocation = bridgeLocation
        this.surveyorName = surveyorName
        this.sistem=sistem
        this.komponen=komponen
        this.subKomponen=subKomponen
        this.bahan=bahan
        this.kerusakan=kerusakan
        this.tags=tags
    }
    fun toMap() : Map<String, Any>{
        val result = HashMap<String, Any>()
        result.put("BridgeName", bridgeName!!)
        result.put("BridgeLocation",bridgeLocation!!)
        result.put("SurveyorName",surveyorName!!)
        result.put("Sistem",sistem!!)
        result.put("Komponen",komponen!!)
        result.put("SubKomponen", subKomponen!!)
        result.put("Bahan", bahan!!)
        result.put("Kerusakan", kerusakan!!)
        result.put("Tags",tags!!)

        return result
    }
}