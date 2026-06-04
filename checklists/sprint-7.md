# Чек-лист самопроверки — Sprint 7

## Описание задач
В этом спринте реализованы два UI-компонента Jetpack Compose, которые пригодятся в дальнейшем проекте:
- `FloatingActionButton` — плавающая кнопка действия
- `ModalBottomSheet` — всплывающая панель снизу экрана

Также реализована логика взаимодействия этих компонентов: открытие `ModalBottomSheet` по нажатию на `FloatingActionButton`.

---

## Функциональные проверки

### Коллбэк поведения FloatingActionButton

- [x] В `FloatButtonExample` добавлен параметр `callback: () -> Unit`.
- [x] В `FloatingActionButton` передан `onClick = callback`.
- [x] Превью работает и не ломается:
    - [x] В `Preview` передаётся пустая лямбда.

### Логика открытия и закрытия BottomSheet

- [x] В родительском компоненте создан стейт:
- [x] `var showBottomSheet by remember { mutableStateOf(false) }`
- [x] При открытии панели устанавливается `showBottomSheet = true`.
- [x] При закрытии панели вызывается `onDismissRequest`, который устанавливает `showBottomSheet = false`.
- [x] Панель скрывается при клике вне области `ModalBottomSheet`.
- [x] Панель скрывается при свайпе вниз.

---

### Итоговое объединение FloatingActionButton + ModalBottomSheet

- [x] Реализован экран, на котором отображается `FloatingActionButton`.
- [x] Реализован `ModalBottomSheet`, который появляется при нажатии на FAB.
- [x] FAB и BottomSheet связаны через общий `state`.
- [x] Поведение соответствует требованиям задания:
    - [x] FAB открывает BottomSheet
    - [x] BottomSheet закрывается кликом вне панели

---

## Структура проекта (организация файлов)

- [x] Компонент `AddFloatingButton` добавлен в `playlist/components`.
- [x] Компонент `PlaylistsBottomSheet` добавлен в `playlist/components`.
- [x] Экран `PlaylistScreen` добавлен в `playlist` общего проекта.
- [x] Структура добавлена временно и будет использоваться в будущих спринтах.

---

## Финальная проверка

- [x] Приложение успешно компилируется.
- [x] Приложение запускается без падений.
- [x] На экране отображается `FloatingActionButton`.
- [x] По нажатию на кнопку появляется `ModalBottomSheet`.
- [x] При клике за пределами панели `ModalBottomSheet` скрывается.

---
