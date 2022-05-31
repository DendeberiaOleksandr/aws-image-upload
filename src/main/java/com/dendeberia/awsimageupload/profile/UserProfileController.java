package com.dendeberia.awsimageupload.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user-profiles")
@CrossOrigin("http://localhost:3000")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles(){
        return userProfileService.getUserProfiles();
    }

    @PostMapping(
            path = "{user-profile-id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadUserProfileImage(@PathVariable("user-profile-id")UUID userProfileId,
                                       @RequestParam("file")MultipartFile file){
        userProfileService.uploadUserProfileImage(userProfileId, file);
    }

    @GetMapping("{user-profile-id}/image/download")
    public byte[] downloadProfileImage(@PathVariable("user-profile-id")UUID userProfileId){
        return userProfileService.downloadUserProfileImage(userProfileId);
    }

}