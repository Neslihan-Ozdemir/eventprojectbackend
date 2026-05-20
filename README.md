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

<pre>
src/main/java/com/example/eventproject/
├── config/
│   ├── AppBeans.java
│   ├── CorsConfig.java
│   ├── GlobalException.java
│   ├── SessionFilter.java
│   └── SwaggerConfig.java
├── controller/
│   ├── EventController.java
│   ├── ParticipantController.java
│   └── UsersController.java
├── dto/
│   ├── EventCreateDto.java
│   ├── EventDetailResponseDto.java
│   ├── EventResponseDto.java
│   ├── EventSearchDto.java
│   ├── EventUpdateDto.java
│   ├── JoinEventDto.java
│   ├── ParticipantResponseDto.java
│   ├── UsersLoginDto.java
│   ├── UsersRegisterDto.java
│   └── UsersResponseDto.java
├── entity/
│   ├── Event.java
│   ├── Participant.java
│   └── Users.java
├── repository/
│   ├── EventRepository.java
│   ├── ParticipantRepository.java
│   └── UsersRepository.java
├── service/
│   ├── EventArchiveService.java
│   ├── EventService.java
│   ├── ParticipantService.java
│   └── UsersService.java
├── util/
│   └── EventStatus.java
└── EventprojectApplication.java
</pre>

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
| GET | /participant/my-events | Katıldığım etkinlikler |

### Sayfalar

***Giriş Yap Sayfası***

<img width="546" height="464" alt="image" src="https://github.com/user-attachments/assets/e038d149-44dc-4399-95ec-efd7d34c693a" />

***Kayıt Ol Sayfası***

<img width="575" height="570" alt="image" src="https://github.com/user-attachments/assets/cefd7a09-3b86-49c8-b80d-9850b8d46f4a" />


**Etkinlik Listeleme Sayfası***

<img width="1920" height="788" alt="image" src="https://github.com/user-attachments/assets/4511d95b-997d-472c-a50d-6a18f6618106" />

***Örnek Etkinik Detayı***

<img width="1920" height="812" alt="image" src="https://github.com/user-attachments/assets/db63dc7a-d35d-42e7-9dfa-9e803f7953f5" />

***Etkinlik Oluşturma Sayfası***

<img width="1920" height="810" alt="image" src="https://github.com/user-attachments/assets/89a68b4f-d66c-47fd-ba3c-29ac112a3e15" />

***Etkinliklerim Sayfası***

<img width="1920" height="808" alt="image" src="https://github.com/user-attachments/assets/e2ef2330-6729-4167-9613-dcf9022b49e2" />

***Katılınan Etkinlikler Sayfası***

<img width="1920" height="809" alt="image" src="https://github.com/user-attachments/assets/1a6ea56c-8932-443a-9632-6d95dde9be6b" />



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
