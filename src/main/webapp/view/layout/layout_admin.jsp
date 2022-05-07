<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<tiles:insertAttribute name="cssjs" flush="true"/>
	</head>

	<body>
			<tiles:insertAttribute name="header" flush="true"/>
			<div class="container">
				<main>	
					<tiles:insertAttribute name="content" flush="true"/>
				</main>
			</div>
			<tiles:insertAttribute name="footer" flush="true"/>

	</body>
</html>