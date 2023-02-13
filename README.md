Para correr la api seguir los siguientes pasos:

Clonar proyecto desde el repositorio.

Descargar las dependencias del build.gradle.

El proyecto fue desarrollado con flyway para la creacion de tablas y carga de datos iniciales, dentro del archivo: /resources/db/migration/script.sql se encuentran el script para creacion de tablas y carga de datos iniciales.

El proyecto se despliega en el puerto 8080 deacuerdo a la configuración en el archivo application.yml.

El contextPath de la app esta configurado con el valor /api.

**Para correr la APi dockerizada seguir los **

./gradlew clean build

docker build -t pichincha-service .

docker run -p 8080:8080 pichincha-service

Para ingresar a la consola de h2: http://localhost:8080/api/h2-console Datos para configurar conexión a la base de datos: Driver Class: org.h2.Driver JDBC URL: jdbc:h2:mem:nisum username: admin / password: admin
