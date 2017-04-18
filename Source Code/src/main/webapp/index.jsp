<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.docs.utilities.Utilities"%>
<html>
<head>
  <title>File Comparison</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  function reset(){
	  document.getElementById("fileA").value == "";
	  document.getElementById("fileB").value == "";
  }
  function show(){
	  var x = document.getElementById('resultdiv');
	    if (x.style.display === 'none') {
	        x.style.display = 'block';
	    } else {
	        x.style.display = 'none';
	    }
  } 
  function checkdata(f){
	  if (document.getElementById("fileA").value == ''){
		alert("Please Select file A");
		return false;
	  }else if(document.getElementById("fileB").value == ''){
		alert("Please Select file B");
		return false;
	  }
	  return true;	 
  };
  
  function ChangeImgae(name) {
	  
	  var o = document.getElementById("images");
		 var s = new String(name);
		 s = s.replace("\\","/");
		 o.src = "file:///" + s; 
  }
  </script>
  
</head>

<body>

<%
boolean isSuccess = false;
Map<String,String> result = new HashMap<String,String>();	
result= (Map)request.getAttribute("Result");
if(result!=null && result.get("Message").contains("successfully compared")){
	isSuccess=true;
}
%>
<div class="container" align="center" style="width: 800px;height: 300px;margin-top: 20px;" >
  <h4 align="left">File Comparison</h4>
  <h5 align="left">${Result.Message}</h5>
  <%if(isSuccess){%>
  	<a align="left" href="<%=Utilities.outputPath%>/<%=result.get("compFile")%>">Download compared file</a>
  <%}%>
  <form enctype="multipart/form-data" class="form-horizontal" id="fileform" name="fileform" action="uploadMultipleFile" method="POST" onsubmit="return checkdata(fileform)">
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">File A:</label>
      <div class="col-sm-10">
        <input type="file" class="form-control"  size=70 name="file" id="fileA" value=''>
      </div>
    </div>
    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">File B:</label>
      <div class="col-sm-10">
        <input type="file" class="form-control"  size=70 name="file" id="fileB" value=''>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10" align="left">
        <button type="submit" class="btn btn-success" ><span class="glyphicon glyphicon-ok" style="padding-right: 5px;"></span>Compare</button>
        <button type="button" id="showbtn" class="btn btn-success" onclick="show()" ><span class="glyphicon glyphicon-ok" style="padding-right: 5px;"></span>View</button>
        <button type="button" class="btn btn-danger" onclick="reset()"><span class="glyphicon glyphicon-refresh" style="padding-right: 5px;"></span>Reset</button>
      </div>
    </div>
  </form>
</div>
<%
if(isSuccess){
	int NoOfPages=2;
	NoOfPages = Integer.parseInt(result.get("NoOfPages"));
%>	
<div class="container" id="resultdiv" align="center" style="width: 800px;height: 300px;margin-top: 20px; display:none;" >
	<h4 align="left">Result File</h4>
	<div  style="overflow-y: scroll; overflow-x: scroll;height:400px;">
		<img  class="img-responsive" id="images"  name="images" width="1000" height="1000" src="<%=Utilities.outputImagePath%>/1_<%=result.get("fileName")%>"/>	
		<ul class="pagination pagination-lg">		
		<%for(int i=0;i<NoOfPages;i++){%>
  			<li onclick='ChangeImgae("<%=URLEncoder.encode(Utilities.outputImagePath.toString()+"\\"+(i+1)+"_"+result.get("fileName"))%>")'><a><%=i+1%></a></li>
  		<%} %>  	
		</ul>
	</div>
	
</div>
<%} %>
</body>
</html>
