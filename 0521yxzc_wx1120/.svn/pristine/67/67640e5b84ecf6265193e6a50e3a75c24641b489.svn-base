package com.plugs.tag;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/18.
 */
public class PageTag extends TagSupport {
    private String pageList;
    private String formName;
    private String pageType;
    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
        PageList rows = (PageList)request.getAttribute(this.pageList);
        StringBuffer sb = new StringBuffer();
        Paginator paginator = null;
        if (rows!=null&&rows.size()>0&&rows.getPaginator()!=null&&rows.getPaginator().getTotalCount()>0){
            paginator= rows.getPaginator();
            sb.append( "<div class=\"dataTables_info\" style=\"\">当前显示 "+paginator.getStartRow()+" 到 "+paginator.getEndRow()+" 条，共 "+paginator.getTotalCount()+" 条记录</div>");
            sb.append("<div class=\"dataTables_paginate paging_full_numbers\" id=\"dyntable_paginate\" >");
        }else{
            sb.append( "<div class=\"dataTables_paginate\" style=\"text-align:left\"><span >对不起，查询不到相关数据！</span></div>");
            sb.append("<div class=\"dataTables_paginate paging_full_numbers\" id=\"dyntable_paginate\" style=\"display:none;\">");
            paginator = new Paginator(1,10,0);
        }

        sb.append("<label>每页显示&nbsp;<select size=\"1\" id=\"pageSizeLng\" name=\"dyntable_length\" onchange=\"pageSizeChange()\">");
        sb.append("<option value=\"10\" ");
        if (paginator.getLimit()==10)
            sb.append("  selected=\"selected\"");
        sb.append(" >10</option>");

        sb.append("<option value=\"20\" ");
        if (paginator.getLimit()==20)
            sb.append("  selected=\"selected\"");
        sb.append(" >20</option>");

        sb.append("<option value=\"30\" ");
        if (paginator.getLimit()==30)
            sb.append("  selected=\"selected\"");
        sb.append(" >30</option>");

        sb.append("<option value=\"50\" ");
        if (paginator.getLimit()==50)
            sb.append("  selected=\"selected\"");
        sb.append(" >50</option>");

    /*    sb.append("<option value=\"100\" ");
        if (paginator.getLimit()==100)
            sb.append("  selected=\"selected\"");
        sb.append(" >100</option>");*/

        sb.append(" </select>&nbsp;条</label>");
        sb.append("<span class=\" paginate_button\" id=\"dyntable_first\" " + getJumpClick(paginator.isFirstPage(),"1") + " >首页</span>");
        sb.append("<span class=\" paginate_button\" id=\"dyntable_previous\" " + getJumpClick(paginator.isFirstPage(),String.valueOf(paginator.getPrePage())) + ">上一页</span>");
        sb.append("<span class=\" paginate_button\" id=\"dyntable_next\" "+getJumpClick(paginator.isLastPage(),String.valueOf(paginator.getNextPage())) + " >下一页</span>");
        sb.append("<span class=\" paginate_button\" id=\"dyntable_last\" " + getJumpClick(paginator.isLastPage(),String.valueOf(paginator.getTotalPages())) + ">末页</span>");
        sb.append("</div>");

        sb.append("<SCRIPT LANGUAGE=\"JavaScript\">");
        sb.append("\n");
        sb.append("<!--");
        sb.append("\n");
        sb.append(" window.onload=function(){");
        sb.append("  var form =  document.forms['" + this.formName + "'];");
        sb.append("  var pageSizeEle = document.createElement('INPUT');");
        sb.append("  pageSizeEle.type='hidden';");
        sb.append("  pageSizeEle.name = 'pageSize';");
        sb.append("  pageSizeEle.value = "+paginator.getPage()+";");
        sb.append("  form.appendChild(pageSizeEle);");
        sb.append("};");
        sb.append("\n");
        sb.append("function "+getFunName("pageNum")+" {");
        sb.append("  var form =  document.forms['" + this.formName + "'];");
        sb.append("  var pageNumEle = document.createElement('INPUT');");
        sb.append("  pageNumEle.type='hidden';");
        sb.append("  pageNumEle.name = 'pageNum';");
        sb.append("  pageNumEle.value = pageNum;");
        sb.append("  form.appendChild(pageNumEle);");

      /*  sb.append("  var pageSizeEle = document.createElement('INPUT');");
        sb.append("  pageSizeEle.type='hidden';");
        sb.append("  pageSizeEle.name = 'pageSize';");
        sb.append("  pageSizeEle.value = " + pageBean.getPageSize() + ";");
        sb.append("  form.appendChild(pageSizeEle);");*/
        sb.append("   form.submit();");
        sb.append("");
        sb.append("}");
        sb.append("\n");
        sb.append("function pageSizeChange(){");
        sb.append("  var form =  document.forms['" + this.formName + "'];");
        sb.append("  form.pageSize.value=document.getElementById('pageSizeLng').value;");
        sb.append("  form.submit();");
        sb.append("}");
        sb.append("\n");
        sb.append("//-->");
        sb.append("</SCRIPT>");
        try {
            pageContext.getOut().write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getFunName(String pageNum){
        return this.formName+"_JumpPage("+pageNum+")";
    }
    private String getJumpClick(boolean isJump,String pageNum){
        String click="";
        if (isJump)
            return click;
        return "onclick=\""+getFunName(pageNum)+"\"";
    }

    public String getPageList() {
        return pageList;
    }

    public void setPageList(String pageList) {
        this.pageList = pageList;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }
}
