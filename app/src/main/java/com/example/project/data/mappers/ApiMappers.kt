package com.example.project.data.mappers

import com.example.project.data.local.CharacterEntity
import com.example.project.data.models.ApiCharacter
import com.example.project.data.models.ApiCharacterList
import com.example.project.domain.models.DomainCharacterList
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

fun ApiCharacterList.toDomain(): DomainCharacterList {
    return DomainCharacterList(
        info = info,
        results = results.map { it.toDomain() }
    )
}
