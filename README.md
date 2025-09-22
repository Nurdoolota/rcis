# Лабораторная работа 4: Работа с базами данных в Spring Framework

## Установка и настройка

### 1. Установка PostgreSQL
1. Скачайте и установите PostgreSQL: https://www.postgresql.org/download/.
2. Запустите сервер PostgreSQL (по умолчанию порт 5432).
3. Создайте пользователя и базу данных:
   ```bash
   psql -U postgres
   CREATE USER your_username WITH PASSWORD 'password';
   CREATE DATABASE tvstore;
   GRANT ALL PRIVILEGES ON DATABASE tvstore TO username;
   ```
4. Создайте таблицу вручную, если не используете автоматическое создание через Hibernate:
   ```sql
   CREATE TABLE tv (
       id SERIAL PRIMARY KEY,
       manufacturer VARCHAR(255) NOT NULL,
       model VARCHAR(255) NOT NULL,
       screenType VARCHAR(100),
       price DOUBLE PRECISION NOT NULL,
       quantity INTEGER NOT NULL
   );
   ```

### 2. Настройка проекта
1. **Склонируйте проект**
2. **Настройте `application.properties`** (в `src/main/java/resources`):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/tvstore
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```
   Замените `your_username` и `your_password` на ваши данные.

## Сборка и запуск
   ```bash
   mvn clean compile
   mvn spring-boot:run
   ```
```В консоли появится меню. Используйте цифры 1–6 для выбора действий.```
