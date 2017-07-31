package com.ericsson.appiot.gateway.examples.smartobjects;

import com.ericsson.appiot.gateway.device.Device;
import com.ericsson.appiot.gateway.device.smartobject.SmartObjectBase;
import com.ericsson.appiot.gateway.deviceregistry.DeviceRegistration;
import com.ericsson.appiot.gateway.dto.SettingCategory;
import com.ericsson.appiot.gateway.examples.twitter.TwitterApiWrapper;
import com.ericsson.appiot.gateway.model.ObjectModel;
import com.ericsson.appiot.gateway.model.ResourceModel;

public class TweetingAddressableTextDisplay extends SmartObjectBase {

    private static final String USER_ID = "userId";

    private static final String ACCESS_TOKEN_SECRET_STR = "accessTokenSecretStr";

    private static final String ACCESS_TOKEN_STR = "accessTokenStr";

    private static final String CONSUMER_SECRET_STR = "consumerSecretStr";

    private static final String CONSUMER_KEY_STR = "consumerKeyStr";

    private static final String TWITTER_CREDENTIALS = "TwitterCredentials";

    public static final int OBJECT_ID_TEMPERATURE = 3341;

    public static final int RESOURCE_ID_TEXT = 5527;

    private TwitterApiWrapper twitterApi;

    /**
     * Creates a Addressable Text Display Smart Object.
     * 
     */
    public TweetingAddressableTextDisplay(Device device, ObjectModel objectModel, Integer objectId,
            Integer instanceId) {
        super(device, objectModel, objectId, instanceId);
        init();
    }

    public void init() {
        DeviceRegistration deviceRegistration = getDevice().getDeviceRegistration();
        if (deviceRegistration != null) {
            SettingCategory credentials = deviceRegistration.getSettingCategory(
                    TWITTER_CREDENTIALS);
            if (credentials != null) {
                String consumerKeyStr = credentials.getSettingValue(CONSUMER_KEY_STR);
                String consumerSecretStr = credentials.getSettingValue(CONSUMER_SECRET_STR);
                String accessTokenStr = credentials.getSettingValue(ACCESS_TOKEN_STR);
                String accessTokenSecretStr = credentials.getSettingValue(ACCESS_TOKEN_SECRET_STR);
                String userId = credentials.getSettingValue(USER_ID);
                twitterApi = new TwitterApiWrapper(consumerKeyStr, consumerSecretStr,
                        accessTokenStr, accessTokenSecretStr, userId);
                ResourceModel model = getModel().getResourceModel(RESOURCE_ID_TEXT);
                TwitterResourceText resourceText = new TwitterResourceText(this, model, twitterApi);
                addResource(resourceText);
            }
        }
    }

    public TwitterApiWrapper getTwitterApiHandler() {
        return twitterApi;
    }
}
