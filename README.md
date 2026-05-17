# Etkinlik Planlama Uygulaması - Backend

## Proje Açıklaması
Kullanıcıların etkinlik oluşturabildiği, diğer kullanıcıların etkinlikleri görüntüleyip katılım sağlayabildiği bir web uygulamasının backend projesidir.

## Kullanılan Teknolojiler
- Java 17
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Validation
- H2 Database
- Lombok
- ModelMapper
- BCrypt
- Http Session Authentication
- Swagger (SpringDoc OpenAPI)

## Proje Yapısı

```
src/main/java/com/example/eventproject/
├── config/
│   ├── AppBeans.java
│   ├── CorsConfig.java
│   ├── GlobalException.java
│   ├── SessionFilter.java
│   └── SwaggerConfig.java
├── controller/
│   ├── UsersController.java
│   ├── EventController.java
│   └── ParticipantController.java
├── dto/
├── entity/
├── repository/
├── service/
└── util/
```

## API Endpoints

### Kullanıcı API
| Method | URL | Açıklama |
|--------|-----|----------|
| POST | /users/register | Kayıt ol |
| POST | /users/login | Giriş yap |
| GET | /users/logout | Çıkış yap |

### Etkinlik API
| Method | URL | Açıklama |
|--------|-----|----------|
| POST | /event/create | Etkinlik oluştur |
| PUT | /event/update | Etkinlik güncelle |
| DELETE | /event/delete/{id} | Etkinlik sil |
| GET | /event/list | Etkinlikleri listele |
| GET | /event/detail/{id} | Etkinlik detayı |
| GET | /event/search?q= | Etkinlik ara |
| GET | /event/my-events | Kendi etkinliklerim |
| PUT | /event/publish/{id} | Etkinlik yayınla |
| PUT | /event/pause/{id} | Yayını durdur |
| PUT | /event/archive/{id} | Arşivle |

### Katılım API
| Method | URL | Açıklama |
|--------|-----|----------|
| POST | /participant/join | Etkinliğe katıl |
| GET | /participant/list/{eventId} | Katılımcıları listele |
| GET | /participant/my-event | Katıldığım etkinlikler |

## Kurulum Adımları

### Gereksinimler
- Java 17+
- Maven

### Backend Çalıştırma
```bash
git clone https://github.com/Neslihan-Ozdemir/eventprojectbackend.git
cd eventprojectbackend
./mvnw spring-boot:run
```

Uygulama `http://localhost:8090` adresinde çalışacaktır.

## Swagger
Swagger UI: `http://localhost:8090/swagger-ui/index.html`

## Veritabanı Bilgileri
- **Veritabanı:** H2 (File-based)
- **H2 Console:** `http://localhost:8090/h2-console`
- **JDBC URL:** `jdbc:h2:file:~/eventproject_db`
- **Username:** sa
- **Password:** sa
