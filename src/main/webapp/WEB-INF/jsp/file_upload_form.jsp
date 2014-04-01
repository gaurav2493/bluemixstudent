<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script
src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script>
$(document).ready(function() {
    //add more file components if Add is clicked
    $('#addFile').click(function() {
        var fileIndex = $('#fileTable tr').children().length;
        $('#fileTable').append(
                '<tr><td>'+
                '   <input type="file" name="files['+ fileIndex +']" />'+
                '</td></tr>');
    });
     
});
</script>

<h1>Upload Notice</h1>
<script src='<c:url value="/tinymce/"/>tinymce.min.js'></script>
<script>
        tinymce.init({selector:'textarea',
        width:'100%',
        height:'100%'
		});	
</script>

<form method="post" action="<c:url value="/notice/uploadnotice" />" id="iploadForm" enctype="multipart/form-data">
 
 	<input type="text" name="subject" class='form-control' placeholder="Enter subject" value="${subject }"/><br/>
	<textarea name="content">${body }</textarea><br/>
	
	
   
    <table id="fileTable">
        <tr>
            <td><input name="files[0]" type="file" value="browse"/></td>
        </tr>
        
    </table><br/><br/>
     <input id="addFile" type="button" value="Add more files to upload" class="btn btn-lg btn-primary" />
     <br/><br/><input type="submit" value="Upload Notice" class="btn btn-default"/>
</form><br/><br/>