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
		<img src="/kumagai/magtobmp?filename=<s:property value="filename" />">
		<ul>
		<li>memo=<s:property value="memo" />
		<li>width=<s:property value="width" /> height=<s:property value="height" />
		<li>ピクセル圧縮率：フラグ全<s:property value="flagSize" />個中、新規ピクセルが<s:property value="pixelCount" />個＝<s:property value="compressRatio1" />％
		<li>トータル圧縮率：<s:property value="totalBytes" />バイト／<s:property value="bmpSize" />バイト＝<s:property value="compressRatio2" />％
		</ul>

		</div>
		</div>
		</div>

	</body>
</html>
