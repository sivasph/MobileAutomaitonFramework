package framework.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestData {
    private String ProjectName;
    private String URL;
    private String CloudURL;
    private String AccessKey;
    private String InstallApp;
    private String Android_AppName;
    private String Android_AppPackage;
    private String Android_AppActivity;
    private String iOS_BundleID;

    //region Getter and Setter methods
    public String getCloudURL() {
        return CloudURL;
    }
    @JsonProperty("CloudURL")
    public void setCloudURL(String cloudURL) {
        CloudURL = cloudURL;
    }

    public String getURL() {
        return URL;
    }
    @JsonProperty("URL")
    public void setURL(String url) {
        URL = url;
    }
    public String getProjectName() {
        return ProjectName;
    }
    @JsonProperty("ProjectName")
    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }
    public String getAccessKey() {
        return AccessKey;
    }
    @JsonProperty("AccessKey")
    public void setAccessKey(String accessKey) {
        AccessKey = accessKey;
    }

    public String getInstallApp() {
        return InstallApp;
    }
    @JsonProperty("InstallApp")
    public void setInstallApp(String installApp) {
        InstallApp = installApp;
    }

    public String getAndroid_AppName() {
        return Android_AppName;
    }
    @JsonProperty("Android_AppName")
    public void setAndroid_AppName(String android_AppName) {
        Android_AppName = android_AppName;
    }

    public String getAndroid_AppPackage() {
        return Android_AppPackage;
    }
    @JsonProperty("Android_AppPackage")
    public void setAndroid_AppPackage(String android_AppPackage) {
        Android_AppPackage = android_AppPackage;
    }

    public String getAndroid_AppActivity() {
        return Android_AppActivity;
    }
    @JsonProperty("Android_AppActivity")
    public void setAndroid_AppActivity(String android_AppActivity) {
        Android_AppActivity = android_AppActivity;
    }

    public String getiOS_BundleID() {
        return iOS_BundleID;
    }
    @JsonProperty("iOS_BundleID")
    public void setiOS_BundleID(String iOS_BundleID) {
        this.iOS_BundleID = iOS_BundleID;
    }



//endregion Getter and Setter methods



    public class TestDataLoader {
        public static TestData loadTestData() throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonFilePath = "testdata.json";
            return objectMapper.readValue(new File(jsonFilePath), TestData.class);
        }
    }



}
