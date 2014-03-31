<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3><font color="orange">${notice.getSubject()}</font></h3>
<div  style="border:1px solid ; padding-top:10px;padding-bottom:25px;padding-right:50px;padding-left:50px;">
<p align="justify">
	<font size=2>${notice.getNotice() }</font>
</p>
<br />
<br />
<p align="right">
	<font size=3>Posted By - <strong>${notice.getAuthor() }<br /><font color="green">${notice.getDate()}</font>
	</strong></font>
</p>
<c:choose>
	<c:when test="${notice.isAttachment() }">
		<h3 align="left">Attachments</h3>
		<c:forEach var="entry" items="${notice.getAttachmentMap() }">
			<a href='<c:url value="/notice/downloadfile" />?sequence_no=${entry.getValue() }&notice_id=${notice.getNotice_id()}'>${entry.getKey() }</a><br/>
		</c:forEach>
	</c:when>
</c:choose>
</div>
<br/>
<c:choose>
	<c:when test="${delete}">
		<input type="button" onclick="location.href='<c:url value="/notice/delete?noticeid=" />${notice.getNotice_id() }'" value='DELETE NOTICE'>
	</c:when>
</c:choose>

