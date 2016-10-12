<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入</title>
<link href="../plugins/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet" >
<script type="text/javascript" src="../plugins/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../plugins/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../plugins/js-xlsx/xlsx.core.min.js"></script>


<link href="../plugins/toastr/toastr.css" rel="stylesheet">
<script type="text/javascript" src="../plugins/toastr/toastr.js"></script>

</head>
<body>
	<input type="file" name="xlfile" id="xlf">

<p><input type="file" name="xlfile" id="xlf" /></p>

<textarea id="b64data"></textarea>

<pre id="out"></pre>
<br />
<script src="shim.js"></script>
<script src="jszip.js"></script>
<script src="xlsx.js"></script>
	
	
<script type="text/javascript">


var wtf_mode = false;

function fixdata(data) {
	var o = "", l = 0, w = 10240;
	for(; l<data.byteLength/w; ++l) o+=String.fromCharCode.apply(null,new Uint8Array(data.slice(l*w,l*w+w)));
	o+=String.fromCharCode.apply(null, new Uint8Array(data.slice(l*w)));
	return o;
}

function ab2str(data) {
	var o = "", l = 0, w = 10240;
	for(; l<data.byteLength/w; ++l) o+=String.fromCharCode.apply(null,new Uint16Array(data.slice(l*w,l*w+w)));
	o+=String.fromCharCode.apply(null, new Uint16Array(data.slice(l*w)));
	return o;
}

function s2ab(s) {
	var b = new ArrayBuffer(s.length*2), v = new Uint16Array(b);
	for (var i=0; i != s.length; ++i) v[i] = s.charCodeAt(i);
	return [v, b];
}

function xw_noxfer(data, cb) {
	var worker = new Worker(XW.noxfer);
	worker.onmessage = function(e) {
		switch(e.data.t) {
			case 'ready': break;
			case 'e': console.error(e.data.d); break;
			case XW.msg: cb(JSON.parse(e.data.d)); break;
		}
	};
	var arr = rABS ? data : btoa(fixdata(data));
	worker.postMessage({d:arr,b:rABS});
}

function xw_xfer(data, cb) {
	var worker = new Worker(rABS ? XW.rABS : XW.norABS);
	worker.onmessage = function(e) {
		switch(e.data.t) {
			case 'ready': break;
			case 'e': console.error(e.data.d); break;
			default: xx=ab2str(e.data).replace(/\n/g,"\\n").replace(/\r/g,"\\r"); console.log("done"); cb(JSON.parse(xx)); break;
		}
	};
	if(rABS) {
		var val = s2ab(data);
		worker.postMessage(val[1], [val[1]]);
	} else {
		worker.postMessage(data, [data]);
	}
}


function to_json(workbook) {
	var result = {};
	workbook.SheetNames.forEach(function(sheetName) {
		var roa = X.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
		if(roa.length > 0){
			result[sheetName] = roa;
		}
	});
	return result;
}


var tarea = document.getElementById('b64data');
function b64it() {
	if(typeof console !== 'undefined') console.log("onload", new Date());
	var wb = X.read(tarea.value, {type: 'base64',WTF:wtf_mode});
	process_wb(wb);
}

function process_wb(wb) {
	var output = "";
	switch(get_radio_value("format")) {
		case "json":
			output = JSON.stringify(to_json(wb), 2, 2);
			break;
		case "form":
			output = to_formulae(wb);
			break;
		default:
		output = to_csv(wb);
	}
	if(out.innerText === undefined) out.textContent = output;
	else out.innerText = output;
	if(typeof console !== 'undefined') console.log("output", new Date());
}


var xlf = document.getElementById('xlf');
function handleFile(e) {
	var files = e.target.files;
	var f = files[0];
	{
		var reader = new FileReader();
		var name = f.name;
		reader.onload = function(e) {
			var data = e.target.result;
			if(use_worker) {
				xw(data, process_wb);
			} else {
				var wb;
				if(rABS) {
					wb = X.read(data, {type: 'binary'});
				} else {
				var arr = fixdata(data);
					wb = X.read(btoa(arr), {type: 'base64'});
				}
				process_wb(wb);
			}
		};
		
		reader.readAsArrayBuffer(f);
	}
}

if(xlf.addEventListener) xlf.addEventListener('change', handleFile, false);


	
</script>
</body>
</html>