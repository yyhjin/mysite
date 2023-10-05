package com.poscodx.mysite.exception;

public class FileUploadServiceException extends RuntimeException {

	public FileUploadServiceException(String message) {
		super(message);
	}

	public FileUploadServiceException() {
		super("FileUploadServiceException Thrown");
	}
}
