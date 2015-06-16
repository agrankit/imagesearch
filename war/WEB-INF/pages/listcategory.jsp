<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ank.imgshare.app.model.ImageCategory" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<html>
<body>
	<h1>List if Categories</h1>
 
	Function : <a href="add">Add Category</a>
	<hr />
 
	<h2>All Categories</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Version</td>
				<td>Category ID</td>
				<td>Title</td>
				<td>Search Text</td>
				<td>Image Url</td>
				<td>Country</td>
				<td>Language</td>
				<td>Has sub category</td>
				<td>Parent Category ID</td>
			</tr>
		</thead>
 
		<%
 
		if(request.getAttribute("categories")!=null){
 
			List<ImageCategory> data = 
                           (List<ImageCategory>)request.getAttribute("categories");
 
			if(!data.isEmpty()){
				 for(ImageCategory c : data){
 
		%>
				<tr>
				  <td><%=c.getVersion() %></td>
				  <td><%=c.getId() %></td>
				  <td><%=c.getTitle() %></td>
				  <td><%=c.getSearchText() %></td>
				  <td><%=c.getImageUrl() %></td>
				  <td><%=c.getCountry() %></td>
				  <td><%=c.getLanguage() %></td>
				  <td><%=c.isHasSubCategory() %></td>
				  <td><%=c.getParentCategoryId()%></td>
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