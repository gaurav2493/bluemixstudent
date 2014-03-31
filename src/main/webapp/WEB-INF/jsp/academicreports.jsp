<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<br/><br/>
<c:forEach var="entry" items="${examList}">
<input type="button" class="btn btn-lg btn-primary" value='${entry.value}' onclick="location.href = 'academicreports/options?examid=${entry.key}';">
</c:forEach>
<input type="button" class="btn btn-lg btn-primary" value='Add New Exam' onclick="location.href = 'addexam'">