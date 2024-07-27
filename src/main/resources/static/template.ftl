<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Block-Type" conetnt="text/html;charset=utf-8"/>
		<title>${fileName}</title>
		<style type="text/css">

			* {
				margin: 0;
				padding: 0;
			}

			.chinese {
				font-family: Chinese;
			}

			@page{
				size : 210mm 297mm;

				@top-center {
					position: relative;
					content: element(header);
                };

				@bottom-center {
					content: element(footer);
                };

			}

			#header {
				position: running(header);
			}

			#footer {
				position: running(footer);
			}

			body {
				font-size: 12px;
				font-family: arial;
				margin-top: 20px;
				margin-bottom: 20px;
			}

			#totalpage, #currentpage{
				opacity: 0;
			}

			#totalpage:before {
				content: counter(pages);
			}

			#currentpage:before {
				content: counter(page);
			}

		</style>
	</head>
	<body>
		<div class="editor-Block-view">
			<div id="header">
				${header!""}
			</div>

			<div id="contents">
				<#if contents?? && contents?is_sequence>
					<#list contents as content>
						${content}
					</#list>
				</#if>
			</div>

			<div id="footer">
				${footer!""}
			</div>
		</div>
	</body>
</html>
