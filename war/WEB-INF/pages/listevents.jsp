<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ank.imgshare.app.model.Events" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<html>
<body>
	<h1>List if Events</h1>
 
	Function : <a href="add">Add Events</a>
	<hr />
 
	<h2>All Categories</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Event ID</td>
				<td>Event Type</td>
				<td>Search Text</td>
				<td>Image Url</td>
				<td>Country</td>
				<td>Language</td>
				<td>Date</td>
			</tr>
		</thead>
 
		<%
 
		if(request.getAttribute("events")!=null){
 
			List<Events> data = 
                           (List<Events>)request.getAttribute("events");
 
			if(!data.isEmpty()){
				 for(Events c : data){
 
		%>
				<tr>
				  <td><%=c.getId() %></td>
				  <td><%=c.getEventType() %></td>
				  <td><%=c.getSearchText() %></td>
				  <td><%=c.getImageUrl() %></td>
				  <td><%=c.getCountry() %></td>
				  <td><%=c.getLanguage() %></td>
				  <td><%=c.getDate() %></td>
				  <td><a href="update/<%= c.getId() %>">Update</a> | 
                                      <a href="delete/<%=c.getId() %>">
                                       Delete</a>
                                  </td>
				</tr>
		<%	
 
				}
 
			}
 
		   }
		%>
 
        </tr>
 
	</table>
 
</body>
</html>