# Playlist Maker Android

Учебный проект по созданию мобильного приложения для управления плейлистами.

---

## Sprint 5

### Что реализовано в Спринте 5:
- Подготовлена архитектура приложения на базе Clean Architecture: разделение на пакеты `data`, `domain`, `ui`, `creator`.
- Создан эмулятор сервера (`Storage`) с локальным списком треков и функцией `search()`.
- Реализован слой **Data**: DTO, модели ответа, запросы, NetworkClient, RepositoryImpl.
- Реализован слой **Domain** с интерфейсами `NetworkClient` и `TracksRepository`.
- Репозиторий возвращает обработанные данные (`Track`) и готов к подключению в будущей `SearchViewModel`.
- Частично применён принцип YAGNI — исключены ненужные Interactor-слои.
- Настроено создание зависимостей (Storage → NetworkClient → Repository) в пакете `creator`.
- Обновлена структура проекта, `MainActivity` перенесён в `ui/activity`, исправлен путь в манифесте.

## Sprint 4

### Что реализовано в Спринте 4:
- Реализованы экраны: SearchScreen (верстка, очистка запроса) и SettingsScreen (шеринги, email intent, открытие оферты).
- Навигация: добавлен `PlaylistHost`/`Screens` (Navigation Compose), все переходы между Main / Search / Settings через `NavController`.
- Компоненты: вынесены переиспользуемые элементы `RowItem`, `SettingsRowItem`, `SettingsSwitchRowItem`, `CustomSwitch`.
- Перенесена логика, связанная со строками и интентами, в `ui/helpers/` (`SettingsStrings.kt`, `SettingsActions.kt`).
- Добавлены анимированные переходы между экранами (Accompanist Navigation Animation).

## Спринт 3

### Что реализовано в Спринте 3:

**Задача 1: Главный экран**
- Создан MainActivity
- Экран сверстан по дизайн-макету из Figma
- Реализованы обработчики нажатий кнопок

**Задача 2: Навигация**
- Созданы SearchActivity и SettingsActivity
- Настроены переходы между экранами
- Убраны Toast-сообщения, добавлена реальная навигация

---

## Чек-листы самопроверки:
- [checklists/sprint-3.md](checklists/sprint-3.md)
- [checklists/sprint-4.md](checklists/sprint-4.md)
- [checklists/sprint-5.md](checklists/sprint-5.md)

---

## Технологии:
- Android Studio
- Kotlin
- Jetpack Compose
- Git/GitHub

---

## История версий:
- **v3.0** - Спринт 3: Главный экран и навигация
- **v4.0** - Спринт 4: Экраны поиска и настроек + навигация