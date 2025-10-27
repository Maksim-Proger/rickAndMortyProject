package com.example.project.data.models

import com.example.project.domain.models.Location
import com.example.project.domain.models.Origin

data class ApiCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: Origin?,
    val location: Location?,
    val image: String?,
    val url: String?,
    val created: String?
)

data class ApiOrigin(
    val name: String,
    val url: String
)

data class ApiLocation(
    val name: String,
    val url: String
)
