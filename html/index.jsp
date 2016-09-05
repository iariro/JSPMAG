<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv=Content-Style-Type content=text/css>
		<link media=all href="hatena.css" type=text/css rel=stylesheet>
		<title>MAG画像ビューア</title>
	</head>

	<body>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h1>path=<s:property value="folder" /></h1>

		<s:form action="magview" theme="simple">
			<s:select name="filename" list="files" size="10">
			</s:select>
			<s:submit />
		</s:form>

		</div>
		</div>
		</div>

	</body>
</html>
