package com.cvs.hackathon.text;

import com.azure.ai.formrecognizer.*;
import com.azure.ai.formrecognizer.models.*;
import com.azure.ai.formrecognizer.training.FormTrainingClient;
import com.azure.ai.formrecognizer.training.FormTrainingClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.Context;
import com.azure.core.util.polling.SyncPoller;

public class FormRecognizer {
	
	private static String key = "cb8538b756c84a30a80c0ce972f037f9";
	private static String endpoint = "https://form-recognizer03.cognitiveservices.azure.com/";

	public static void main(String[] args) throws IOException {
		
		FormRecognizerClient recognizerClient = new FormRecognizerClientBuilder()
			    .credential(new AzureKeyCredential(key))
			    .endpoint(endpoint)
			    .buildClient();
			    
		//FormTrainingClient trainingClient = recognizerClient.getFormTrainingClient();
		FormTrainingClient trainingClient = new FormTrainingClientBuilder()
				.credential(new AzureKeyCredential(key))
				.endpoint(endpoint)
				.buildClient();

		
		String trainingDataUrl = "<SAS-URL-of-your-form-folder-in-blob-storage>";
	    String formUrl = "https://docs.microsoft.com/en-us/azure/cognitive-services/form-recognizer/media/contoso-allinone.jpg";//<SAS-URL-of-a-form-in-blob-storage>";
	    	//"C:\\Users\\csm\\eclipse-workspace-4.7\\AzureVisionWeb\\WebContent\\WEB-INF\\images\\recognize-content.png"; 
	    String receiptUrl = "https://docs.microsoft.com/azure/cognitive-services/form-recognizer/media"
	    + "/contoso-allinone.jpg";

	    // Call Form Recognizer scenarios:
	    System.out.println("Get form content...");
	    GetContent(recognizerClient, formUrl);

	    //System.out.println("Analyze receipt...");
	    //AnalyzeReceipt(recognizerClient, receiptUrl);

		/*
		 * System.out.println("Train Model with training data..."); modelId =
		 * TrainModel(trainingClient, trainingDataUrl);
		 * 
		 * System.out.println("Analyze PDF form..."); AnalyzePdfForm(recognizerClient,
		 * modelId, formUrl);
		 * 
		 * System.out.println("Manage models..."); ManageModels(trainingClient,
		 * trainingDataUrl) ;
		 */

	}
	
	private static void GetContent(FormRecognizerClient recognizerClient, String invoiceUri) throws IOException
	{
	    String analyzeFilePath = invoiceUri;
		
		SyncPoller<OperationResult, List<FormPage>> recognizeContentPoller = recognizerClient.beginRecognizeContentFromUrl(analyzeFilePath);
		 
	    
		/*
		 * File form = new File(analyzeFilePath); byte[] fileContent =
		 * Files.readAllBytes(form.toPath()); InputStream inputStream = new
		 * ByteArrayInputStream(fileContent);
		 * 
		 * SyncPoller<OperationResult, List<FormPage>> recognizeContentPoller =
		 * recognizerClient.beginRecognizeContent(inputStream, form.length());
		 */
	    
	    List<FormPage> contentResult = recognizeContentPoller.getFinalResult();
	    contentResult.forEach(formPage -> {
	        // Table information
	        System.out.println("----Recognizing content ----");
	        System.out.printf("Has width: %f and height: %f, measured with unit: %s.%n", formPage.getWidth(),
	            formPage.getHeight(),
	            formPage.getUnit());
	        formPage.getTables().forEach(formTable -> {
	            System.out.printf("Table has %d rows and %d columns.%n", formTable.getRowCount(),
	                formTable.getColumnCount());
	            formTable.getCells().forEach(formTableCell -> {
	                System.out.printf("Cell has text %s.%n", formTableCell.getText());
	            });
	            System.out.println();
	        });
	    });
		}
	}
/*
 * private static void AnalyzeReceipt(FormRecognizerClient recognizerClient,
 * String receiptUri) { SyncPoller<OperationResult, List<RecognizedReceipt>>
 * syncPoller = formRecognizerClient.beginRecognizeReceiptsFromUrl(receiptUri);
 * List<RecognizedReceipt> receiptPageResults = syncPoller.getFinalResult();
 * 
 * for (int i = 0; i < receiptPageResults.size(); i++) { RecognizedReceipt
 * recognizedReceipt = receiptPageResults.get(i); Map<String, FormField>
 * recognizedFields = recognizedReceipt.getRecognizedForm().getFields();
 * System.out.printf("----------- Recognized Receipt page %d -----------%n", i);
 * FormField merchantNameField = recognizedFields.get("MerchantName"); if
 * (merchantNameField != null) { if (merchantNameField.getFieldValue().getType()
 * == FieldValueType.STRING) {
 * System.out.printf("Merchant Name: %s, confidence: %.2f%n",
 * merchantNameField.getFieldValue().asString(),
 * merchantNameField.getConfidence()); } } FormField merchantAddressField =
 * recognizedFields.get("MerchantAddress"); if (merchantAddressField != null) {
 * if (merchantAddressField.getFieldValue().getType() == FieldValueType.STRING)
 * { System.out.printf("Merchant Address: %s, confidence: %.2f%n",
 * merchantAddressField.getFieldValue().asString(),
 * merchantAddressField.getConfidence()); } } FormField transactionDateField =
 * recognizedFields.get("TransactionDate"); if (transactionDateField != null) {
 * if (transactionDateField.getFieldValue().getType() == FieldValueType.DATE) {
 * System.out.printf("Transaction Date: %s, confidence: %.2f%n",
 * transactionDateField.getFieldValue().asDate(),
 * transactionDateField.getConfidence()); } }
 * 
 * FormField receiptItemsField = recognizedFields.get("Items"); if
 * (receiptItemsField != null) { System.out.printf("Receipt Items: %n"); if
 * (receiptItemsField.getFieldValue().getType() == FieldValueType.LIST) {
 * List<FormField> receiptItems = receiptItemsField.getFieldValue().asList();
 * receiptItems.forEach(receiptItem -> { if
 * (receiptItem.getFieldValue().getType() == FieldValueType.MAP) {
 * receiptItem.getFieldValue().asMap().forEach((key, formField) -> { if
 * (key.equals("Name")) { if (formField.getFieldValue().getType() ==
 * FieldValueType.STRING) { System.out.printf("Name: %s, confidence: %.2fs%n",
 * formField.getFieldValue().asString(), formField.getConfidence()); } } if
 * (key.equals("Quantity")) { if (formField.getFieldValue().getType() ==
 * FieldValueType.INTEGER) {
 * System.out.printf("Quantity: %d, confidence: %.2f%n",
 * formField.getFieldValue().asInteger(), formField.getConfidence()); } } if
 * (key.equals("Price")) { if (formField.getFieldValue().getType() ==
 * FieldValueType.FLOAT) { System.out.printf("Price: %f, confidence: %.2f%n",
 * formField.getFieldValue().asFloat(), formField.getConfidence()); } } if
 * (key.equals("TotalPrice")) { if (formField.getFieldValue().getType() ==
 * FieldValueType.FLOAT) {
 * System.out.printf("Total Price: %f, confidence: %.2f%n",
 * formField.getFieldValue().asFloat(), formField.getConfidence()); } } }); }
 * }); } } } }
 */
	
/*
 * private static String TrainModel( FormRecognizerClient trainingClient, String
 * trainingDataUrl) { String trainingSetSource =
 * "{unlabeled_training_set_SAS_URL}"; SyncPoller<OperationResult,
 * CustomFormModel> trainingPoller =
 * formTrainingClient.beginTraining(trainingSetSource, false);
 * 
 * CustomFormModel customFormModel = trainingPoller.getFinalResult();
 * 
 * // Model Info System.out.printf("Model Id: %s%n",
 * customFormModel.getModelId()); System.out.printf("Model Status: %s%n",
 * customFormModel.getModelStatus());
 * System.out.printf("Model created on: %s%n", customFormModel.getCreatedOn());
 * System.out.printf("Model last updated: %s%n%n",
 * customFormModel.getCompletedOn());
 * 
 * System.out.println("Recognized Fields:"); // looping through the sub-models,
 * which contains the fields they were trained on // Since the given training
 * documents are unlabeled, we still group them but they do not have a label.
 * customFormModel.getSubmodels().forEach(customFormSubModel -> { // Since the
 * training data is unlabeled, we are unable to return the accuracy of this
 * model customFormSubModel.getFieldMap().forEach((field, customFormModelField)
 * -> System.out.printf("Field: %s Field Label: %s%n", field,
 * customFormModelField.getLabel())); }); return customFormModel.getModelId(); }
 * 
 * private static String TrainModelWithLabels( FormRecognizerClient
 * trainingClient, String trainingDataUrl) { // Train custom model String
 * trainingSetSource = trainingDataUrl; SyncPoller<OperationResult,
 * CustomFormModel> trainingPoller = client.beginTraining(trainingSetSource,
 * true);
 * 
 * CustomFormModel customFormModel = trainingPoller.getFinalResult();
 * 
 * // Model Info System.out.printf("Model Id: %s%n",
 * customFormModel.getModelId()); System.out.printf("Model Status: %s%n",
 * customFormModel.getModelStatus());
 * System.out.printf("Model created on: %s%n",
 * customFormModel.getRequestedOn());
 * System.out.printf("Model last updated: %s%n%n",
 * customFormModel.getCompletedOn()); // looping through the sub-models, which
 * contains the fields they were trained on // The labels are based on the ones
 * you gave the training document. System.out.println("Recognized Fields:"); //
 * Since the data is labeled, we are able to return the accuracy of the model
 * customFormModel.getSubmodels().forEach(customFormSubModel -> {
 * System.out.printf("Sub-model accuracy: %.2f%n",
 * customFormSubModel.getAccuracy());
 * customFormSubModel.getFieldMap().forEach((label, customFormModelField) ->
 * System.out.printf("Field: %s Field Name: %s Field Accuracy: %.2f%n", label,
 * customFormModelField.getName(), customFormModelField.getAccuracy())); });
 * return customFormModel.getModelId(); } }
 */
