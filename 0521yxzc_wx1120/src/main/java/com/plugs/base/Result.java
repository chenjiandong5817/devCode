package com.plugs.base;

/**
 * Created with IntelliJ IDEA.
 * User: Lins
 * Date: 14-1-19
 * Time: 上午1:49
 * To change this template use File | Settings | File Templates.
 */
public class Result {
    private int counts;
    private String msg;
    private String title;
    private boolean success;
    private Object data;

    public Result()
    {

    }

    public Result(int counts, Object data)
    {
        this.counts = counts;
        this.data = data;
    }

    public Result(int counts, String msg, String title, boolean success, Object data)
    {
        this.counts = counts;
        this.msg = msg;
        this.title = title;
        this.success = success;
        this.data = data;
    }

    public static Result createSuccess(String msg)
    {
        return new Result(1, msg, "", true, null);
    }

    public static Result createSuccess(String msg, String title)
    {
        return new Result(1, msg, title, true, null);
    }

    public static Result createSuccess(String msg, String title, Object data)
    {
        return new Result(0, msg, title, true, data);
    }
    public static Result createSuccess(int count, String msg, String title, Object data)
    {
        return new Result(count, msg, title, true, data);
    }

    public static Result createError(String msg)
    {
        return new Result(0, msg, "", false, null);
    }
    public static Result createError(String msg, Object data)
    {
        return new Result(0, msg, "", false, data);
    }

    public static Result createError(String msg, String title)
    {
        return new Result(0, msg, title, false, null);
    }

    public int getCounts()
    {
        return counts;
    }
    public Object getData()
    {
        return data;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
