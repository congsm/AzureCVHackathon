package com.cvs.hackathon.face;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * PersonGroup is named 'CVSEmployee'
 */
public class PersonGroup {
	
	public final static String personGroupIdName = "CVSEmployee";
	public final static String personGroupId = "cvs_employee";
	private final static String action ="/face/v1.0/persongroups/";
	private final static String detectAction = "/face/v1.0/detect";
	private final static String addPersonToPG ="persons";
	private final static String faceAttributes = "age,gender,headPose,smile,facialHair,glasses,emotion";
	
	public static void main(String[] args) {
		
		//createPersonGroup();

		
		//add person to group
		//String personName = "Shumin Cong"; //{"personId":"3f7ac57f-1fb1-4e2e-b027-da7aae8514b5"}
		//createPerson(personGroupId,  personName);
		
		//add faces to person
		//addFaceToPerson();
		
		//train group
		//trainGroup();
		
		//get train status
		//getTrainingStatus();
		
		//Identify a person
		identify();
		
		//String imgUrl = "https://storagecvteam01.blob.core.windows.net/faces/facetoid01.jpg";
		//getFaceId(imgUrl);
	}

	
	private static void createPersonGroup() {
		HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder(FaceAPIUtil.uriBase + action + personGroupId);//"https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}");


            URI uri = builder.build();
            HttpPut request = new HttpPut(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", FaceAPIUtil.subscriptionKey);


            // Request body
            JSONObject jObj = new JSONObject();
            jObj.put("name", personGroupIdName);  
            StringEntity reqEntity = new StringEntity(jObj.toString());
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
	
	}
	
	/**
	 * Create a new person in a specified person group
	 */
	
	private static void createPerson(String personGroupId, String personName) {
		
		HttpClient httpclient = HttpClients.createDefault();
		
		try
        {
			//"https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}/persons
			String url = FaceAPIUtil.uriBase + action + personGroupId + "/" + addPersonToPG;
            URIBuilder builder = new URIBuilder(url);
            //System.out.println("Request url: " + builder.getPath());
            System.out.println("Request url: " + url);


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", FaceAPIUtil.subscriptionKey);


            // Request body
            JSONObject jObj = new JSONObject();
            jObj.put("name", personName);  
            StringEntity reqEntity = new StringEntity(jObj.toString());
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
		
	}
	
	private static void addFaceToPerson() {
		
		String personId = "3f7ac57f-1fb1-4e2e-b027-da7aae8514b5";		
		
		HttpClient httpclient = HttpClients.createDefault();
		//CloseableHttpClient httpClient = HttpClients.createDefault();

		for (int i = 2; i<=4; i++ ) {
			String imgUrl = "https://storagecvteam01.blob.core.windows.net/faces/shumin0"; 
			imgUrl = imgUrl + "0" + Integer.toString(i) + ".jpg";
			
			
        try
        {
        	//https://{endpoint}/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces
        	//https://hackathonface-team01.cognitiveservices.azure.com/face/v1.0/persongroups/cvs_employee/persons/3f7ac57f-1fb1-4e2e-b027-da7aae8514b5/persistedFaces	
        	//https://{endpoint}/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces[?userData][&targetFace][&detectionModel]
        	//https://canadacentral.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces"
        	String urlStr = FaceAPIUtil.uriBase + action + personGroupId + "/" + addPersonToPG + "/" + personId + "/persistedFaces";
            URIBuilder builder = new URIBuilder(urlStr);
            
            if (i==1)
            	System.out.println("request url: " + urlStr);
            
            //builder.setParameter("userData", "{string}");
            //builder.setParameter("targetFace", "{string}");
            //builder.setParameter("detectionModel", "detection_01");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", FaceAPIUtil.subscriptionKey);


            // Request body
            JSONObject jObj = new JSONObject();
            jObj.put("url", imgUrl);
            System.out.println("image url: " + imgUrl);
            StringEntity reqEntity = new StringEntity(jObj.toString());
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        //end for loop
		}
    }
	
	
	private static void trainGroup() {
		
		//https://{endpoint}/face/v1.0/persongroups/{personGroupId}/train
		//https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}/train
		String requestUrl = FaceAPIUtil.uriBase + action + personGroupId + "/train";
		
		HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder(requestUrl);


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", FaceAPIUtil.subscriptionKey);


            // Request body
            request.setEntity(null);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    
	}
	
	
	private static void getTrainingStatus() {
		
		//https://{endpoint}/face/v1.0/persongroups/{personGroupId}/training
		
		String requestUrl = FaceAPIUtil.uriBase + action + personGroupId + "/training";
				
		HttpClient httpclient = HttpClients.createDefault();

        try
        {
        	URIBuilder builder = new URIBuilder(requestUrl);

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", FaceAPIUtil.subscriptionKey);

            // Request body
            
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    
	
	}
	
	
	private static void identify() {
		
		//Faces to ID
		
		//String imgUrl = "https://storagecvteam01.blob.core.windows.net/faces/facetoid01.jpg";
		//String imgUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg";
		//String imgUrl = "https://storagecvteam01.blob.core.windows.net/faces/shumin008.jpg";
		String imgUrl = "https://storagecvteam01.blob.core.windows.net/faces/Joe pic2.jpg";
		
		String faceId = getFaceId(imgUrl);
		System.out.println("Face id: " + faceId);
		String[] faceIds = new String[] {faceId};
		
				
		//https://westus.api.cognitive.microsoft.com/face/v1.0/identify"
		//https://{endpoint}/face/v1.0/identify
		String requestUrl = FaceAPIUtil.uriBase + "/face/v1.0/identify";
		HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder(requestUrl);


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", FaceAPIUtil.subscriptionKey);


            // Request body
            JSONObject jObj = new JSONObject();
            jObj.put("faceIds", faceIds);
            jObj.put("personGroupId", personGroupId);
            System.out.println("request json string: " + jObj.toString());
            StringEntity reqEntity = new StringEntity(jObj.toString());
                       
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
            	String jsonString = EntityUtils.toString(entity).trim();

                if (jsonString.charAt(0) == '[') {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    
                    
                    JSONArray candidates = jsonArray.getJSONObject(0).getJSONArray("candidates");
                    if (!candidates.isEmpty()) {                    
	                    String personId = candidates.getJSONObject(0).getString("personId");
	                    float confidence = jsonArray.getJSONObject(0).getJSONArray("candidates").getJSONObject(0).getFloat("confidence");
	                    System.out.println("personId: " + personId);
	                    System.out.println("Candidate was VERIFIED with confidence of " + confidence);
                    }else {
                    	System.out.println("Candidate CANNOT be verified!!! ");
                    }
                    //System.out.println(jsonArray.toString(2));
                }
                else if (jsonString.charAt(0) == '{') {
					/*
					 * JSONObject jsonObject = new JSONObject(jsonString); candidates =
					 * jsonObject.getString("candidates"); System.out.println("candidates: " +
					 * candidates); System.out.println(jsonObject.toString(2));
					 */
                } else {
                    System.out.println(jsonString);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
	}
	
	private static String getFaceId(String imgUrl) {
		String faceId = "";
    	
    	HttpClient httpclient = HttpClientBuilder.create().build();

        try
        {
            URIBuilder builder = new URIBuilder(FaceAPIUtil.uriBase + detectAction + "?returnFaceId");

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
	

