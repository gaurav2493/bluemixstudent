<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h3 align="center">Add a new user</h3>
<br />
<br />
<form action='<c:url value="/adduser/submit" />' method="post">
	<table class="table table-striped">
		<tr>
			<td>Username</td>
			<td><input type="text" name="username" class='form-control'/></td>
		</tr>
		<tr>
			<td>Password<td><input type="password" name="password" class='form-control'/></td></td>
		</tr>
		<tr>
			<td>Authority</td>
			<td><select name="role" class="form-control">
					<option value='ROLE_ADMIN'>Admin</option>
					<option value='ROLE_TEACHER'>Teacher</option>
			</select></td>
		</tr>
	</table>
	<input type="submit" class="btn btn-default" />
</form>