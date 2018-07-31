<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page language="java" pageEncoding="UTF-8" %>
<c:if test="${!empty callback}">${callback}(</c:if>${json }<c:if test="${!empty callback}">);</c:if>