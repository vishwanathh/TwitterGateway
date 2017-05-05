package com.ericsson.appiot.gateway.examples.twitter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class TwitterApiWrapper {

    private String consumerKeyStr;
    private String consumerSecretStr;
    private String accessTokenStr;
    private String accessTokenSecretStr;
    private String userId;

    public TwitterApiWrapper(String consumerKeyStr, String consumerSecretStr, String accessTokenStr,
            String accessTokenSecretStr, String userId) {
        this.consumerKeyStr = consumerKeyStr;
        this.consumerSecretStr = consumerSecretStr;
        this.accessTokenStr = accessTokenStr;
        this.accessTokenSecretStr = accessTokenSecretStr;
        this.userId = userId;
    }

    public void tweet(String message) throws TwitterApiException {
        OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr,
                consumerSecretStr);
        oAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);

        HttpPost httpPost = new HttpPost(
                "https://api.twitter.com/1.1/statuses/update.json?status=" + message);

        try {
            oAuthConsumer.sign(httpPost);

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode > 299) {
                throw new TwitterApiException(httpResponse.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            throw new TwitterApiException(e);
        }
    }
    
    public String getLatest() throws TwitterApiException {
        String result = "";
        OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr,
                consumerSecretStr);
        oAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);
        HttpGet httpGet = new HttpGet(
                "https://api.twitter.com/1.1/statuses/user_timeline.json?user_id=" + userId + "&count=1");
        try {
            oAuthConsumer.sign(httpGet);

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode > 299) {
                throw new TwitterApiException(httpResponse.getStatusLine().getReasonPhrase());
            }
            
            String response = IOUtils.toString(httpResponse.getEntity().getContent());
            System.out.println(response);
            Tweet[] tweets = new Gson().fromJson(response, Tweet[].class);
            Tweet tweet = tweets[0];
            result = tweet.getText();
            
        } catch (Exception e) {
            throw new TwitterApiException(e);
        }        
        return result;        
    }
}
