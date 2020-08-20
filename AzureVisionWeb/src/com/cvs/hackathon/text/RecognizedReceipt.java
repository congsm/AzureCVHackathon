package com.cvs.hackathon.text;

public class RecognizedReceipt {
	
	private String receiptLocale;
	private RecognizedForm recognizedForm;
	
	
	public RecognizedReceipt(String receiptLocale,
            RecognizedForm recognizedForm) {
		this.receiptLocale = receiptLocale;
		this.recognizedForm = recognizedForm;
	}


	public String getReceiptLocale() {
		return receiptLocale;
	}


	public void setReceiptLocale(String receiptLocale) {
		this.receiptLocale = receiptLocale;
	}


	public RecognizedForm getRecognizedForm() {
		return recognizedForm;
	}


	public void setRecognizedForm(RecognizedForm recognizedForm) {
		this.recognizedForm = recognizedForm;
	}

	
}
