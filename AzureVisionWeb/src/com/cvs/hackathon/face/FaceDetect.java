package com.cvs.hackathon.face;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class FaceDetect {

	    // Replace <Subscription Key> with your valid subscription key.
	    //private static final String subscriptionKey = "ee0b3a6503534fbbae49d7a737707306";

	    //private static final String uriBase = "https://hackathonface-team01.cognitiveservices.azure.com/face/v1.0/detect";
	        //"https://<My Endpoint String>.com/face/v1.0/detect";
	    
	    private static final String action = "/face/v1.0/detect";

	    //location eastus;
	    
	   // private static final String imageWithFaces =
	  //      "{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}";
	    
	    private static final String imageWithFaces =
		        "{\"url\":\"https://storagecvteam01.blob.core.windows.net/faces/facetoid01.jpg\"}";

	    private static final String faceAttributes =
	        "age,gender,headPose,smile,facialHair,glasses,emotion";
	    
	    public static void main(String[] args) {
	        
	    	detectFace();
	    	
	    	
	    	String imgUrl = "https://storagecvteam01.blob.core.windows.net/faces/facetoid01.jpg";
	    	//String imgUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg";
	    	
	    	//getFaceId(imgUrl);
	    }
	    
	    private static void detectFace() {
	    	HttpClient httpclient = HttpClientBuilder.create().build();

	        try
	        {
	            URIBuilder builder = new URIBuilder(FaceAPIUtil.uriBase + action);

	            // Request parameters. All of them are optional.
	            builder.setParameter("returnFaceId", "true");
	            builder.setParameter("returnFaceLandmarks", "false");
	            builder.setParameter("returnFaceAttributes", faceAttributes);

	            // Prepare the URI for the REST API call.
	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);

	            // Request headers.
	            request.setHeader("Content-Type", "application/json");
	            request.setHeader("Ocp-Apim-Subscription-Key", FaceAPIUtil.subscriptionKey);

	            // Request body.
	            StringEntity reqEntity = new StringEntity(imageWithFaces);
	            request.setEntity(reqEntity);

	            // Execute the REST API call and get the response entity.
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();
	            if (entity != null)
	            {
	                // Format and display the JSON response.
	                System.out.println("REST Response:\n");

	                String jsonString = EntityUtils.toString(entity).trim();
	                if (jsonString.charAt(0) == '[') {
	                    JSONArray jsonArray = new JSONArray(jsonString);
	                    
	                    System.out.println(jsonArray.toString(2));
	                    System.out.println("Faceid: " + jsonArray.getJSONObject(0).getString("faceId"));
	                }
	                else if (jsonString.charAt(0) == '{') {
	                    JSONObject jsonObject = new JSONObject(jsonString);
	                    System.out.println(jsonObject.toString(2));
	                } else {
	                    System.out.println(jsonString);
	                }
	            }
	        }
	        catch (Exception e)
	        {
	            // Display error message.
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    private static String getFaceId(String imgUrl) {
	    	
	    	String faceId = "";
	    	
	    	HttpClient httpclient = HttpClientBuilder.create().build();

	        try
	        {
	            URIBuilder builder = new URIBuilder(FaceAPIUtil.uriBase + action + "?returnFaceId");

	            // Request parameters. All of them are optional.
	            builder.setParameter("returnFaceId", "true");
	            builder.setParameter("returnFaceLandmarks", "false");
	            builder.setParameter("returnFaceAttributes", faceAttributes);

	            // Prepare the URI for the REST API call.
	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);

	            // Request headers.
	            request.setHeader("Content-Type", "application/json");
	            request.setHeader("Ocp-Apim-Subscription-Key", FaceAPIUtil.subscriptionKey);

	            // Request body.
	            JSONObject jObj = new JSONObject();
	            jObj.put("url", imgUrl);
	            System.out.println("image url json: " + jObj.toString());
	            StringEntity reqEntity = new StringEntity(jObj.toString());
	            request.setEntity(reqEntity);

	            // Execute the REST API call and get the response entity.
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();
	            if (entity != null)
	            {
	                // Format and display the JSON response.
	                System.out.println("REST Response:\n");

	                String jsonString = EntityUtils.toString(entity).trim();

	                if (jsonString.charAt(0) == '[') {
	                    JSONArray jsonArray = new JSONArray(jsonString);
	                    faceId = jsonArray.getJSONObject(0).getString("faceId");
	                    System.out.println("Faceid: " + faceId);
	                    System.out.println(jsonArray.toString(2));
	                }
	                else if (jsonString.charAt(0) == '{') {
	                    JSONObject jsonObject = new JSONObject(jsonString);
	                    faceId = jsonObject.getString("faceId");
	                    System.out.println("faceid: " + faceId);
	                    System.out.println(jsonObject.toString(2));
	                } else {
	                    System.out.println(jsonString);
	                }
	            }
	        }
	        catch (Exception e)
	        {
	            // Display error message.
	            System.out.println(e.getMessage());
	        }
	        
	    	return faceId;
	    }
	}