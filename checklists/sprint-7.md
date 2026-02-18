# Чек-лист самопроверки — Sprint 7

## Описание задач
В этом спринте реализованы два UI-компонента Jetpack Compose, которые пригодятся в дальнейшем проекте:
- `FloatingActionButton` — плавающая кнопка действия
- `ModalBottomSheet` — всплывающая панель снизу экрана

Также реализована логика взаимодействия этих компонентов: открытие `ModalBottomSheet` по нажатию на `FloatingActionButton`.

---

## Функциональные проверки

### Коллбэк поведения FloatingActionButton

- [ ] В `FloatButtonExample` добавлен параметр `callback: () -> Unit`.
- [ ] В `FloatingActionButton` передан `onClick = callback`.
- [ ] Превью работает и не ломается:
    - [ ] В `Preview` передаётся пустая лямбда.

### Логика открытия и закрытия BottomSheet

- [ ] В родительском компоненте создан стейт:
    - [ ] `var showBottomSheet by remember { mutableStateOf(false) }`
- [ ] При открытии панели устанавливается `showBottomSheet = true`.
- [ ] При закрытии панели вызывается `onDismissRequest`, который устанавливает `showBottomSheet = false`.
- [ ] Панель скрывается при клике вне области `ModalBottomSheet`.
- [ ] Панель скрывается при свайпе вниз.

---

### Итоговое объединение FloatingActionButton + ModalBottomSheet

- [ ] Реализован экран, на котором отображается `FloatingActionButton`.
- [ ] Реализован `ModalBottomSheet`, который появляется при нажатии на FAB.
- [ ] FAB и BottomSheet связаны через общий `state`.
- [ ] Поведение соответствует требованиям задания:
    - [ ] FAB открывает BottomSheet
    - [ ] BottomSheet закрывается кликом вне панели

---

## Структура проекта (организация файлов)

- [ ] Компонент `AddFloatingButton` добавлен в `playlist/components`.
- [ ] Компонент `PlaylistsBottomSheet` добавлен в `playlist/components`.
- [ ] Экран `PlaylistScreen` добавлен в `playlist` общего проекта.
- [ ] Структура добавлена временно и будет использоваться в будущих спринтах.

---

## Финальная проверка

- [ ] Приложение успешно компилируется.
- [ ] Приложение запускается без падений.
- [ ] На экране отображается `FloatingActionButton`.
- [ ] По нажатию на кнопку появляется `ModalBottomSheet`.
- [ ] При клике за пределами панели `ModalBottomSheet` скрывается.

---
