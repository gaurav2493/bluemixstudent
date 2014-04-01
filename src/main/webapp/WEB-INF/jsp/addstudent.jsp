<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
$(document).ready(function(){
  $("#rollid").on("input",function(){
    $.ajax({url:"<c:url value="/ajax/validaterollno" />?rollno="+$("#rollid").val(),success:function(result){
      alert(result);
    }});
  });
});
</script>
<center>
	<h1>Enter new student detail</h1>
</center>
<form action='submit_new_student' method='post'>
	<table class="table table-striped">
		<tr>
			<td>Name</td>
			<td><input type='text' name='name' class="form-control" /></td>
		</tr>
		<tr>
			<td>Roll no</td>
			<td><input type='text' name='rollno' class="form-control" id='rollid'/></td>
		</tr>
		<tr>
			<td>Father's name</td>
			<td><input type='text' name='fname' class="form-control" /></td>
		</tr>
		<tr>
			<td>Mother's name</td>
			<td><input type='text' name='mname' class="form-control" /></td>
		</tr>
		<tr>
			<td>Email Id</td>
			<td><input type='email' name='email' class="form-control" /></td>
		</tr>
		<tr>
			<td>Parent's Email Id</td>
			<td><input type='email' name='pemail' class="form-control" /></td>
		</tr>
		<tr>
			<td>Branch</td>
			<td><select name="branch" class="form-control">
					<c:forEach var="entry" items="${branchmap }">
					<option value='${entry.getKey() }'>${entry.getValue() }</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>Section</td>
			<td><select name="class" class="form-control">
					<option value='a'>A</option>
					<option value='b'>B</option>
			</select></td>
		</tr>
		<tr>
			<td>Year</td>
			<td><select name="year" class="form-control">
					<option value=1>1</option>
					<option value=2>2</option>
					<option value=3>3</option>
					<option value=4>4</option>
			</select></td>
		</tr>
	</table>
	<Input type='submit' class="btn btn-default" />
</form><br/><br/>