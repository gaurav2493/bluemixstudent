<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
function selectclassvalidate()
{
	var ses=document.getElementsByName("session")[0].value;
	if(ses=="session")
	{
		alert('Select Session First');
		return false;
	}
	return true;
}
</script>
<h3>Choose Your Class</h3>
<br />
<form action='<c:url value="/academicreports/uploadmarks/insertmarks" />' method="post" onsubmit="return selectclassvalidate();">
	<table class="table table-striped">
		<tr>
			<td>Session</td>
			<td><Select name="session" class="form-control">
					<option value="session">Session</option>
					<option value="${currentYear-1}">${currentYear-1} - ${currentYear}</option>
					<option value="${currentYear}">${currentYear} - ${currentYear+1}</option>
					<option value="${currentYear+1}">${currentYear+1} - ${currentYear+2}</option>
			</Select></td>
		<tr>
			<td>Branch</td>
			<td><select name="branch" class="form-control">
					<c:forEach var="entry" items="${branchmap }">
					<option value='${entry.getKey() }'>${entry.getValue() }</option>
				</c:forEach>
			</select></td>
		<tr>
			<td>Year</td>
			<td><select name="year" class="form-control">
					<option value=1>1</option>
					<option value=2>2</option>
					<option value=3>3</option>
					<option value=4>4</option>
			</select></td>
		<tr>
			<td>Section</td>
			<td><select name="section" class="form-control">
					<option value='a'>A</option>
					<option value='b'>B</option>
			</select></td>
		</tr>
		<tr>
			<td>Subject</td>
			<td><select name="subject" class="form-control">
					<c:forEach var="entry" items="${subjectsMap}">
						<option value='<c:out value="${entry.key}"/>'><c:out value="${entry.value}"/></option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>Total Marks</td>
			<td><input type="number" value='100' name="total" class="btn btn-default"/></td>
		</tr>
	</table>
	<input type="submit" class="btn btn-default"/>
</form>