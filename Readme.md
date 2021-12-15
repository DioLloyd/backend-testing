Тестирование с помощью Retrofit
===

* Тестируемый сервис [**Market**](http://80.78.248.82:8189/market/)
* Документация API [**Swagger**](http://80.78.248.82:8189/market/swagger-ui.html)
* Подготовка и запуск тестов:
  * Генерация ORM `mvn mybatis-generator:generate`
  * Запуск тестов `mvn clean test allure:serve`

___

## Используемые библиотеки:

[**Allure**](https://docs.qameta.io/allure/) – Создание тестовых отчётов

[**Junit5**](https://junit.org/junit5/docs/current/user-guide/) – Тестовая платформа

[**Retrofit**](https://square.github.io/retrofit/) – API клиент

[**OkHttp3**](https://square.github.io/okhttp/) – HTTP клиент

[**Jackson**](https://github.com/FasterXML/jackson-docs) – Сериализация/десериализация JSON

[**Lombok**](https://projectlombok.org/setup/maven) – Автогенерация шаблонных методов

[**JavaFaker**](https://github.com/DiUS/java-faker) – Генерация случайных значений

[**Hamcrest**](http://hamcrest.org/JavaHamcrest/) – Библиотека matcher-ов

[**PostgreSQL**](https://jdbc.postgresql.org/) – JDBC драйвер

[**MyBatis**](https://mybatis.org/mybatis-3/getting-started.html) – ORM фреймворк для работы с БД

[**MyBatis Generator**](https://mybatis.org/generator/running/runningWithJava.html) – Генерация сущностей ORM
