<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2 align="center">Notices</h2>
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
<center>


	<ul class="pagination">
		<li class='<c:out value="${pageno>1 ? 'active': 'disabled'}"/>'><a href='<c:url value="/notice/viewnotices/" />${pageno>1 ? pageno-1: '1'}	'>&laquo;</a></li>
		<c:choose>
			<c:when test="${pageno-2>0}">
				<c:forEach var="i" begin="${pageno-2 }" end="${pageno-1}">
					<li><a href='<c:url value="/notice/viewnotices/" />${i}'><c:out
								value="${i}" /><span class="sr-only">(current)</span></a></li>
				</c:forEach>
			</c:when>
			<c:when test="${pageno-3<0}">
				<c:forEach var="i" begin="1" end="${pageno-1}">
					<li><a href='<c:url value="/notice/viewnotices/" />${i}'><c:out
								value="${i}" /><span class="sr-only">(current)</span></a></li>
				</c:forEach>
			</c:when>
		</c:choose>


		<li class="active"><a href="#">${pageno } <span
				class="sr-only">(current)</span></a></li>
		<c:forEach var="i" begin="${pageno+1 }" end="${pageno+3}">
			<li><a href="<c:url value="/notice/viewnotices/" />${i}"><c:out
						value="${i}" /><span class="sr-only">(current)</span></a></li>
		</c:forEach>
		<li class="active"><a
			href="<c:url value="/notice/viewnotices/" />${pageno+1}	">&raquo;</a></li>
	</ul>
</center>
