# Growth Hit

_Se implementa una RestAPI con Spring-boot, RabbitMQ y MySQL_

## Comenzando 🚀

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas._

### Pre-requisitos 📌

_Para el uso correcto de esta implementación es necesario tener pre-instalado el JDK de Java, Maven, MySQL Server y RabbitMQ Server._
_Para el caso de instalarse en Windows, es necesario tener dadas de alta las variables de entorno en el sistema, para su correcta ejecución._

* [JDK Java](https://www.oracle.com/mx/java/technologies/javase-downloads.html) - El lenguaje de programación usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [MySQL Server](https://dev.mysql.com/downloads/) - Manejador de base de datos
* [RabbitMQ Server](https://www.rabbitmq.com/) - Broker de mensajería de código abierto

_Para saber como realizar la instalación de estos pre-requisitos dirigirse a sus páginas correspondientes y seguir la información de la documentación._

### Pre-Instalación 🔧

_Una vez tenemos instalados todos los pre-requisitos y habiendo clonado el proycto a nuestro repositorio local, haremos una configuración en MySQL Server para tener un usuario dedicado para la ejecución de esta aplicación_

_Ejecutaremos MySQL con el usuario root y posteriormente realizaremos la siguiente configuración:_

```
mysql> create database db_example; -- Crea una nueva BD
mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Crea el usuario con su respectiva contraseña
mysql> grant all on db_example.* to 'springuser'@'%'; -- Asigna todos los privilegios de la base de datos al usuario que hemos creado
```

_Esta configuración nos será de utilidad para modificar el archivo "application.properties" que se encuentra en la siguiente dirección de la raíz de nuestro proyecto:_

```
.>src>resources>application.properties
```

_El archivo con la configuración asignada queda de la siguiente manera con los datos que hayas proporcionado:_
```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
spring.datasource.username=springuser
spring.datasource.password=ThePassword
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
```

### Instalación y ejecución 💻💡

_Para levantar nuestro servicio RestAPI tenemos que abrir una terminal en la raíz de nuestro proyecto, y ejecutar la siguiente instrucción:_
```
./mvn spring-boot:run
```

_Para el caso de Windows es necesario que la variable de entorno de Maven esté bien implementada para que se ejecute dicha instrucción_

_Una vez se haya ejecutado el servidor podremos realizar las pruebas de ejecución con "curl", la cual es una herramienta de línea de comandos para transferencia de datos con URL's_

_Como primera parte del proyecto se implementa la ejecución de una solicitud HTTP GET como se muestra a continuación:_
```
C:\Users\Sweet>curl --location --request GET localhost:8080/hit/test3 --header accept:application/json
```

_Dicha instrucción devuelve un formato JSON, en el cual se encuentra el KEY indicado en la URL y la cantidad de veces que se encuentra alojado en nuestra BD_
```
{"key":"test3","hits":4}
```
_Podemos usar cualquier tipo de KEY que contenga números o letras, esto se encuentra validado dentro de la misma aplicación. En caso de no ser así la aplicación devolverá un error  indicando el código de error 501 NOT IMPLEMENTED como se muestra a continuación:_
```
{"timestamp":"2021-08-17T03:10:51.231+00:00","status":501,"error":"Not implemented","path":"/hit/test2"}curl: (6) Could not resolve host: application
```

_En caso de que no especifiquemos el header en la instrucción curl que ejecutemos esta nos regresará un mensaje de error indicando el código de error 400 BAD REQUEST, como el que se muestra a continuación:_
```
{"timestamp":"2021-08-17T03:10:49.435+00:00","status":400,"error":"Bad Request","path":"/hit/test4"}curl: (6) Could not resolve host: application
```

_Como segunda parte del proyecto se realiza la implementación de una solicitud HTTP GET como se muestra a continuación:_
```
C:\Users\Sweet>curl --location --request GET localhost:8080/hit --header accept:application/json
```

_Dicha instrucción devuelve un formato JSON, en el cual se encuentran todas las KEY alojadas en nuestra Base de Datos y la cantidad de veces que se ha solicitado._
```
[{"key":"test1","hits":15},{"key":"test2","hits":8},{"key":"test3","hits":4}]
```
_En caso de que se presente algún error durante la ejecución de la instrucción esta nos regresará un mensaje de error indicando el código de error 409 CONFLICT, como el que se muestra a continuación:_
```
{"timestamp":"2021-08-19T08:11:20.215+00:00","status":409,"error":"Conflict","path":"/hit"}curl: (6) Could not resolve host: application
```

## Test Unitario 📋

_Un test unitario es una forma de comprobar que un fragmento de código funciona correctamente, para este test se realizaron los test suficientes que nos ayudaron a completar el 50% de "coding coverage". Para validar que esto sea correcto se utilizó el plugin JaCoCo, el cual nos ayuda a realizar un mapeo del código que vamos cubriendo en forma de reporte, la representación de este se realiza a través de una página web._


_Para comenzar a realizar nuestro test unitario es necesario ingresar a la base de datos que tenemos creada y eliminar los datos que tenemos en ella almacenados con la siguiente instrucción:_ 
```
delete from hit_entity;
```

_Posteriormente es necesario realizar un clean del cliente jaCoCo con la siguiente instrucción de Maven(estas instrucciones se deben ejecutar en una terminal en la raíz de nuestro proyecto):_
```
mvn clean jacoco:prepare-agent
```

_Una vez haya terminado la ejecución anterior procederemos a inicializar nuestro test, para esto ejecutaremos de igual manera la siguiente instrucción:_
```
mvn test
```

_Durante la ejecución de la instrucción anterior podremos visualizar en la terminal como se inicializa nuestro proyecto y se ejecutan nuestro test unitario. En caso de haber realizado esto de forma correcta podremos realizar la revisión del informa que JaCoCo nos genera en la siguiente ubicación:_
```
.>target>site>jacoco>index.html
```

_Al abrir el archivo podremos visualizar a detalle las partes que nuestro test ha cubierto, la cantidad de instrucciones cubiertas, las partes faltantes por cubrir de nuestro código, entre muchas otras cosas que nos pueden ser de utilidad para saber si el funcionamiento de nuestro código es el esperado._

## Conclusiones 💬

_La implementación de una RestAPI en Spring-boot resulta bastanmte sencilla cuando se siguen paso a paso las guías que se encuentran en la documentación de la página oficial, de igual manera la implementación individual de RabbitMQ con Spring y la implementación individual de Spring-boot con MySQL, sin embargo, el verdadero desafío se encuentra en realizar una integración completa entre las 3 herramientas, ya que las implementaciones estandar que Spring nos brinda para poder manipular entidades dentro de nuestra RestAPI no siempre son compatibles con la forma en la que RabbitMQ o MySQL manipulan la información._

_Este desafío ayuda a darnos cuenta del mundo que es Java, de la existencia de muchas herramientas que brindan una gran ayuda para agilizar y realizar desarrollos ágiles orientados a la productividad y a los resultados._


## Construido con 🛠️

_Menciona las herramientas que utilizaste para crear tu proyecto_

* [JDK Java](https://www.oracle.com/mx/java/technologies/javase-downloads.html) - El lenguaje de programación usado
* [Spring-boot](https://spring.io/projects/spring-boot) - Framework web utilizado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [MySQL Server](https://dev.mysql.com/downloads/) - Manejador de base de datos
* [RabbitMQ Server](https://www.rabbitmq.com/) - Broker de mensajería de código abierto
* [VSCode](https://code.visualstudio.com/) - Editor de código fuente


⌨️ con ❤️ por [pacopro3](https://github.com/pacopro3) 😊
