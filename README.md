Configurar el backend 

1. Ve al archivo:
   app/src/main/java/com/example/eduread/network/RetrofitClient.kt

2. Busca la línea donde aparece BASE_URL.

3. Cambia BASE_URL por la dirección del servidor donde está tu backend.
   - Si usas Render, coloca el dominio que te da Render. Ejm: https://eco-read-backend.onrender.com
   - Si usas el local con el repositorio https://github.com/Kesowont/eco_read_backend.git. Coloca tu IP local y el puerto. Ejm: http://192.168.1.6:8000/

4. Guardar los cambios y vuelve a ejecutar la app.
