<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logg på SDP admin</title>
</head>
<body>
	<form name="frmlogin" action="j_security_check" method="post">
		
		<div align="center" style="position: absolute;background-color:#679AC6;left:40%;top:20%;">
			<table style="border-style:groove;border:1;background-color: rgb(); ">
				<tr>
					<th colspan="2" >
					Logg inn på SDP adminsystem
					</th>
				</tr>
				<tr>
					<td >Bruker:</td>
					<td>
						<input type="text" name="j_username"/>
					</td>
				</tr>
				<tr>
					<td>Passord:</td>
					<td>
						<input type="password" name="j_password">
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="submit" value="Logg inn" name="Logon">
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>