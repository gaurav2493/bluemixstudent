<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAnonymous()">
    <h2>Members</h2>
    <form class="form-signin" role="form" action='<c:url value='j_spring_security_check' />' method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input name="j_username" type="text" class="form-control" placeholder="Email address" required autofocus><br/>
        <input name="j_password" type="password" class="form-control" placeholder="Password" required>
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
     </form>
      
    <c:if test="${not empty error}">
		<div><font color="red"><br/>
			Invalid username or password</font>
		</div>
	</c:if>
</sec:authorize>
<font size=3>
<ul class="nav nav-stacked" id="sidebar">
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
                
                  <li><a href="<c:url value="/selectattendance" />">Attendance</a></li>
                  <li><a href="<c:url value="/academicreports" />">Academic Reports</a></li>
                  <li><a href="<c:url value="/notice/feeschedules" />">Fee Schedules</a></li>
                  <li><a href="<c:url value="/addstudent" />">Add a Student</a></li>
              	  <li><a href="<c:url value="/adduser" />">Add a User</a></li>
              	  <li><a href="<c:url value="/addclass" />">Add a class</a></li>
              	  <li><a href="<c:url value="/addsubject" />">Add a Subject</a></li>
              	  <li><a href="<c:url value="/branch/addbranchform" />">Add a branch</a></li>
              	  <li><a href="<c:url value="/notice/uploadpage" />">Upload a Notice</a></li>
              	
</sec:authorize>
<sec:authorize access="isAuthenticated()">
				  <li><a href="<c:url value="/notice/viewnotices/1" />">View Notices</a></li>
              	  <li><a href="<c:url value="/profile/changepassword" />">Change Password</a></li>
                  <li><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>
</sec:authorize>
</ul>
              	</font>