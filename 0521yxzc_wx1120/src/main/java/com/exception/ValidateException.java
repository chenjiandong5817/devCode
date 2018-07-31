package com.exception;

/**
 * 验证异常，当逻辑层执行需要的前置条件不满足的情况下，抛出此异常
 * @author Lins
 *
 */
public class ValidateException extends RuntimeException
{

	/**
	 *
	 */
	private static final long serialVersionUID = -7247658404070653458L;

	public ValidateException(String msg)
	{
		super(msg);
	}

}
