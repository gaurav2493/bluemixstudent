<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2 align="center">Student's Marks</h2><br/>

<table class="table table-striped">
	<tr>
		<td>Roll no</td>
		<td>Name</td>
		<td>Marks Obtained</td>
		<td>Total Marks</td>
	</tr>
	<c:forEach var='entry' items="${subjectmarkslist}">
		<tr>
			<td>${entry.getRollno()}</td>
			<td>${entry.getName()}</td>
			<td>${entry.getObtainedMarks()}</td>
			<td>${entry.getTotal_marks()}</td>
		</tr>
	</c:forEach>
</table>