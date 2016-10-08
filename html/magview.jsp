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

		<h3>パレット</h3>
		<table style='border-collapse: collapse;'>
			<tr>
				<s:iterator var="counter" begin="0" end="15" >
				<th style='border: 1px black solid;'><s:property /></th>
				</s:iterator>
			</tr>
			<tr>
				<s:iterator value="palette">
				<td style='border: 1px black solid; background-color:<s:property />; width:20; height:20;'></td>
				</s:iterator>
			</tr>
		</table>

		<h3>属性値</h3>
		<ul>
		<li>memo=<s:property value="memo" />
		<li>width=<s:property value="width" /> height=<s:property value="height" />
		<li>ピクセル圧縮率：フラグ全<s:property value="flagSize" />個中、実体ピクセルが<s:property value="pixelCount" />個＝<s:property value="compressRatio1" />％
		<li>トータル圧縮率：<s:property value="totalBytes" />バイト／<s:property value="bmpSize" />バイト＝<s:property value="compressRatio2" />％
		</ul>

		<h3>添付文書</h3>
		<textarea readonly>
<s:iterator value="docFileLines"><s:property />
</s:iterator>
		</textarea>

		</div>
		</div>
		</div>

	</body>
</html>
