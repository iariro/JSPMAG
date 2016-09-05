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

		<h1>file=<s:property value="filename" /></h1>
		<ul>
		<li>memo=<s:property value="memo" />
		<li>width=<s:property value="width" /> height=<s:property value="height" />
		</ul>
		<img src="/kumagai/magtobmp?filename=<s:property value="filename" />">

		</div>
		</div>
		</div>

	</body>
</html>
