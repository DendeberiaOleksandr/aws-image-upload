# aws-image-upload

Application allows to manage user profile image storing it in [Amazon S3 Service](https://docs.aws.amazon.com/s3/index.html).

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

## [Frontend](https://github.com/DendeberiaOleksandr/aws-image-upload/tree/frontend)

![Client](https://i.imgur.com/RF2NZwz.png)
