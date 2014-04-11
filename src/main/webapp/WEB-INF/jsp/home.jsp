<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2 align="center">Recent Notices</h2>
<br />

<table class="table table-striped">
	<tr>
		<th>Subject</th>
		<th>Date</th>
		<th>Author</th>
	</tr>
	<c:forEach var="entry" items="${noticeList}">
		<tr>
			<td><a
				href='<c:url value="/notice/viewnotice/${entry.getNotice_id() }"/>'>${entry.getSubject()}&nbsp;<c:choose>
						<c:when test="${entry.isAttachment()}">
							<span class="glyphicon glyphicon-paperclip"></span>
						</c:when>
					</c:choose></a></td>

			<td><font color="green">${entry.getDate()}</font></td>
			<td>${entry.getAuthor()}</td>
		</tr>
	</c:forEach>
</table>