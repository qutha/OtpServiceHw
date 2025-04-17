# OTP Service

Сервис для подтверждения операций с помощью временных OTP-кодов.

## Особенности

- JWT-аутентификация
- Разграничение по ролям (`Admin` и `User`)
- Права администратора:
    - Изменение конфигурации OTP (длина кода и срок действия)
    - Получение списка пользователей
    - Удаление пользователей и кодов
- Права пользователя:
    - Генерация OTP-кода
    - Получение кода по Email/SMS/Telegram/файл
    - Проверка кода
- Автоматическая установка статуса `EXPIRED` с помощью планировщика
- Логирование всех операций
- Swagger UI

## Зависимости

- Java 23
- PostgreSQL 17
- Gradle 8.13
- SMPP эмулятор (для тестирования SMS)
- Telegram Bot Token (для работы с Telegram)
- Flyway (для миграций базы данных)

## Сборка и запуск

1. Заполните `application.yml` по шаблону `application-template.yml`:

- PostgreSQL:

```yml
datasource:
  url: jdbc:postgresql://localhost:5433/otp_db
  username: postgres
  password: postgres
  driver-class-name: org.postgresql.Driver
```

- SMTP:

```yml
  mail:
    host: smtp.example.com
    port: 587
    username: email@example.com
    password: password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
  email:
    from: email@example.com
```

- Telegram:

```yml
telegram:
  token: TOKEN
```

- JWT

```yml
jwt:
  secret: SECRET
  #  ms
  expiration: 86400000
```

- SMS

```yml
smpp:
  host: localhost
  port: 2775
  system-id: smppclient
  password: password
  system-type: OTP
  source-addr: OTPService
```

2. Убедитесь, что база данных `otp_db` создана.

3. Соберите проект:

```bash
./gradlew build
```

4. Запустите приложение:

```bash
./gradlew bootRun
```

## API

### Swagger UI

- **URL**: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

### Регистрация пользователя

```
POST /api/auth/register
```

```json
{
  "username": "user",
  "password": "password"
}
```

### Логин пользователя

```
POST /api/auth/login
```

```json
{
  "username": "user",
  "password": "password"
}
```

Ответ:

```json
{
  "message": "message",
  "token": "<token>"
}
```

### Генерация OTP-кода

```
POST /api/otp/generate
```

```json
{
  "operationId": "e50f1b47-cc08-4768-aba6-d94d43f8ddb3",
  "channel": "EMAIL",
  "recipient": "user@example.com"
}
```

### Проверка OTP-кода

```
POST /api/otp/validate
```

```json
{
  "operationId": "e50f1b47-cc08-4768-aba6-d94d43f8ddb3",
  "code": "123456"
}
```

### Получение пользователей

```
GET /api/admin/users
Authorization: Bearer <token>
```

### Удаление пользователя

```
DELETE /api/admin/users/{userId}
Authorization: Bearer <token>
```

### Изменение конфигурации OTP

```
PATCH /api/admin/config

Authorization: Bearer <token>
```

```json
{
  "codeLength": 6,
  "lifetime": 120
}
```
