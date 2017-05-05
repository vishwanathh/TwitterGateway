package com.ericsson.appiot.gateway.examples.smartobjects;

import com.ericsson.appiot.gateway.device.smartobject.SmartObjectBase;
import com.ericsson.appiot.gateway.device.smartobject.resource.ReadRequestException;
import com.ericsson.appiot.gateway.device.smartobject.resource.WriteRequestException;
import com.ericsson.appiot.gateway.device.smartobject.resource.type.StringResource;
import com.ericsson.appiot.gateway.examples.twitter.TwitterApiException;
import com.ericsson.appiot.gateway.examples.twitter.TwitterApiWrapper;
import com.ericsson.appiot.gateway.model.ResourceModel;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TwitterResourceText extends StringResource {

    TwitterApiWrapper twitterApi;
    public TwitterResourceText(SmartObjectBase smartObject, ResourceModel resourceModel, TwitterApiWrapper twitterApi) {
        super(smartObject, resourceModel);
        this.twitterApi = twitterApi;
    }

    @Override
    public void onWriteRequest(Object value) throws WriteRequestException {
        try {
            twitterApi.tweet(URLEncoder.encode((String) value, StandardCharsets.UTF_8.toString()));
            setValue(value);
        } catch (Exception e) {
           throw new WriteRequestException(e);
        } 
    }

    @Override
    public Object onReadRequest() throws ReadRequestException {
        try {
            return twitterApi.getLatest();
        } catch (TwitterApiException e) {
            throw new ReadRequestException(e);
        }        
    }    
}
