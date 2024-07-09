# ecommerce-co
Para poder instalar este programa y poder correrlo a nivel local, deberán configurar los siguientes campos del archivo application.properties que se encuentra
en: ecommerce -> ecommerce -> src -> main -> resources -> application.properties

spring.datasource.url=jdbc:mysql://localhost:tupuerto/elnombredetubasededatos
spring.datasource.username=tu usuario
spring.datasource.password=tu contraseña

/////////////////////////////

Por otra parte, para facilitar las consultas en postman, exporté la colección 
entera de consultas para que no las tengan que crear. 

Este archivo lo van a encontrar como postmancollection.json y para importarlo desde postman, 
van a Files -> import y ahí eligen el archivo postmancollection.json

