<%@ page import="com.ank.imgshare.app.model.Events" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<html>
<body>
	<h1>Update Event</h1>
 
	<%
		Events event = new Events();
 
		if(request.getAttribute("events")!=null){
 
			event = (Events)request.getAttribute("events");
 
		}
 
	%>
 
	<form method="post" action="../update" >
		<input type="hidden" name="id" id="id" 
			value="<%= event.getId() %>"/> 
 
		<table>
			<tr>
				<td>
					Event Type :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                             maxlength="30" name="eventtype" id="eventtype" 
						value="<%=event.getEventType() %>" />
				</td>
			</tr>
			<tr>
				<td>
					Search Text :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                             maxlength="30" name="search" id="search" 
						value="<%=event.getSearchText().toString() %>" />
				</td>
			</tr>
			<tr>
				<td>
					Image Url :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                             maxlength="30" name="img" id="img" 
						value="<%=event.getImageUrl() %>" />
				</td>
			</tr>
			 <tr>
				<td>
					Country :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                             maxlength="30" name="country" id="country" 
						value="<%=event.getCountry() %>" />
				</td>
			</tr>
			<tr>
				<td>
					Language :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                             maxlength="30" name="lang" id="lang" 
						value="<%=event.getLanguage() %>" />
				</td>
			</tr>
			<tr>
				<td>
					Date :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                             maxlength="30" name="date" id="date" 
						value="<%=event.getDate() %>" />
				</td>
			</tr>
		</table>
		<input type="submit" class="update" title="Update" value="Update" />
	</form>
 
</body>
</html>