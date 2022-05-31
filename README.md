# aws-image-upload

Application allows to manage user profiles images.

## [Backend](https://github.com/DendeberiaOleksandr/aws-image-upload/tree/master)

### Domain
Application has next entities:
1. UserProfile
    - profileId
    - username
    - profileImageLink

But application doesn't provide methods for managing user profiles. It has in memory hardcoded profiles. Application only allows to upload/download user profile image. For this purposes it has 2 endpoints.

| Method  | Endpoint | Desc |
| ------------- | ------------- | ------------- |
| GET  | {user-profile-id}/image/download  | Download current user profile image |
| POST  | {user-profile-id}/image/upload  | Upload new user profile image |
