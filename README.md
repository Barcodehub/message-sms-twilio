<br />
<div align="center">
<h3 align="center">PRAGMA POWER-UP - SMS NOTIFICATION SERVICE</h3>
  <p align="center">
    Microservicio de mensajería SMS usando Twilio para el sistema de plazoleta de comidas. Notifica a los clientes cuando sus pedidos están listos para recoger.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Twilio](https://img.shields.io/badge/Twilio-F22F46?style=for-the-badge&logo=Twilio&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

## Descripción General

Este microservicio proporciona funcionalidad de envío de mensajes SMS a través de Twilio. Está diseñado para ser llamado desde el microservicio `foodcourt` para notificar a los usuarios sobre el estado de sus pedidos.

**Responsabilidades:**
- **Envío de SMS**: Integración con API de Twilio para enviar mensajes de texto
- **Notificaciones de pedidos**: Envía PIN de seguridad cuando un pedido está listo para recoger
- **Validación de números**: Verifica formato de números telefónicos (E.164)
- **Manejo de errores**: Gestiona errores de conectividad y entrega de SMS

**Puerto:** 8084  
**Base de datos:** No requiere (stateless)  
**Arquitectura:** Hexagonal (Puertos y Adaptadores)

### Arquitectura

El proyecto sigue los principios de **Arquitectura Hexagonal (Puertos y Adaptadores)**:

```
src/
├── domain/              # Lógica de negocio pura
│   ├── model/          # Modelos de dominio
│   ├── usecase/        # Casos de uso
│   ├── api/            # Puertos de entrada
│   ├── spi/            # Puertos de salida
│   └── exception/      # Excepciones de dominio
├── application/         # Capa de aplicación
│   ├── handler/        # Handlers
│   └── mapper/         # Mappers (MapStruct)
└── infrastructure/      # Adaptadores
    ├── input/rest/     # Controladores REST
    ├── out/twilio/     # Adaptador de Twilio
    └── configuration/  # Configuración de Spring
```
---

## Endpoints Implementados

### `POST /sms/send`

Envía un mensaje SMS a un número de teléfono específico.

> **Nota:** Este endpoint es llamado internamente por el microservicio `foodcourt` cuando un pedido está listo.

**Request Body:**
```json
{
  "phoneNumber": "+573001234567",
  "message": "Su pedido #123 está listo para recoger. PIN de seguridad: 4567",
  "metadata": {
    "orderId": "123",
    "restaurantName": "Restaurante Ejemplo",
    "securityPin": "4567"
  }
}
```

**Validaciones:**
- ✅ `phoneNumber` es obligatorio y debe estar en formato E.164 (ejemplo: +573001234567)
- ✅ `message` es obligatorio y debe tener máximo 1600 caracteres
- ✅ `metadata` es opcional (información adicional para tracking)

**Response (200 OK):**
```json
{
  "data": {
    "sid": "SM1234567890abcdef1234567890abcdef",
    "status": "queued",
    "phoneNumber": "+573001234567",
    "sentAt": "2025-12-07T10:30:00Z",
    "message": "Su pedido #123 está listo para recoger. PIN de seguridad: 4567"
  }
}
```

**Posibles valores de status:**
- `queued`: El mensaje está en cola para ser enviado
- `sent`: El mensaje ha sido enviado
- `delivered`: El mensaje fue entregado exitosamente
- `failed`: El envío falló

**Errores:**
- `400 Bad Request`: Número de teléfono inválido o mensaje muy largo
- `500 Internal Server Error`: Error en la API de Twilio

---

## Cómo Ejecutar Localmente

### 1. Prerequisitos

- ✅ JDK 17
- ✅ Gradle
- ✅ **Cuenta de Twilio** (con Account SID, Auth Token y número de teléfono)

### 2. Crear Cuenta de Twilio

1. **Registrarse en Twilio**
   - Ir a [https://www.twilio.com/try-twilio](https://www.twilio.com/try-twilio)
   - Crear una cuenta gratuita (incluye créditos de prueba)

2. **Obtener credenciales**
   - Una vez dentro del dashboard, ir a "Account Info"
   - Copiar:
     - `Account SID`
     - `Auth Token`

3. **Obtener un número de teléfono**
   - En el dashboard, ir a "Phone Numbers" → "Manage" → "Buy a number"
   - Seleccionar un número con capacidad SMS
   - Para pruebas, Twilio proporciona un número gratuito

4. **Verificar números de destino (cuenta de prueba)**
   - Si usas una cuenta de prueba, debes verificar los números a los que enviarás SMS
   - Ir a "Phone Numbers" → "Manage" → "Verified Caller IDs"
   - Agregar y verificar tu número de teléfono

### 3. Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd message-sms
   ```

2. **Configurar variables de entorno**

   ```powershell
   $env:TWILIO_ACCOUNT_SID="tu_account_sid_aqui"
   $env:TWILIO_AUTH_TOKEN="tu_auth_token_aqui"
   $env:TWILIO_PHONE_NUMBER="+1234567890"
   ```

3. **Verificar configuración**
   
   Editar `src/main/resources/application-dev.yml`:
   ```yaml
   twilio:
     account-sid: ${TWILIO_ACCOUNT_SID}
     auth-token: ${TWILIO_AUTH_TOKEN}
     phone-number: ${TWILIO_PHONE_NUMBER}
   ```

### 4. Compilar el Proyecto

```bash
# Generar DTOs desde OpenAPI spec
./gradlew openApiGenerate

# Compilar todo el proyecto
./gradlew clean build

```

### 5. Ejecutar la Aplicación

**Opción 1: Desde terminal**
```bash
./gradlew bootRun
```

**Opción 2: Desde IntelliJ IDEA**
- Configurar las variables de entorno en la configuración de ejecución
- Right-click `PowerUpApplication.java` → Run

### 6. Probar envío de SMS

**Usando Postman:**
1. Crear una petición POST a `http://localhost:8084/sms/send`
2. En Body (raw JSON), pegar el JSON de ejemplo
3. Cambiar el `phoneNumber` por tu número verificado
4. Enviar y verificar que recibes el SMS

---

## Integración con otros Microservicios

Este microservicio está diseñado para ser consumido por el microservicio [`foodcourt`](https://github.com/Barcodehub/foodcourt).

---

## Autor

**Brayan Barco**

## Licencia

Este proyecto es parte de la prueba técnica para Pragma.

