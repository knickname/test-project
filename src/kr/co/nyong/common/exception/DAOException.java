package kr.co.nyong.common.exception;

@SuppressWarnings("serial")
public class DAOException extends RuntimeException{
	public DAOException(){
		super();	
		}
	public DAOException(String msg){
		super(msg);
		}
	public DAOException(Throwable cause){
		super(cause);	
		}
}
