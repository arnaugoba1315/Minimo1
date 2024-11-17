Mi proyecto tiene dos clases Ubication y User bien implementadas.
Tambien el manager y el ManagerImpl.
En el test me hace bien todos los test excepto TestAddPointOfInterest.
Accede sin problemas al swagger.
A la hora de implemetar los GET POST,etc puedo saber que:
-   PUT /services/users/{userId}/position funciona bien.
- GET  /services/users/{userId}/position funciona bien 
- POST /services/users/{userId}/points error
- GET /services/users/{userId}/points error
- GET /services/users/{id} funciona bien 
- POST /services/users ERROR
- GET /services/users error
- GET /services/points error
 
    