<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<center>
	<h1>Enter new Branch detail</h1>
</center>

<form action="<c:url value="/branch/add"/>" method='post'>
	<table class="table table-striped">
		<tr>
			<td>Branch Name</td>
			<td><input type='text' name='name' class="form-control"/></td>
		</tr>
		<tr>
			<td>Branch code</td>
			<td><input type='text' name='code' class="form-control"/></td>
		</tr>
	</table>
	<Input type='submit'  class="btn btn-default"/>
</form>