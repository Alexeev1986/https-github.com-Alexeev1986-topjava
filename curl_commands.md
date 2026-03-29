# Curl команды для тестирования MealRestController

## Базовые настройки
- **Base URL:** `http://localhost:8080/topjava/rest/meals`
- **Аутентификация:** Basic Auth
    - Username: `user@yandex.ru`
    - Password: `password`

---

## 1. GET /rest/meals - получить все записи
```bash
curl -X GET "http://localhost:8080/topjava/rest/meals" -u user@yandex.ru:password
```
**Результат:** 200 OK, возвращает список MealTo

---

## 2. GET /rest/meals/{id} - получить запись по ID
```bash
curl -X GET "http://localhost:8080/topjava/rest/meals/100003" -u user@yandex.ru:password
```
**Результат:** 200 OK, возвращает Meal

---

## 3. POST /rest/meals - создать новую запись
```bash
curl -X POST "http://localhost:8080/topjava/rest/meals" \
  -u user@yandex.ru:password \
  -H "Content-Type: application/json" \
  -d "{\"dateTime\":\"2026-03-28T20:00:00\",\"description\":\"Тестовый ужин\",\"calories\":550}"
```
**Результат:** 201 Created, возвращает созданный Meal с новым ID

---

## 4. PUT /rest/meals/{id} - обновить запись
```bash
curl -X PUT "http://localhost:8080/topjava/rest/meals/100003" \
  -u user@yandex.ru:password \
  -H "Content-Type: application/json" \
  -d "{\"id\":100003,\"dateTime\":\"2026-03-28T21:00:00\",\"description\":\"Обновленный завтрак\",\"calories\":480}"
```
**Результат:** 204 No Content

---

## 5. DELETE /rest/meals/{id} - удалить запись
```bash
curl -X DELETE "http://localhost:8080/topjava/rest/meals/100010" -u user@yandex.ru:password
```
**Результат:** 500 Internal Server Error (NotFoundException, если ID не существует)

---

## 6. GET /rest/meals/filter - фильтр по дате и времени
```bash
curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-31" -u user@yandex.ru:password
```
**Результат:** 200 OK, возвращает отфильтрованный список MealTo

---

## 7. GET /rest/meals/filter-by-datetime - фильтр по DateTime
```bash
curl -X GET "http://localhost:8080/topjava/rest/meals/filter-by-datetime?startDateTime=2020-01-30T00:00:00&endDateTime=2020-01-31T23:59:59" -u user@yandex.ru:password
```
**Результат:** 200 OK, возвращает отфильтрованный список MealTo

---

## Примечания
- Для DELETE запроса с несуществующим ID возвращается 500 (NotFoundException)
- Все GET, POST и PUT запросы работают корректно
- POST создает новую запись с автоматически сгенерированным ID