<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<br />
<br />
<script>
var subjectOptions="<c:forEach var="entry" items="${subjectsMap}"><option value='<c:out value="${entry.key}"/>'><c:out value="${entry.value}"/></option></c:forEach>";
</script>


<script type="text/javascript">
var fieldsCreated=false;
var n;

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
function allTicksDone()
{
	var n=document.getElementById("nostudents").value;
	var docElement;
	for(var i=0;i<n;i++)
	{
		docElement=document.getElementById("img"+i).getAttribute("src");
		if(docElement==null || docElement.indexOf("tick")<0)
		{
			return false;
		}		
	}
	return true;
	
}
function checkDuplicateSubject()
{
	var arr=[];
	var n=document.getElementById("nosubjects").value;
   for (var i=0; i<n; i++) {
     var subject=document.getElementsByName("sub"+i)[0].value;
     arr[i]=subject;
   }
	arr.sort();
	var last = arr[0];
	for (var i=1; i<arr.length; i++) {
   		if (arr[i] == last){alert('a subject is repeatd twice');
   		return false;
   		}
   		last = arr[i];
	}
	return true;
}
function checkDuplicateRollno()
{
    var arr=[];
	var n=document.getElementById("nostudents").value;
   for (var i=0; i<n; i++) {
     var rollno=document.getElementsByName("stu"+i)[0].value;
     if(rollno.length==0)
     {
     	alert("Roll no field can't be left blank");
     	return false;
     }
     arr[i]=rollno;
   }
	arr.sort();
	var last = arr[0];
	for (var i=1; i<arr.length; i++) {
   		if (arr[i] == last){alert('Roll no '+arr[i]+' have been repeated');
   		return false;
   		}
   		last = arr[i];
	}
	return true;
}


function validateRollno(roll)
{	
	$.ajax({
    type: "POST",	
    url: "<c:url value="/ajax/validaterollno"/>",
    // The key needs to match your method's input parameter (case-sensitive).
    data: document.getElementsByName("stu"+roll)[0].value,
    contentType: "text/html; charset=utf-8",
    dataType: "json",
    success: function(data){if(JSON.stringify(data)=="true"){
    document.getElementById("img"+roll).setAttribute("src","<c:url value="/images/tick.png"/>");
    }
    else
     document.getElementById("img"+roll).setAttribute("src","<c:url value="/images/cross.png"/>");},
    failure: function(errMsg) {
        alert(errMsg);
    }
});
	return true;
}
function validatenewclass()
{
	if(fieldsCreated==false){
		var nostudent=document.getElementById("nostudents").value;
		var nosubjects=document.getElementById("nosubjects").value;
		
		if(nostudent>0 && nosubjects>0)
		{
			createSubjectFields();
			createStudentFields();
			document.getElementById("nostudents").setAttribute("disabled",true);
			document.getElementById("nosubjects").setAttribute("disabled",true);
			fieldsCreated=true;
		}
		else
			alert("Enter no of students and subjects");
		return false;
	}
	if(allTicksDone())
		if(checkDuplicateSubject())
		  return checkDuplicateRollno();
	return false;
	
}
	function createSubjectFields() {
		var n = document.getElementById("nosubjects").value;
		document.getElementById("hid1").value=n;
		var i;
		var subfields="<table class='table table-striped'>";
		for (i = 0; i < n; i++) {
			subfields+="<tr><td>Subject - "+(i+1)+"</td><td><select name='sub"+i+"' class='form-control'>"+subjectOptions+"</select></td></tr>";
		}
		subfields+="</table>";
		document.getElementById("subject-fields").innerHTML=subfields;	
		document.getElementById("div1").style.display='block';
	}
	function createStudentFields() {
		n = document.getElementById("nostudents").value;
		document.getElementById("hid0").value=n;
		var i;
		var subfields="<table class='table table-striped'>";
		for (i = 0; i < n; i++) {
			subfields+="<tr><td>Student - "+(i+1)+"</td><td><input type='text' name='stu"+i+"' class='form-control' placeholder='Roll no' list='searchresults' autocomplete='off' id='search"+i+"' onfocusout='validateRollno("+i+")'/><datalist id='searchresults'></datalist></td><td><img id='img"+i+"'/></td></tr>";
		}
		subfields+="</table>";
		document.getElementById("roll-fields").innerHTML=subfields;
		document.getElementById("div2").style.display='block';
		for(i=0;i<n;i++)
		{
		if(document.createElement("datalist").options) {

$("#search"+i).on("input", function(e) {
var val = $(this).val();
if(val === "") return;
//You could use this to limit results
//if(val.length < 3) return;
console.log(val);
$.get("<c:url value="/ajax/getrollno?count=10" />", {term:val}, function(res) {
var dataList = $("#searchresults");
dataList.empty();
if(res.DATA.length) {
for(var i=0, len=res.DATA.length; i<len; i++) {
var opt = $("<option></option>").attr("value", res.DATA[i][0]);
dataList.append(opt);
}

}
},"json");
});

}
}
	}
	
</script>
<form action='submit_new_class' method='post' onsubmit='return validatenewclass()'>
	<div class="panel panel-default">
		<div class="panel-heading">Enter new Class Detail</div>
		<div class="panel-body">
			<table class="table table-striped">
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
					<td><select name="section" class="form-control">
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
				<tr>
					<td>Session</td>
					<td><div class="control-group">
							<div class="controls">
								<select name="session" class="form-control">
									<option value="${currentYear-1}">${currentYear-1} -
										${currentYear}</option>
									<option value="${currentYear}">${currentYear} -
										${currentYear+1}</option>
									<option value="${currentYear+1}">${currentYear+1} -
										${currentYear+2}</option>
								</select>
							</div>
						</div></td>
				</tr>
				<tr>
					<td>Number of subjects</td>
					<td><input type="number" id="nosubjects" class="form-control" /></td>					
				</tr>
				<tr>
					<td>No of students</td>
					<td><input type="number" id="nostudents" class="form-control" /></td>
				</tr>
			</table>
		</div>
	</div>

	<div class="panel panel-default" style="display: none;" id='div1'>
		<div class="panel-heading">Enter new Subjects for this class</div>
		<div class="panel-body" id='subject-fields'></div>
	</div>

	<div class="panel panel-default" style="display: none;" id='div2'>
		<div class="panel-heading">Enter new Student's roll numbers</div>
		<div class="panel-body" id='roll-fields'></div>
	</div>
	<input type='hidden' name='nostudents' id='hid0' /> <input
		type='hidden' name='nosubjects' id='hid1' /> <Input type='submit'
		class="btn btn-default" />
</form><br/><br/>