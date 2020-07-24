package com.example.servicito.domains.buildings.models.pojo

class LandlordInfo(var id: Long, var name: String, var phone: String) {

    override fun toString(): String {
        return "LandlordInfo{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}'
    }

}
