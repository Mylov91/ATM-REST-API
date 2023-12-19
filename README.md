# AtmApplication
REST API по работе с банковским счетом. Может использоваться банкоматом, веб-приложением или мобильным приложением Интернет-банка.

## Содержание
- [Технологии](#технологии)
- [Возможности](#возможности)
- [Структура БД](#структура-бд)
- [Автор проекта](#автор-проекта)

## Технологии
- [Java 17](https://www.java.com/ru/)
- [Spring Boot 3.2.0](https://spring.io/projects/spring-boot)
- [PostgreSQL 16.1](https://www.postgresql.org)

## Возможности
В текущей версии проекта доступны следующие виды взаимодействия с пользователями банка, хранящимися в БД:
- [Отображение баланса пользователя](#отображение-баланса-пользователя)
- [Снятие средств со счета пользователя](#снятие-средств-со-счета-пользователя)
- [Внесение средств на счет пользователя](#внесение-средств-на-счет-пользователя)


### Отображение баланса пользователя
Запрос баланса осуществляется путем http запроса на URL **/show** и передачи id пользователя в качестве параметра. 
Пример:
```
http://localhost:8080/show?id=3
```

### Снятие средств со счета пользователя
Снятие средств осуществляется путем http запроса на URL **/take** и передачи в запрос следующий параметров:
1. Идентификатор пользователя в БД (id=value)
2. Сумма снятия (amount=value)

Пример:
```
http://localhost:8080/take?id=3&amount=500
```

### Внесение средств на счет пользователя
Внесение средств осуществляется путем http запроса на URL **/put** и передачи в запрос следующий параметров:
1. Идентификатор пользователя в БД (id=value)
2. Сумма внесения (amount=value)

Пример:
```
http://localhost:8080/put?id=3&amount=500
```

## Структура БД
В структуре проекта по пути `src/main/resources/init.sql` находится скрипт для инициализации БД.
БД имеет следующую структуру:

![img](https://i.ibb.co/h77w2PK/2023-12-19-16-46-29.png)

## Автор проекта
  [Мылов Сергей](https://github.com/Mylov91) — Java-developer (Mylov91@yandex.ru)