# Growth Hit

_Se implementa una RestAPI con Spring-boot, RabbitMQ y MySQL_

## Comenzando 馃殌

_Estas instrucciones te permitir谩n obtener una copia del proyecto en funcionamiento en tu m谩quina local para prop贸sitos de desarrollo y pruebas._

### Pre-requisitos 馃搶

_Para el uso correcto de esta implementaci贸n es necesario tener pre-instalado el JDK de Java, Maven, MySQL Server y RabbitMQ Server._
_Para el caso de instalarse en Windows, es necesario tener dadas de alta las variables de entorno en el sistema, para su correcta ejecuci贸n._

* [JDK Java](https://www.oracle.com/mx/java/technologies/javase-downloads.html) - El lenguaje de programaci贸n usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [MySQL Server](https://dev.mysql.com/downloads/) - Manejador de base de datos
* [RabbitMQ Server](https://www.rabbitmq.com/) - Broker de mensajer铆a de c贸digo abierto

_Para saber como realizar la instalaci贸n de estos pre-requisitos dirigirse a sus p谩ginas correspondientes y seguir la informaci贸n de la documentaci贸n._

### Pre-Instalaci贸n 馃敡

_Una vez tenemos instalados todos los pre-requisitos y habiendo clonado el proycto a nuestro repositorio local, haremos una configuraci贸n en MySQL Server para tener un usuario dedicado para la ejecuci贸n de esta aplicaci贸n_

_Ejecutaremos MySQL con el usuario root y posteriormente realizaremos la siguiente configuraci贸n:_

```
mysql> create database db_example; -- Crea una nueva BD
mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Crea el usuario con su respectiva contrase帽a
mysql> grant all on db_example.* to 'springuser'@'%'; -- Asigna todos los privilegios de la base de datos al usuario que hemos creado
```

_Esta configuraci贸n nos ser谩 de utilidad para modificar el archivo "application.properties" que se encuentra en la siguiente direcci贸n de la ra铆z de nuestro proyecto:_

```
.>src>resources>application.properties
```

_El archivo con la configuraci贸n asignada queda de la siguiente manera con los datos que hayas proporcionado:_
```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
spring.datasource.username=springuser
spring.datasource.password=ThePassword
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
```

### Instalaci贸n y ejecuci贸n 馃捇馃挕

_Para levantar nuestro servicio RestAPI tenemos que abrir una terminal en la ra铆z de nuestro proyecto, y ejecutar la siguiente instrucci贸n:_
```
./mvn spring-boot:run
```

_Para el caso de Windows es necesario que la variable de entorno de Maven est茅 bien implementada para que se ejecute dicha instrucci贸n_

_Una vez se haya ejecutado el servidor podremos realizar las pruebas de ejecuci贸n con "curl", la cual es una herramienta de l铆nea de comandos para transferencia de datos con URL's_

_Como primera parte del proyecto se implementa la ejecuci贸n de una solicitud HTTP GET como se muestra a continuaci贸n:_
```
C:\Users\Sweet>curl --location --request GET localhost:8080/hit/test3 --header accept:application/json
```

_Dicha instrucci贸n devuelve un formato JSON, en el cual se encuentra el KEY indicado en la URL y la cantidad de veces que se encuentra alojado en nuestra BD_
```
{"key":"test3","hits":4}
```
_Podemos usar cualquier tipo de KEY que contenga n煤meros o letras, esto se encuentra validado dentro de la misma aplicaci贸n. En caso de no ser as铆 la aplicaci贸n devolver谩 un error  indicando el c贸digo de error 501 NOT IMPLEMENTED como se muestra a continuaci贸n:_
```
{"timestamp":"2021-08-17T03:10:51.231+00:00","status":501,"error":"Not implemented","path":"/hit/test2"}curl: (6) Could not resolve host: application
```

_En caso de que no especifiquemos el header en la instrucci贸n curl que ejecutemos esta nos regresar谩 un mensaje de error indicando el c贸digo de error 400 BAD REQUEST, como el que se muestra a continuaci贸n:_
```
{"timestamp":"2021-08-17T03:10:49.435+00:00","status":400,"error":"Bad Request","path":"/hit/test4"}curl: (6) Could not resolve host: application
```

_Como segunda parte del proyecto se realiza la implementaci贸n de una solicitud HTTP GET como se muestra a continuaci贸n:_
```
C:\Users\Sweet>curl --location --request GET localhost:8080/hit --header accept:application/json
```

_Dicha instrucci贸n devuelve un formato JSON, en el cual se encuentran todas las KEY alojadas en nuestra Base de Datos y la cantidad de veces que se ha solicitado._
```
[{"key":"test1","hits":15},{"key":"test2","hits":8},{"key":"test3","hits":4}]
```
_En caso de que se presente alg煤n error durante la ejecuci贸n de la instrucci贸n esta nos regresar谩 un mensaje de error indicando el c贸digo de error 409 CONFLICT, como el que se muestra a continuaci贸n:_
```
{"timestamp":"2021-08-19T08:11:20.215+00:00","status":409,"error":"Conflict","path":"/hit"}curl: (6) Could not resolve host: application
```

## Test Unitario 馃搵

_Un test unitario es una forma de comprobar que un fragmento de c贸digo funciona correctamente, para este test se realizaron los test suficientes que nos ayudaron a completar el 50% de "coding coverage". Para validar que esto sea correcto se utiliz贸 el plugin JaCoCo, el cual nos ayuda a realizar un mapeo del c贸digo que vamos cubriendo en forma de reporte, la representaci贸n de este se realiza a trav茅s de una p谩gina web._


_Para comenzar a realizar nuestro test unitario es necesario ingresar a la base de datos que tenemos creada y eliminar los datos que tenemos en ella almacenados con la siguiente instrucci贸n:_ 
```
delete from hit_entity;
```

_Posteriormente es necesario realizar un clean del cliente jaCoCo con la siguiente instrucci贸n de Maven(estas instrucciones se deben ejecutar en una terminal en la ra铆z de nuestro proyecto):_
```
mvn clean jacoco:prepare-agent
```

_Una vez haya terminado la ejecuci贸n anterior procederemos a inicializar nuestro test, para esto ejecutaremos de igual manera la siguiente instrucci贸n:_
```
mvn test
```

_Durante la ejecuci贸n de la instrucci贸n anterior podremos visualizar en la terminal como se inicializa nuestro proyecto y se ejecutan nuestro test unitario. En caso de haber realizado esto de forma correcta podremos realizar la revisi贸n del informa que JaCoCo nos genera en la siguiente ubicaci贸n:_
```
.>target>site>jacoco>index.html
```

_Al abrir el archivo podremos visualizar a detalle las partes que nuestro test ha cubierto, la cantidad de instrucciones cubiertas, las partes faltantes por cubrir de nuestro c贸digo, entre muchas otras cosas que nos pueden ser de utilidad para saber si el funcionamiento de nuestro c贸digo es el esperado._

## Conclusiones 馃挰

_La implementaci贸n de una RestAPI en Spring-boot resulta bastanmte sencilla cuando se siguen paso a paso las gu铆as que se encuentran en la documentaci贸n de la p谩gina oficial, de igual manera la implementaci贸n individual de RabbitMQ con Spring y la implementaci贸n individual de Spring-boot con MySQL, sin embargo, el verdadero desaf铆o se encuentra en realizar una integraci贸n completa entre las 3 herramientas, ya que las implementaciones estandar que Spring nos brinda para poder manipular entidades dentro de nuestra RestAPI no siempre son compatibles con la forma en la que RabbitMQ o MySQL manipulan la informaci贸n._

_Este desaf铆o ayuda a darnos cuenta del mundo que es Java, de la existencia de muchas herramientas que brindan una gran ayuda para agilizar y realizar desarrollos 谩giles orientados a la productividad y a los resultados._


## Construido con 馃洜锔?

_Menciona las herramientas que utilizaste para crear tu proyecto_

* [JDK Java](https://www.oracle.com/mx/java/technologies/javase-downloads.html) - El lenguaje de programaci贸n usado
* [Spring-boot](https://spring.io/projects/spring-boot) - Framework web utilizado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [MySQL Server](https://dev.mysql.com/downloads/) - Manejador de base de datos
* [RabbitMQ Server](https://www.rabbitmq.com/) - Broker de mensajer铆a de c贸digo abierto
* [VSCode](https://code.visualstudio.com/) - Editor de c贸digo fuente


鈱笍 con 鉂わ笍 por [pacopro3](https://github.com/pacopro3) 馃槉
