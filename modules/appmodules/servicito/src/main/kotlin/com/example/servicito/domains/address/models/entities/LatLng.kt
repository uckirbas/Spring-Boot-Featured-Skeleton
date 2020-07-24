package com.example.servicito.domains.address.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.example.servicito.domains.buildings.models.pojo.LatLngMeta
import com.example.common.exceptions.notfound.NotFoundException
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable


@Embeddable
class LatLng() {
    @Column(name = "latitude")
    var lat: Double = 0.toDouble()
    @Column(name = "longitude")
    var lng: Double = 0.toDouble()

    constructor(lat: Double, lng: Double) : this() {
        this.lat = lat
        this.lng = lng
    }

    @JsonIgnore
    fun googleMapLink(): String? {
        return "https://www.google.com/maps/place/" + this.lat.toString() + "," + this.lng.toString()
    }

    companion object {

        @JvmStatic
        fun parse(areaName: String): Map<String, Double> {
            val aName = areaName.replace(" ", "%20")
            val url = URL("https://maps.googleapis.com/maps/api/geocode/json?address=$aName,bangladesh&sensor=true&key=AIzaSyANZfglkpcpj5QcUHw-zPGloYnpeE4qiMY")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val br: BufferedReader
            br = if (connection.responseCode == 200) BufferedReader(InputStreamReader(connection.inputStream)) else BufferedReader(InputStreamReader(connection.errorStream))
            var output: String
            val sb = StringBuilder()
            while (br.readLine().also { output = it } != null) {
                sb.append(output)
            }
            br.close()
            output = sb.toString()
            return parseLatLng(output)
        }

        @JvmStatic
        private fun parseLatLng(output: String): Map<String, Double> {
            val jsonArray: JSONArray = JSONObject(output).getJSONArray("results")
            val jsonObject: JSONObject
            if (jsonArray.length() < 1) throw NotFoundException("Can not parse LatLng, area isn't provided or empty!")
            jsonObject = (jsonArray.get(0) as JSONObject).getJSONObject("geometry").getJSONObject("location")
            val latLng: MutableMap<String, Double> = HashMap()
            latLng["lat"] = jsonObject.getDouble("lat")
            latLng["lng"] = jsonObject.getDouble("lng")
            return latLng
        }
    }

}
