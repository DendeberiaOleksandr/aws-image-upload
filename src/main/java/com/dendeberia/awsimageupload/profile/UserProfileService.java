package com.dendeberia.awsimageupload.profile;

import com.dendeberia.awsimageupload.bucket.BucketName;
import com.dendeberia.awsimageupload.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                              FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = getUserProfileOrElseThrow(userProfileId);
        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getProfileId());
        return user.getProfileImageLink()
                .map(link -> fileStore.download(path, link))
                .orElse(new byte[0]);
    }

    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId,
                                       MultipartFile file){

        //1. Check if file is not empty
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file");
        }

        //2. Check if file type is supported
        String fileContentType = file.getContentType();
        if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(fileContentType)){
            throw new IllegalStateException("Type of image is not supported. Supported types: JPEG, PNG, GIF");
        }

        //3. Check if user exists in database
        UserProfile user = getUserProfileOrElseThrow(userProfileId);

        //4. Grab metadata from file
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", fileContentType);
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        //5. Upload file to S3 bucket
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(
                path, filename, Optional.of(metadata), file.getInputStream()
            );
            user.setProfileImageLink(filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private UserProfile getUserProfileOrElseThrow(UUID userProfileId){
        return userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not exist", userProfileId)));
    }

}
