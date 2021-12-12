Тестирование с помощью Retrofit
===

* Тестируемый сервис [**Market**](http://80.78.248.82:8189/market/)

* Документация API [**Swagger**](http://80.78.248.82:8189/market/swagger-ui.html)

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
___

## Подключение Allure:

```xml

<properties>
    <aspectj.version>1.9.6</aspectj.version>
    <allure-maven.version>2.14.0</allure-maven.version>
</properties>
```

```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>${maven.compiler.source}</source>
                <target>${maven.compiler.target}</target>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>
            <configuration>
                <systemPropertyVariables>
                    <allure.results.directory>target/allure-results</allure.results.directory>
                </systemPropertyVariables>

                <testFailureIgnore>true</testFailureIgnore>
                <argLine>
                    -Dfile.encoding=UTF-8
                    -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
                </argLine>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.aspectj</groupId>
                    <artifactId>aspectjweaver</artifactId>
                    <version>${aspectj.version}</version>
                </dependency>
            </dependencies>
        </plugin>
        <plugin>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-maven</artifactId>
            <version>2.10.0</version>
            <configuration>
                <reportVersion>2.14.0</reportVersion>
            </configuration>
        </plugin>
    </plugins>
</build>
```

```xml

<dependencies>
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-junit5</artifactId>
        <version>${allure-maven.version}</version>
    </dependency>
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-okhttp3</artifactId>
        <version>${allure-maven.version}</version>
    </dependency>
</dependencies>
```
