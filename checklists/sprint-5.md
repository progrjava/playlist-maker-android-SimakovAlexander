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
- [ ] Проект разделён на пакеты `data`, `domain`, `ui`, `creator`.
- [ ] `MainActivity` перенесён в `ui/activity`.
- [ ] В `AndroidManifest.xml` обновлён путь к `MainActivity`.

### Clean Architecture
- [ ] Слой **Data** не зависит от UI.
- [ ] Слой **Domain** не зависит ни от UI, ни от Data.
- [ ] creator-пакет содержит объекты для создания зависимостей (Storage, NetworkClient, Repository).

### Эмулятор сервера (Storage)
- [ ] Создан класс `Storage` со списком треков.
- [ ] Реализована функция `search(request: String)` с фильтрацией по lowercase.
- [ ] `Storage` размещён в `creator`.

### Data layer
- [ ] Созданы классы: `BaseResponse`, `TrackDto`, `TracksSearchRequest`, `TracksSearchResponse`.
- [ ] Реализован `RetrofitNetworkClient`, обращающийся к `Storage`.
- [ ] `RetrofitNetworkClient.doRequest()` возвращает корректный `TracksSearchResponse` c `resultCode = 200`.
- [ ] Реализован `TracksRepositoryImpl`:
    - [ ] вызывает `doRequest()`
    - [ ] эмулирует задержку `delay(1000)`
    - [ ] конвертирует `TrackDto` → `Track`
    - [ ] возвращает пустой список при ошибке

### Domain layer
- [ ] Созданы интерфейсы:
    - [ ] `NetworkClient`
    - [ ] `TracksRepository`
- [ ] Логика поиска заложена на будущее, без UI.
- [ ] Interactor не создан — решение осознанное (YAGNI).

### Creator (DI)
- [ ] Созданы фабрики / объекты:
    - [ ] `Storage()`
    - [ ] `NetworkClient(storage)`
    - [ ] `TracksRepositoryImpl(networkClient)`
- [ ] Все зависимости создаются в creator-пакете.

### Готовность к UI в Sprint 6
- [ ] `TracksRepository` готов к использованию во `SearchViewModel`.
- [ ] Нет утечек зависимостей между слоями.

---
