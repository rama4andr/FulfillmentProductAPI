# API для управления продуктами

API для управления продуктами в различных центрах выполнения заказов (Fulfillment Centers), обеспечивающий поддержание актуальной информации о состоянии запасов и выполнении операций с продуктами.

## Технические детали

- **Язык**: Java 17
- **Фреймворк**: Spring Boot 3.3.5
- **База данных**: PostgreSQL
- **ORM**: Hibernate
- **Документация API**: Swagger UI

## Функциональность API

- **GET /products** - Получение всех продуктов.
- **GET /products/{id}** - Получение продукта по `id`.
- **POST /products** - Добавление нового продукта.
- **PUT /products/{id}** - Обновление продукта по `id`.
- **DELETE /products/{id}** - Удаление продукта по `id`.
- **GET /products/status/{status}** - Получение всех продуктов по указанному статусу.
- **GET /products/total-value** - Получение общей стоимости продуктов со статусом `Sellable` (или по переданному статусу через `@RequestParam`).

## Установка и настройка

### Требования
- **Java 17**
- **PostgreSQL**
- **Docker** и **Docker Compose**

### Шаги установки и запуска

1. **Клонируйте репозиторий проекта:**
```bash
git clone https://github.com/rama4andr/FulfillmentProductAPI.git
```

2. **Создайте файл .env для конфигурации переменных окружения:**

```env
POSTGRES_DB=productsdb
POSTGRES_USER=root
POSTGRES_PASSWORD=rootpass
HOST=postgres
PORT=5432
```
Замените `your_user` и `your_password` на ваши значения.

Эта команда развернёт контейнер PostgreSQL, доступный по порту 5432.

3. **Запуск приложения:**

- *Локальный запуск:*
   
Поднимите контейнер Docker c БД PostgreSQL при помощи команды:
```bash
docker run -d \
  --name postgres-container \
  -e POSTGRES_USER=${POSTGRES_USER} \
  -e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
  -e POSTGRES_DB=${POSTGRES_DB} \
  -p 5432:5432 \
  -v postgres-data:/var/lib/postgresql/data \
  postgres:13
```
Запустите приложение с помощью команды:
```mvn spring-boot:run```

- *Запуск через Docker:*
   
```bash
docker-compose up --build
```
  
5. **Документация API:** После запуска приложения вы можете просмотреть документацию API в Swagger UI по адресу:

```bash
http://localhost:8080/swagger-ui/index.html
```

#### В API присутствуют unit-тесты для проверки контроллеров, запустить можно с помощью команды:```mvn test```
  
