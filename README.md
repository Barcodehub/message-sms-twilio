# 📱 SMS Notification Service

Microservicio de mensajería SMS usando Twilio para el proyecto Food Court - Pragma PowerUp.

## 🎯 Descripción

Este microservicio proporciona funcionalidad de envío de mensajes SMS a través de Twilio. Está diseñado para ser llamado desde otros microservicios del sistema Food Court para notificar a los usuarios sobre el estado de sus pedidos u otras comunicaciones importantes.

## 🏗️ Arquitectura

El proyecto sigue los principios de **Arquitectura Hexagonal (Puertos y Adaptadores)**:

- **Domain**: Lógica de negocio pura y reglas de validación
- **Application**: Casos de uso y orquestación
- **Infrastructure**: Adaptadores para comunicación externa (Twilio, REST API)

### Estructura del Proyecto

```
src/main/java/com/pragma/powerup/
├── domain/
│   ├── api/              # Puertos de entrada (interfaces de servicios)
│   ├── spi/              # Puertos de salida (interfaces de persistencia)
│   ├── model/            # Modelos de dominio
│   ├── usecase/          # Casos de uso
│   └── exception/        # Excepciones de dominio
├── application/
│   ├── handler/          # Manejadores de aplicación
│   └── mapper/           # Mapeadores entre capas
└── infrastructure/
    ├── configuration/    # Configuración de Spring
    ├── input/rest/       # Controladores REST
    ├── out/twilio/       # Adaptador de Twilio
    └── exceptionhandler/ # Manejo global de excepciones
```

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Twilio SDK 10.0.0**
- **MapStruct** - Mapeo de objetos
- **Lombok** - Reducción de código boilerplate
- **SpringDoc OpenAPI** - Documentación de API
- **Gradle** - Gestión de dependencias

## 📋 Prerequisitos

- JDK 17 o superior
- Cuenta de Twilio (con Account SID, Auth Token y número de teléfono)
- Gradle 7.x o superior

## ⚠️ Seguridad y Protección de Secretos

**No incluyas valores sensibles (como Account SID, Auth Token o Phone Number de Twilio) directamente en archivos de configuración como `application.yml`.** Usa siempre variables de entorno y verifica que no se suban secretos al repositorio. GitHub puede bloquear tus pushes si detecta secretos expuestos.

## ⚙️ Configuración

### Variables de Entorno

Configura las siguientes variables de entorno en tu sistema o en el entorno de despliegue:

```bash
# Configuración de Twilio
TWILIO_ACCOUNT_SID=tu_account_sid
TWILIO_AUTH_TOKEN=tu_auth_token
TWILIO_PHONE_NUMBER=tu_numero_twilio

# Configuración del servidor
SERVER_PORT=8081
SPRING_PROFILE=dev
```

### Archivos de Configuración

- `application.yml` - No debe contener valores sensibles, solo referencias a variables de entorno.
- `application-dev.yml` - Configuración de desarrollo
- `application-prod.yml` - Configuración de producción

## 🔧 Instalación y Ejecución

### Compilar el proyecto

```bash
./gradlew clean build
```

### Ejecutar en modo desarrollo

```bash
./gradlew bootRun
```

### Ejecutar con perfil específico

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## 📡 API Endpoints

### Enviar SMS

**POST** `/sms/send`

**Request Body:**
```json
{
  "phoneNumber": "+573001234567",
  "message": "Su pedido está listo para ser recogido",
  "metadata": {
    "orderId": "12345",
    "restaurantName": "Restaurante Ejemplo"
  }
}
```

**Response (200 OK):**
```json
{
  "data": {
    "sid": "SM1234567890abcdef1234567890abcdef",
    "status": "queued",
    "phoneNumber": "+573001234567",
    "sentAt": "2024-12-01T10:30:00Z",
    "message": "Su pedido está listo para ser recogido"
  }
}
```

## 📚 Documentación API

La documentación interactiva de la API está disponible en:

- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI Spec**: `http://localhost:8081/api-docs`

## 🧪 Testing

```bash
# Ejecutar todos los tests
./gradlew test

# Ejecutar tests con reporte de cobertura
./gradlew test jacocoTestReport
```

## 🔒 Validaciones

El servicio incluye validaciones para:

- ✅ Formato de número de teléfono (E.164)
- ✅ Longitud del mensaje (máx. 1600 caracteres)
- ✅ Campos requeridos

## 🐛 Manejo de Errores

El servicio maneja las siguientes excepciones:

- `InvalidPhoneNumberException` - Número de teléfono inválido
- `SmsDeliveryException` - Error al enviar el SMS
- `SmsException` - Error genérico de SMS
- `NoDataFoundException` - Datos no encontrados

## 📝 Logs

Los logs se configuran según el perfil activo:

- **Desarrollo**: Nivel DEBUG para el paquete `com.pragma.powerup`
- **Producción**: Nivel INFO para el paquete `com.pragma.powerup`

## 🤝 Integración con otros Microservicios

Este microservicio está diseñado para ser consumido por otros servicios del ecosistema Food Court. Para integrarlo:

1. Realiza una petición HTTP POST a `/sms/send`
2. Incluye las credenciales necesarias (si aplica)
3. Proporciona el número de teléfono en formato E.164
4. Incluye el mensaje y metadatos opcionales

### Ejemplo de integración (Java)

```java
RestTemplate restTemplate = new RestTemplate();
SmsRequest request = new SmsRequest(
    "+573001234567",
    "Su pedido #123 está listo",
    Map.of("orderId", "123")
);

ResponseEntity<SmsDataResponse> response = restTemplate.postForEntity(
    "http://localhost:8081/sms/send",
    request,
    SmsDataResponse.class
);
```

## 📞 Contacto

Pragma PowerUp Team

## 📄 Licencia

Este proyecto es parte del programa Pragma PowerUp.
