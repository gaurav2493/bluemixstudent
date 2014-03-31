<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src='<c:url value="/tinymce/"/>tinymce.min.js'></script>
<script>
        tinymce.init({selector:'textarea',
        width:'100%',
        height:'100%'
		});	
</script>
<br/><h3 align="center">Upload a Notice</h3><br/>
<form action='<c:url value="/notice/uploadnotice" />' method="post">
	<input type="text" name="subject" class='form-control' placeholder="Enter subject"/><br/>
	<textarea name="content"></textarea><br/>
	<input type="submit" value="Upload Notice" class="btn btn-default"/>
</form>