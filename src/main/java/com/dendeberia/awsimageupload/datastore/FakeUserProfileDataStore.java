package com.dendeberia.awsimageupload.datastore;

import com.dendeberia.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("0519d4bb-8ffe-45bd-8a8a-b5849412f0a4"), "salvator", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("9999fdd5-abd0-474a-9855-7a078c18aebb"), "jack", null));
    }

    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }

}
