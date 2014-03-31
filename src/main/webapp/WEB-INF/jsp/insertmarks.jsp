<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<br />
<br />
<form action='<c:url value="/academicreports/uploadmarks/submitmarks"/>' method="post">
	<table class="table table-striped">
		<tr>
			<td>Roll number</td>
			<td>Name</td>
			<td>Marks</td>
		</tr>
		<c:forEach var="entry" items="${rollList}">
			<tr>
				<td><c:out value="${entry.key}"/></td>
				<td><c:out value="${entry.value}"/></td>
				<td><input type="number" name='<c:out value="${entry.key}"/>' class="btn btn-default"/></td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" class='btn btn-default'/>
</form>