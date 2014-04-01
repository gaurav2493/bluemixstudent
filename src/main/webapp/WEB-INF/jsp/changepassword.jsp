<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<br/><br/>
<h1 align='center'>Change Password</h1>
<script>
function validatepass()
{
	var pass1=document.getElementById("pass").value;
	var pass2=document.getElementById("confirmpass").value;
	if(pass1==pass2)
	{
		if(pass1.length>5)
		{
			return true;
		}
		else
			alert("At least 6 character password required");
			return false;
	}
	alert("Entered passwords do not match");
	return false;
}
</script>
<form action='<c:url value="/profile/changepasswordsubmit"/>' method="post" onsubmit="return validatepass();">
<table class="class="table table-striped">
<tr><td>
Enter Old password </td><td><input type='password' name='old' class='form-control' placeholder="old password"/></td>
</tr><tr><td>
Enter New password</td><td> <input type='password' id='pass' class='form-control' placeholder="new password"/></td></tr>
<tr><td>
Confirm New password</td><td> <input type='password' id='confirmpass' name='new' class='form-control' placeholder="confirm passsword"/></td></tr>
</table>
<input type='submit' class='btn btn-default'/>
</form>