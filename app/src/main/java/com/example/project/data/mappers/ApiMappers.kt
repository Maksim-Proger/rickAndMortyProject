package com.example.project.data.mappers

import com.example.project.data.local.CharacterEntity
import com.example.project.data.models.ApiCharacter
import com.example.project.data.models.ApiCharacterList
import com.example.project.data.models.ApiInfo
import com.example.project.data.models.ApiLocation
import com.example.project.data.models.ApiOrigin
import com.example.project.domain.models.DomainCharacterList
import com.example.project.domain.models.DomainInfo
import com.example.project.domain.models.DomainModelCharacter
import com.example.project.domain.models.Location
import com.example.project.domain.models.Origin
import com.google.gson.Gson

fun ApiCharacter.toDomain(): DomainModelCharacter {
    return DomainModelCharacter(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        image = image,
        url = url,
        created = created
    )
}

fun ApiInfo.toDomain(): DomainInfo {
    return DomainInfo(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )
}

fun ApiOrigin.toDomain(): Origin {
    return Origin(
        name = name,
        url = url
    )
}

fun ApiLocation.toDomain(): Location {
    return Location(
        name = name,
        url = url
    )
}

fun ApiCharacterList.toDomain(): DomainCharacterList {
    return DomainCharacterList(
        info = info.toDomain(),
        results = results.map { it.toDomain() }
    )
}

fun ApiCharacter.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        originJson = Gson().toJson(origin ?: ApiOrigin("", "")),
        locationJson = Gson().toJson(location ?: ApiLocation("", "")),
        image = image,
        url = url,
        created = created
    )
}

fun CharacterEntity.toDomain(): DomainModelCharacter {
    val originObj: ApiOrigin = Gson().fromJson(originJson, ApiOrigin::class.java)
    val locationObj: ApiLocation = Gson().fromJson(locationJson, ApiLocation::class.java)

    return DomainModelCharacter(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = originObj.toDomain(),
        location = locationObj.toDomain(),
        image = image,
        url = url,
        created = created
    )
}