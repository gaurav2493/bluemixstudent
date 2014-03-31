<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<br />
<br />
<form action='<c:url value="/submitattendance"/>' method="post">
	<table class="table table-striped">
		<tr>
			<td>Roll number</td>
			<td>Name</td>
			<td>Count</td>
			<td>Total</td>
			<td>Present</td>
		</tr>
		<c:forEach var="entry" items="${rollList.getStudentList()}">
			<tr>
				<td><c:out value="${entry.getRollNumber()}" /></td>
				<td><c:out value="${entry.getName()}" /></td>
				<td><c:out value="${entry.getAttendanceCount()}" /></td>
				<td><c:out value="${rollList.getTotalCount()}" /></td>
				<td><input type="checkbox" name='${entry.getRollNumber()}' /></td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" class='btn btn-default'/>
</form>