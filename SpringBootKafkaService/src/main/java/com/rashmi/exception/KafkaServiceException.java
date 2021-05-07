package com.rashmi.exception;

public class KafkaServiceException extends RuntimeException{

	public KafkaServiceException(String msg)
	{
		super(msg);
	}
	
	public KafkaServiceException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
