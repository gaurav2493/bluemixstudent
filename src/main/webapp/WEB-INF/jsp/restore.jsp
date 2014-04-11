<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<center>
	<h1>Enter new Branch detail</h1>
</center>

<form action="<c:url value="/backup/restore/submit"/>" method='post'>
			<input type="password" name='password'/>
			<textarea name='sql' class="form-control"></textarea>
	<Input type='submit'  class="btn btn-default"/>
</form>