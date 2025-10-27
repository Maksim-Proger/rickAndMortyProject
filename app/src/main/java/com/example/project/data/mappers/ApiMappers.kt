package com.example.project.data.mappers

import com.example.project.data.local.CharacterEntity
import com.example.project.data.models.ApiCharacter
import com.example.project.data.models.ApiCharacterList
import com.example.project.data.models.ApiInfo
import com.example.project.domain.models.DomainCharacterList
import com.example.project.domain.models.DomainInfo
import com.example.project.domain.models.DomainModelCharacter

fun ApiCharacter.toDomain(): DomainModelCharacter {
    return DomainModelCharacter(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}

fun ApiCharacter.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}

fun CharacterEntity.toDomain(): DomainModelCharacter {
    return DomainModelCharacter(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
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

fun DomainInfo.toData(): ApiInfo {
    return ApiInfo(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )
}

fun ApiCharacterList.toDomain(): DomainCharacterList {
    return DomainCharacterList(
        info = info.toDomain(),
        results = results.map { it.toDomain() }
    )
}
