# Чек-лист самопроверки — Sprint 5

## Описание задач
В этом спринте реализована архитектурная основа поиска:
- Разделение проекта на слои **Data / Domain / UI / creator** (Clean Architecture)
- Подготовка структуры пакетов
- Перенос `MainActivity` в `ui/activity` и обновление пути в манифесте
- Создание эмулятора сервера (`Storage`)
- Реализация слоя **Data**: DTO, Response, NetworkClient, RepositoryImpl
- Реализация слоя **Domain**: интерфейсы `NetworkClient` и `TracksRepository`
- Исключение ненужных сущностей (Interactor) согласно принципу **YAGNI**
- Подготовка репозитория для будущей `SearchViewModel`

---

## Функциональные и структурные проверки

### Структура проекта
- [x] Проект разделён на пакеты `data`, `domain`, `ui`, `creator`.
- [x] `MainActivity` перенесён в `ui/activity`.
- [x] В `AndroidManifest.xml` обновлён путь к `MainActivity`.

### Clean Architecture
- [x] Слой **Data** не зависит от UI.
- [x] Слой **Domain** не зависит ни от UI, ни от Data.
- [x] creator-пакет содержит объекты для создания зависимостей (Storage, NetworkClient, Repository).

### Эмулятор сервера (Storage)
- [x] Создан класс `Storage` со списком треков.
- [x] Реализована функция `search(request: String)` с фильтрацией по lowercase.
- [x] `Storage` размещён в `creator`.

### Data layer
- [x] Созданы классы: `BaseResponse`, `TrackDto`, `TracksSearchRequest`, `TracksSearchResponse`.
- [x] Реализован `RetrofitNetworkClient`, обращающийся к `Storage`.
- [x] `RetrofitNetworkClient.doRequest()` возвращает корректный `TracksSearchResponse` c `resultCode = 200`.
- [x] Реализован `TracksRepositoryImpl`:
    - [x] вызывает `doRequest()`
    - [x] эмулирует задержку `delay(1000)`
    - [x] конвертирует `TrackDto` → `Track`
    - [x] возвращает пустой список при ошибке

### Domain layer
- [x] Созданы интерфейсы:
    - [x] `NetworkClient`
    - [x] `TracksRepository`
- [x] Логика поиска заложена на будущее, без UI.
- [x] Interactor не создан — решение осознанное (YAGNI).

### Creator (DI)
- [x] Созданы фабрики / объекты:
    - [x] `Storage()`
    - [x] `NetworkClient(storage)`
    - [x] `TracksRepositoryImpl(networkClient)`
- [x] Все зависимости создаются в creator-пакете.

### Готовность к UI в Sprint 6
- [x] `TracksRepository` готов к использованию во `SearchViewModel`.
- [x] Нет утечек зависимостей между слоями.

---
