# Rick & Morty Explorer

![Version](https://img.shields.io/badge/version-1.0-blue)
![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Platform](https://img.shields.io/badge/platform-Android-lightgrey?logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-purple?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-orange?logo=jetpackcompose)
![License](https://img.shields.io/badge/license-MIT-yellow)
Android-приложение для просмотра персонажей из вселенной **Rick and Morty**.  

---

## 🚀 Основные возможности

- 📜 **Список персонажей** с бесконечной прокруткой (Paging 3 + RemoteMediator).
- 🔍 **Поиск по имени** с дебаунсом и подгрузкой из локальной БД.
- 🧩 **Фильтрация** по имени, статусу и гендеру.
- 📶 **Офлайн-доступ** — данные автоматически кэшируются в Room, и доступны без сети.
- 👤 **Детальный экран персонажа** с fallback-логикой (API → БД).
- 🎨 Полная поддержка **Material 3** и светлой/тёмной темы.

---

## 🏗️ Архитектура проекта

Проект построен по принципам **Clean Architecture** и разделён на модули:

